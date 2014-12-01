package com.marine.world.generators;

import com.marine.world.BlockID;
import com.marine.world.Chunk;
import com.marine.world.ChunkPos;
import com.marine.world.Dimension;
import com.marine.world.World;

public final class TotalFlatGrassGenerator extends WorldGenerator{

	
	public TotalFlatGrassGenerator(World w) {
		super(w);
	}

	@Override
	public Chunk generateChunk(int x, int z) { // Really need to be improved i guess it could be made in a less laggy way :S
		Chunk r = new Chunk(world, new ChunkPos(x,z));
		
		for(int xx = 0; xx < 16;xx++)
			for(int zz = 0; zz < 16;zz++) {
				r.setTypeAt(xx, 0, zz, BlockID.BEDROCK);
				r.setTypeAt(xx, 1, zz, BlockID.DIRT);
				r.setTypeAt(xx, 2, zz, 	BlockID.DIRT);
				r.setTypeAt(xx, 3, zz, BlockID.DIRT);
				r.setTypeAt(xx, 2, zz, BlockID.GRASS);
			}
				
		
		return r;
	}

	@Override
	public Dimension getDimension() {
		return Dimension.OVERWORLD;
	}

}
