package com.marine.net.handshake;

import java.io.IOException;

import com.marine.io.data.ByteData;
import com.marine.net.Packet;
import com.marine.net.PacketOutputStream;
import com.marine.net.States;

public class PingPacket extends Packet{
	
	protected long TIME;
	
	@Override
	public int getID() {
		return 0x01;
	}

	@Override
	public void writeToStream(PacketOutputStream stream) throws IOException {
		ByteData data = new ByteData();
		data.writeVarInt(getID());
		data.writeLong(TIME);
		data.writePacketPrefix();
		
		stream.write(data.getBytes());
	}

	@Override
	public void readFromBytes(ByteData input) {
		TIME = input.readLong();
	}

	@Override
	public States getPacketState() {
		return States.INTRODUCE;
	}
	
}
