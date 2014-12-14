package com.marine.world.chunk;

import com.marine.util.Position;
import com.marine.world.BlockID;

public class GlobalBlock {
	private final Position pos;
	private final BlockID type;
	
	public GlobalBlock(final Position pos, final BlockID type) {
		this.pos = pos;
		this.type = type;
	}
	
	public BlockID getBlockID() { return type; }
	public Position getPos() { return pos; }
	
}
