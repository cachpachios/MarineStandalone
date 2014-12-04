package com.marine.net.play.clientbound;

import java.io.IOException;
import java.util.List;

import com.marine.io.data.ByteData;
import com.marine.net.Packet;
import com.marine.net.PacketOutputStream;
import com.marine.net.States;
import com.marine.world.Chunk;
import com.marine.world.Dimension;
import com.marine.world.World;

public class MapChunkPacket extends Packet {

	List<Chunk> chunks;
	
	World world;
	
	public MapChunkPacket(World w, List<Chunk> chunks) {
		this.chunks = chunks;
		world = w;
	}
	
	@Override
	public int getID() {
		return 0x26;
	}

	@Override
	public void writeToStream(PacketOutputStream stream) throws IOException {
		ByteData data = new ByteData();
		
		data.writeVarInt(getID());
		
		data.writeBoolean(world.getDimension() == Dimension.OVERWORLD);
		
		data.writeVarInt(chunks.size());
		
		for(Chunk c : chunks) {
			//Write Chunk metadata
			data.writeInt(c.getPos().getX());
			data.writeInt(c.getPos().getY());
			data.writeShort(c.getSectionBitMap());
		
			// Write Data length
			data.writeVarInt(c.getByteData(true).length);
			
			// Write Chunk data
			data.writeend(c.getByteData(true));
		}
		
		data.writePacketPrefix();
	}

	@Override
	public void readFromBytes(ByteData input) {}

	@Override
	public States getPacketState() {
		return States.INGAME;
	}

}
