package com.marine.world.chunk;

import com.marine.world.BlockID;

public class ChunkSection {
	
	public static final int WIDTH = 16, HEIGHT = 16, DEPTH = 16;
	
	private int sectionID;
	
	private byte[][][] blockMap;
	
	public ChunkSection(int y) {
		this.sectionID = y;
		this.blockMap = new byte[WIDTH][HEIGHT][DEPTH];
	}
	
	public void setBlock(int x, int y, int z, BlockID id) {
		if(x > 16) return;
		if(y > 16) return;
		if(z > 16) return;
		
		blockMap[x][y][z] = id.getID();
	}
	
	public BlockID getBlock(int x, int y, int z) {
		return BlockID.AIR;
		
	}
	
	public int getID() {
		return sectionID;
	}
	
}