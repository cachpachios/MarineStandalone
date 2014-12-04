package com.marine.net.play.clientbound;

import java.io.IOException;

import com.marine.io.data.ByteData;
import com.marine.net.Packet;
import com.marine.net.PacketOutputStream;
import com.marine.net.States;

public class TimeUpdatePacket extends Packet{
	final long worldTime;
	final long worldAge;
	
	public TimeUpdatePacket(long worldTime, long worldAge) {
		super();
		this.worldTime = worldTime;
		this.worldAge = worldAge;
	}

	@Override
	public int getID() {
		return 0x03;
	}

	@Override
	public void writeToStream(PacketOutputStream stream) throws IOException {
		ByteData d = new ByteData();
		d.writeVarInt(getID());
	}

	@Override
	public void readFromBytes(ByteData input) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public States getPacketState() {
		// TODO Auto-generated method stub
		return null;
	}

}
