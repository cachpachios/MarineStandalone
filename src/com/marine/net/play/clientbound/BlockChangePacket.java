package com.marine.net.play.clientbound;

import java.io.IOException;

import com.marine.io.data.ByteData;
import com.marine.net.Packet;
import com.marine.net.PacketOutputStream;
import com.marine.net.States;
import com.marine.util.Position;
import com.marine.world.Block;

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
	public void writeToStream(PacketOutputStream stream) throws IOException {
		
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
