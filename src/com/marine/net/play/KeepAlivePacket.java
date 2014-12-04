package com.marine.net.play;

import java.io.IOException;

import com.marine.io.data.ByteData;
import com.marine.net.Packet;
import com.marine.net.PacketOutputStream;
import com.marine.net.States;

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
	public void writeToStream(PacketOutputStream stream) throws IOException {
		ByteData d = new ByteData();
		
		d.writeVarInt(aliveID);
		
		
		stream.write(getID(), d.getBytes());
	}

	@Override
	public void readFromBytes(ByteData input) {
		
	}

	@Override
	public States getPacketState() {
		return States.INGAME;
	}

}
