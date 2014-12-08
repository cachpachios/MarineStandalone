package com.marine.net;

import com.marine.io.data.ByteData;
import com.marine.io.data.ByteEncoder;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;

public class Client {
    private final NetworkManager networkManager;
    private final Socket connection;
    private final PacketOutputStream output;
    private States state;
    private int compressionThreshold = -1;
    // For indexing in IngameInterceptor
    private String userName;

    public Client(NetworkManager network, Socket s) throws IOException {
        this.state = States.HANDSHAKE;
        this.networkManager = network;
        this.connection = s;
        output = new PacketOutputStream(this, s.getOutputStream());
    }

    public void sendPacket(Packet packet) { //TODO: PacketBuffer
        try {
            packet.writeToStream(output);
        } catch (IOException e) {
            networkManager.cleanUp(this);
        }
    }

    public NetworkManager getNetwork() {
        return networkManager;
    }

    public InetAddress getAdress() {
        return connection.getInetAddress();
    }

    protected Socket getConnection() {
        return connection;
    }

    public void setState(int state) {
        if (state == 0)
            this.state = States.HANDSHAKE;
        else if (state == 1)
            this.state = States.INTRODUCE;
        else if (state == 2)
            this.state = States.LOGIN;
        else if (state == 3)
            this.state = States.INGAME;
    }

    public States getState() {
        return state;
    }

    public void setState(States state) {
        this.state = state;
    }

    public void terminate() {
        try {
            this.connection.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ConnectionStatus process() { // Returns true if connection is closed.

        if (this.state != States.INGAME)
            try { // Write a 0 bit to check if available
                getConnection().getOutputStream().write(ByteEncoder.writeBoolean(false));
            } catch (IOException e1) {
                return ConnectionStatus.CONNECTION_PROBLEMS;
            }


        // Read from client:
        if (getConnection().isClosed()) {
            return ConnectionStatus.CONNECTION_PROBLEMS;
        }

        int a = 0;
        try {
            a = getConnection().getInputStream().available();
        } catch (IOException e) {
            return ConnectionStatus.CONNECTION_PROBLEMS;
        }


        if (a == 0) return ConnectionStatus.EMPTY;

        
        int x = 0;
		try {
			x = getConnection().getInputStream().read();
		} catch (IOException e1) {
			 return ConnectionStatus.CONNECTION_PROBLEMS;
		}
		if(x == -1) {
			return ConnectionStatus.CLOSED;
		}
		
        

        byte[] allData = new byte[a];

        try {
            getConnection().getInputStream().read(allData);
        } catch (IOException e) {
            return ConnectionStatus.CONNECTION_PROBLEMS;
        }

        ByteData data = new ByteData(allData);
        data.write(0, (byte)x);

        ArrayList<ByteData> packages = new ArrayList<ByteData>();


        while (data.remainingBytes() > 0) {
            int l = data.readVarInt();

            if (l == 0)
                continue;

            if (l > data.remainingBytes())
                continue;

            packages.add(data.readData(l));
        }

        for (ByteData p : packages) {
            getNetwork().packetHandler.intercept(p, this);
        }

        return ConnectionStatus.PROCESSED;
    }

    public int getCompressionThreshold() {
        return compressionThreshold;
    }

    public void setCompressionThreshold(int compressionThreshold) {
        this.compressionThreshold = compressionThreshold;
    }

    public boolean compressionEnabled() {
        return compressionThreshold != -1;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String name) {
        userName = name;
    }

    public enum ConnectionStatus {
        EMPTY,
        CONNECTION_PROBLEMS,
        PROCESSED,
        CLOSED
    }


}
