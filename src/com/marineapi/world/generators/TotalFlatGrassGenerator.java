package com.marineapi.world.generators;

import com.marineapi.world.BlockID;
import com.marineapi.world.Chunk;
import com.marineapi.world.ChunkPos;
import com.marineapi.world.World;

public final class TotalFlatGrassGenerator extends WorldGenerator{

	
	public TotalFlatGrassGenerator(World w) {
		super(w);
	}

	@Override
	public Chunk generateChunk(int x, int z) {
		Chunk r = new Chunk(world, new ChunkPos(x,z));
		
		for(int xx = 0; xx < 16;xx++)
			for(int zz = 0; zz < 16;zz++) {
				r.setTypeAt(xx, 0, zz, BlockID.BEDROCK);
				r.setTypeAt(xx, 1, zz, BlockID.DIRT);
				r.setTypeAt(xx, 2, zz, BlockID.DIRT);
				r.setTypeAt(xx, 3, zz, BlockID.DIRT);
				r.setTypeAt(xx, 2, zz, BlockID.GRASS);
			}
				
		
		return r;
	}

}
