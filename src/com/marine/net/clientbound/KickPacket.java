package com.marine.net.clientbound;

import java.io.IOException;

import com.marine.io.data.ByteData;
import com.marine.io.data.ByteEncoder;
import com.marine.net.Packet;
import com.marine.net.PacketOutputStream;
import com.marine.net.States;

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
	public void writeToStream(PacketOutputStream stream) throws IOException {
		stream.write(getID(), ByteEncoder.writeUTFPrefixedString(msg));
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
