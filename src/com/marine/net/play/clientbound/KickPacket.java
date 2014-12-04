package com.marine.net.play.clientbound;

import java.io.IOException;

import com.marine.io.data.ByteData;
import com.marine.net.Packet;
import com.marine.net.PacketOutputStream;
import com.marine.net.States;

public class KickPacket extends Packet{

	private final String msg;
	
	public KickPacket(final String msg) {
		this.msg = msg;
	}
	
	@Override
	public int getID() {
		return 0x40;
	}

	@Override
	public void writeToStream(PacketOutputStream stream) throws IOException {
		ByteData d = new ByteData();
		d.writeUTF8(msg);
		stream.write(getID(), d.getBytes());
	}


	@Override
	public void readFromBytes(ByteData input) {
	// Non Client to Server Packet :)
	}

	@Override
	public States getPacketState() {
		return States.INGAME;
	}

}
