package com.marineapi.net.handshake;

import java.io.IOException;
import java.io.OutputStream;

import com.marineapi.net.Packet;
import com.marineapi.net.States;
import com.marineapi.net.data.ByteData;
import com.marineapi.net.data.ByteEncoder;

public class PingPacket extends Packet{
	
	protected long TIME;
	
	@Override
	public int getID() {
		return 0x01;
	}

	@Override
	public void writeToStream(OutputStream stream) throws IOException {
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
