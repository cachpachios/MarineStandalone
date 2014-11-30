package com.marineapi.net.play.clientbound;

import java.io.IOException;
import java.io.OutputStream;

import com.marineapi.io.data.ByteData;
import com.marineapi.net.Packet;
import com.marineapi.net.States;
import com.marineapi.util.Position;
import com.marineapi.world.Block;

public class BlockChangePacket extends Packet {
	public Position pos;
	public Block newBlock;
	
	public BlockChangePacket(Position pos, Block toBlock) {
		this.pos = pos;
		this.newBlock = toBlock;
	}

	@Override
	public int getID() {
		return 0x23;
	}

	@Override
	public void writeToStream(OutputStream stream) throws IOException {
		
		ByteData data = new ByteData();

		data.writeVarInt(getID());
		
		data.writeVarInt(newBlock.toPacketBlock());
		data.writeLong(pos.encode());
		
		data.writePacketPrefix();
		
		stream.write(data.getBytes());
		
	}

	@Override
	public void readFromBytes(ByteData input) {
		// Serversent packet :)
	}

	@Override
	public States getPacketState() {
		return States.INGAME;
	}
	
}
