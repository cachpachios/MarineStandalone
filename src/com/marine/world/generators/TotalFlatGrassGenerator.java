package com.marine.world.generators;

import com.marine.world.BlockID;
import com.marine.world.chunk.ChunkPos;
import com.marine.world.Dimension;
import com.marine.world.chunk.Chunk;

public final class TotalFlatGrassGenerator extends WorldGenerator{

	
	public TotalFlatGrassGenerator() {
	}

	@Override
	public Chunk generateChunk(int x, int y) { // Really need to be improved i guess it could be made in a less laggy way :S
		Chunk r = new Chunk(world, new ChunkPos(x,y));
		
		for(int xx = 0; xx < 16;xx++)
			for(int zz = 0; zz < 16;zz++) {
				r.setBlock(xx, 0, zz, BlockID.BEDROCK);
				r.setBlock(xx, 1, zz, BlockID.DIRT);
				r.setBlock(xx, 2, zz, 	BlockID.DIRT);
				r.setBlock(xx, 3, zz, BlockID.DIRT);
				r.setBlock(xx, 4, zz, BlockID.GRASS);
			}
				
		
		return r;
	}

	@Override
	public Dimension getDimension() {
		return Dimension.OVERWORLD;
	}

	@Override
	public Chunk[] generateRegion(int x, int y, int width, int height) {
		Chunk[] r = new Chunk[width*height];
		int i = 0;
		for(int xx = -(width/2); xx < width/2; xx++) 
			for(int yy = -(width/2); yy < width/2; yy++) {
					r[i] = generateChunk(x + xx, y + yy);
					i++;
			}
		return r;	
	}

	@Override
	public LevelType getLevelType() {
		return LevelType.FLAT;
	}

}
