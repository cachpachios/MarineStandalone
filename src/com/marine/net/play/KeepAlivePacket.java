package com.marineapi.net.play;

import java.io.IOException;
import java.io.OutputStream;

import com.marineapi.io.data.ByteData;
import com.marineapi.net.Packet;
import com.marineapi.net.States;

public class KeepAlivePacket extends Packet {
	
	int aliveID;
	
	public KeepAlivePacket(int id) {
		this.aliveID = id;
	}
	
	public KeepAlivePacket() {
		this.aliveID = -1;
	}
	
	@Override
	public int getID() {
		return 0x00;
	}

	@Override
	public void writeToStream(OutputStream stream) throws IOException {
		ByteData d = new ByteData();
		
		d.writeVarInt(getID());
		
		d.writeVarInt(aliveID);
		
		d.writePacketPrefix();
		
		stream.write(d.getBytes());
	}

	@Override
	public void readFromBytes(ByteData input) {
		
	}

	@Override
	public States getPacketState() {
		return States.INGAME;
	}

}
