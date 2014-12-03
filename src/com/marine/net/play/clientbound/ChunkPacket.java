package com.marine.net.play.clientbound;

import java.io.IOException;
import java.io.OutputStream;

import com.marine.io.data.ByteData;
import com.marine.net.Packet;
import com.marine.net.States;
import com.marine.world.Chunk;

public class ChunkPacket extends Packet{
	final Chunk c;
	
	public ChunkPacket(Chunk chunk) {
		this.c = chunk;
	}

	@Override
	public int getID() {
		return 0x21;
	}

	@Override
	public void writeToStream(OutputStream stream) throws IOException {
		ByteData d = new ByteData();
		
		d.writeVarInt(getID());
		
		c.getPos();
		
		d.writeInt(c.getPos().getX());
		d.writeInt(c.getPos().getY());
		
		d.writeShort(c.getSectionBitMap());
		
		byte[] data = c.getByteData(true);
		
		d.writeVarInt(data.length);
		
		d.writeByte(data);
		
		d.writePacketPrefix();
		
		stream.write(d.getBytes());
	}

	@Override
	public void readFromBytes(ByteData input) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public States getPacketState() {
		return States.INGAME;
	}
}
