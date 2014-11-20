package com.marineapi.world;

import com.marineapi.ServerProperties;
import com.marineapi.util.Position;

public class Chunk {
	private World world;
	
	private Block[][][] blocks;
	
	//TODO: Entities etc
	
	public static final int SIZE = 16;
	
	public Chunk(World w) {
		world = w;
		blocks = new Block[SIZE][ServerProperties.MAX_Y][SIZE];
	}
	
	//TODO: Construct chunk using nbt data
	
	public World getWorld() {
		return world;
	}
	
	public Block getBlockAt(int x, int y, int z) {
		if(x > 16)
			return null;
		if(y > 16)
			return null;
		if(z > 16)
			return null;
		return blocks[x][y][z];
	}
	
	public Block getBlockAt(Position p) {
		return getBlockAt(p.getX(),p.getY(),p.getZ());
	}
	
}
