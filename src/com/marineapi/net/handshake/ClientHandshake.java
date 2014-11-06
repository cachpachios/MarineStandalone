package com.marineapi.net.handshake;

import java.io.IOException;
import java.io.OutputStream;

import com.marineapi.net.ByteData;
import com.marineapi.net.Packet;

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
	public void writeToStream(OutputStream stream) throws IOException {
		// Non clientbound packet!
	}

	@Override
	public void readFromBytes(ByteData input) {
		protocolVersion = input.readVarInt();
		serverAddress = input.readString();
		port = input.readUnsignedShort();
		nextState = input.readVarInt();
	}
	
	public String toString() {
		return protocolVersion + " at " + serverAddress + " : " + port + " target state " + nextState;
	}
	
}
