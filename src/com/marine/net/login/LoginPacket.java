package com.marineapi.net.login;

import java.io.IOException;
import java.io.OutputStream;

import com.marineapi.io.data.ByteData;
import com.marineapi.net.Packet;
import com.marineapi.net.States;

public class LoginPacket extends Packet {

	public String name;
	
	@Override
	public int getID() {
		return 0x00;
	}

	@Override
	public void writeToStream(OutputStream stream) throws IOException {
		// SERVERBOND PACKET
	}

	@Override
	public void readFromBytes(ByteData input) {
		name = input.readUTF8();
	}

	@Override
	public States getPacketState() {
		return States.LOGIN;
	}
}
