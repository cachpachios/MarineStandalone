package com.marineapi.world;

import com.marineapi.util.Position;

public class Block {
	private Position blockPos;
	private Chunk chunk;
	
	private BlockData type;
	
	private int metadata;
	
	
	public int toPacketBlock() {
		return type.getID() << 4 | metadata;
	}
	
}

