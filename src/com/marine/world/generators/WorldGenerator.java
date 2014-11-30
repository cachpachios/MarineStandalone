package com.marineapi.world.generators;

import com.marineapi.world.Chunk;
import com.marineapi.world.World;

public abstract class WorldGenerator {
	
	protected World world;
	
	public WorldGenerator(World w) {
		this.world = w;
	}
	
	public abstract Chunk generateChunk(int x, int y);
}
