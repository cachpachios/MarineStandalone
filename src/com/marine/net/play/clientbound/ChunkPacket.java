package com.marine.net.play.clientbound;

import java.io.IOException;

import com.marine.io.data.ByteData;
import com.marine.net.Packet;
import com.marine.net.PacketOutputStream;
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
	public void writeToStream(PacketOutputStream stream) throws IOException {
		ByteData d = new ByteData();
		
		//d.writeInt(c.getPos().getX());
		//d.writeInt(c.getPos().getY());
		
		//d.writeShort(c.getSectionBitMap());
		
		//byte[] data = c.getByteData(true);
		
		//d.writeVarInt(data.length);
		
		//d.writeByte(data);
		
		stream.write(getID(), d);
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
