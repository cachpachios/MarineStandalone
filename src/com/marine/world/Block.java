package com.marineapi.world;

import com.marineapi.util.Position;

public class Block { // Help class for easier reading not used to save/set data
	
	public Block(Position blockPos, Chunk chunk, int lighting, BlockID type) {
		this.blockPos = blockPos;
		this.chunk = chunk;
		this.lighting = lighting;
		this.type = type;
	}

	private Position blockPos;
	private Chunk chunk;
	
	private int lighting; 
	
	public Position getBlockPos() {
		return blockPos;
	}

	public Chunk getChunk() {
		return chunk;
	}

	public int getLighting() {
		return lighting;
	}

	public BlockID getType() {
		return type;
	}

	private BlockID type;
	
	public static Block getAirBlock(Position pos, Chunk chunk) {
		return new Block(pos,chunk,0,BlockID.AIR);
	}
	
	public int getNBTBlockPos() {
		return blockPos.y*16*16 + blockPos.z*16 + blockPos.x;
	}
	
	public int toPacketBlock() {
		return type.getID() << 4 | 0; // TODO Replace 0 with avalible metadata
	}
	
}

