package com.marine.world.generators;

import com.marine.world.Chunk;
import com.marine.world.Dimension;
import com.marine.world.World;

public abstract class WorldGenerator {
	
	protected World world;
	
	public WorldGenerator(World w) {
		this.world = w;
	}
	
	public Chunk[] generateRegion(int x, int y) {
		return generateRegion(x,y,16,16);
	}
	
	public abstract Chunk[] generateRegion(int x, int y, int width, int height);
	
	public abstract Dimension getDimension(); //TODO Enum for dimensions
	
	public abstract Chunk generateChunk(int x, int y);
}
