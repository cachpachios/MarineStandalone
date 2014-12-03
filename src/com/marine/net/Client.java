package com.marine.net;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;

import com.marine.Logging;
import com.marine.io.data.ByteData;
import com.marine.io.data.ByteEncoder;

public class Client {
	private final NetworkManager networkManager;
	
	
	private States state;
	
	private final Socket connection;
	
	public Client(NetworkManager network, Socket s) {
		this.state = States.HANDSHAKE;
		this.networkManager = network;
		this.connection = s;
	}
	
	public void sendPacket(Packet packet) { //TODO: PacketBuffer
		Logging.instance().info("Sending packet ID: " + packet.getID() + " State: " + packet.getPacketState());
		try {
			packet.writeToStream(connection.getOutputStream());
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
		if(state==0)
			this.state = States.HANDSHAKE;
		else
			if(state==1)
				this.state = States.INTRODUCE;
		else
			if(state==2)
				this.state = States.LOGIN;
		else
			if(state==3)
				this.state = States.INGAME;
	}
	
	public void setState(States state) {
		this.state = state;
	}
	
	public States getState() {
		return state;
	}
	
	public void terminate() {
		try {
			this.connection.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	public ConnectionStatus process(){ // Returns true if connection is closed.
		
		if(this.state != States.INGAME)
		try { // Write a 0 bit to check if available
			getConnection().getOutputStream().write(ByteEncoder.writeBoolean(false));
		} catch (IOException e1) {
			return ConnectionStatus.CONNECTION_PROBLEMS;
		}
		
		
		// Read from client:
		if(getConnection().isClosed()) {
			return ConnectionStatus.CONNECTION_PROBLEMS;
		}
		
		int a = 0;
		try { a = getConnection().getInputStream().available(); } catch (IOException e) {
			return ConnectionStatus.CONNECTION_PROBLEMS;
		}
		
		
		if(a==0) return ConnectionStatus.EMPTY;
		
		
		
		byte[] allData = new byte[a];
		
		try { getConnection().getInputStream().read(allData); } catch (IOException e) {return ConnectionStatus.CONNECTION_PROBLEMS;}			
		
		ByteData data = new ByteData(allData);
		
		ArrayList<ByteData> packages = new ArrayList<ByteData>();
		
		
		while(data.remainingBytes() > 0) {
			int l = data.readVarInt();
		
			if(l == 0)
				continue;
			
			if(l > data.remainingBytes())
				continue;
			
			packages.add(data.readData(l));
		}
		
		for(ByteData p : packages) {
			getNetwork().packetHandler.intercept(p, this);
		}
		
		return ConnectionStatus.PROCESSED;
}
	
	public enum ConnectionStatus {
		EMPTY,
		CONNECTION_PROBLEMS,
		PROCESSED
	}

	
}
