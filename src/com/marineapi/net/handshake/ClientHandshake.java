package com.marineapi.net.handshake;

import java.io.IOException;
import java.io.OutputStream;

import com.marineapi.Logging;
import com.marineapi.net.Packet;
import com.marineapi.net.States;
import com.marineapi.net.data.ByteData;

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
		serverAddress = input.readUTF8PrefixedString();
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
