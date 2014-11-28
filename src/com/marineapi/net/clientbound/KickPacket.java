package com.marineapi.net.clientbound;

import java.io.IOException;
import java.io.OutputStream;

import com.marineapi.io.data.ByteData;
import com.marineapi.io.data.ByteEncoder;
import com.marineapi.net.Packet;
import com.marineapi.net.States;

public class KickPacket extends Packet{

	private String msg;
	
	public KickPacket(String msg) {
		this.msg = msg;
	}
	
	@Override
	public int getID() {
		return 0xFF;
	}

	@Override
	public void writeToStream(OutputStream stream) throws IOException {
		stream.write(ByteEncoder.writeInt(getID()));
		stream.write(ByteEncoder.writeUTFPrefixedString(msg));
		
	}


	@Override
	public void readFromBytes(ByteData input) {
	// Non Client to Server Packet :)
	}
	
	public static int getGroup() {
		return 2;
	}

	@Override
	public States getPacketState() {
		return States.INGAME;
	}

}
