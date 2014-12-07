package com.marine.net.play.clientbound;

import com.marine.io.data.ByteData;
import com.marine.net.Packet;
import com.marine.net.PacketOutputStream;
import com.marine.net.States;
import com.marine.util.Position;
import com.marine.world.Block;
import com.marine.world.BlockID;

import java.io.IOException;

public class BlockChangePacket extends Packet {
	public Position pos;
	public int newBlock;
	
	public BlockChangePacket(Block toBlock) {
		this(toBlock.getBlockPos(), toBlock.getType().getPacketID());
	}

	public BlockChangePacket(Position pos, int b) {
		this.pos = pos;
		this.newBlock = b;
	}

	public BlockChangePacket(Position p, BlockID b) {
		this(p,b.getID());
	}

	@Override
	public int getID() {
		return 0x23;
	}

	@Override
	public void writeToStream(PacketOutputStream stream) throws IOException {
		
		ByteData data = new ByteData();

		data.writePosition(pos);
		data.writeVarInt(newBlock);
		
		stream.write(getID(), data.getBytes());
		
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
