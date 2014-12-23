///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// MarineStandalone is a minecraft server software and API.
// Copyright (C) MarineMC (marinemc.org)
//
// This program is free software; you can redistribute it and/or modify
// it under the terms of the GNU General Public License as published by
// the Free Software Foundation; either version 2 of the License, or
// (at your option) any later version.
//
// This program is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
// GNU General Public License for more details.
//
// You should have received a copy of the GNU General Public License along
// with this program; if not, write to the Free Software Foundation, Inc.,
// 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

package org.marinemc.net;

import org.marinemc.io.data.ByteData;
import org.marinemc.io.data.ByteEncoder;
import org.marinemc.server.Marine;

import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
/**
 * @author Fozie
 */
public class Client {
    private final Socket connection;
    private final PacketOutputStream output;

    private InputStream input;

    private States state;
    private int compressionThreshold = -1;

    // For indexing in IngameInterceptor
    // private String userName;
    private short uid; // Saved as Short not short for the ability to equal null

    public Client(Socket s) throws IOException {
        this.state = States.HANDSHAKE;
        this.connection = s;
        this.input = s.getInputStream();
        output = new PacketOutputStream(s.getOutputStream());
        this.uid = -1;
    }
    
    public void sendPacket(Packet packet) { //TODO: PacketBuffer
        try {
            packet.writeToStream(output);
            System.out.println("Sent packet: "+packet.getID()+ ", State: " + state.name());
        } catch (IOException e) {
        	//TODO: 
        }
    }

    public NetworkManager getNetwork() {
        return Marine.getServer().getNetworkManager();
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
            this.connection.getOutputStream().flush();
            this.connection.getOutputStream().close();
            this.connection.getInputStream().close();
            this.connection.close();
        } catch (IOException e) {
        }
    }


    public boolean isActive() {
        try { // Write a 0 bit to check if available
            getConnection().getOutputStream().write(ByteEncoder.writeBoolean(false));
        } catch (IOException e1) {
            return false;
        }
        return true;
    }

    public ConnectionStatus process() { // Returns true if connection is closed.

        int a = 0;
        try {
            a = input.available();
        } catch (IOException e) {
            return ConnectionStatus.CONNECTION_PROBLEMS;
        }


        if (a == 0) return ConnectionStatus.EMPTY;


        byte[] allData = new byte[a];

        try {
            input.read(allData);
        } catch (IOException e) {
            return ConnectionStatus.CONNECTION_PROBLEMS;
        }

        ByteData data = new ByteData(allData);

        List<ByteData> packages = new ArrayList<ByteData>();


        while (data.remainingBytes() > 0) {
            int l = data.readVarInt();

            if (l == 0)
                continue;

            if (l > data.remainingBytes())
                continue;

            packages.add(data.readData(l));
        }

        for (ByteData p : packages) {
        	final int id  = p.readVarInt();
            System.out.println("Intercepted packet: "+ id + ", State: " + state.name());
            getNetwork().packetHandler.intercept(id, p, this);
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

    public short getUID() {
        return this.uid;
    }

    public void setUID(final short uid) {
        this.uid = uid;
    }

    public enum ConnectionStatus {
        EMPTY,
        CONNECTION_PROBLEMS,
        PROCESSED,
        CLOSED
    }


}
