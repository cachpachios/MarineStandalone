package com.marine.net.handshake;

import java.io.IOException;

import com.marine.io.data.ByteData;
import com.marine.net.Packet;
import com.marine.net.PacketOutputStream;
import com.marine.net.States;

public class ClientHandshake extends Packet {

	protected int protocolVersion;
	protected String serverAddress;
	protected int port;
	protected int nextState;
	
	@Override
	public int getID() {
		return 0x00;
	}
	
	@Override
	public void writeToStream(PacketOutputStream stream) throws IOException {
		// Non clientbound packet!
	}

	@Override
	public void readFromBytes(ByteData input) {
		protocolVersion = input.readVarInt();
		serverAddress = input.readUTF8();
		port = input.readUnsignedShort();
		nextState = input.readVarInt();
	}
	
	public String toString() {
		return protocolVersion + " at " + serverAddress + " : " + port + " target state " + nextState;
	}
	
	public int getProtocolVersion() {
		return protocolVersion;
	}

	public String getServerAddress() {
		return serverAddress;
	}

	public int getPort() {
		return port;
	}

	public int getState() {
		return nextState;
	}

	@Override
	public States getPacketState() {
		return States.HANDSHAKE;
	}
	
}
