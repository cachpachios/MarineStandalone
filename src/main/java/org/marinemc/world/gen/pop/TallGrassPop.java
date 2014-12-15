package org.marinemc.world.gen.pop;

import java.util.Random;

import org.marinemc.world.BlockID;
import org.marinemc.world.chunk.Chunk;
import org.marinemc.world.gen.ChunkPopulator;

public class TallGrassPop implements ChunkPopulator {

	@Override
	public void populate(Chunk c) {
		Random r = new Random();
		for(int x = 0; x < 16; x++)
			for(int y = 0; y < 16; y++)
				if(r.nextInt(4) == 0) {
					if(r.nextInt(4) != 0)
							if(r.nextInt(5) != 0)
								c.setBlock(x, (int) c.getMaxHeightAt(x, y)+1, y, BlockID.TALL_GRASS);
							else
								c.setBlock(x, (int) c.getMaxHeightAt(x, y)+1, y, BlockID.FERN);
						else
							if(r.nextBoolean()) {
								c.setBlock(x, (int) c.getMaxHeightAt(x, y)+1, y, BlockID.SUGERCANE);
								c.setBlock(x, (int) c.getMaxHeightAt(x, y)+1, y, BlockID.SUGERCANE);
								c.setBlock(x, (int) c.getMaxHeightAt(x, y)+1, y, BlockID.SUGERCANE);
								if(r.nextBoolean()) 
									c.setBlock(x, (int) c.getMaxHeightAt(x, y)+1, y, BlockID.SUGERCANE);
							}
							else
								c.setBlock(x, (int) c.getMaxHeightAt(x, y)+1, y, BlockID.YELLOW_FLOWER);
								
				}
	}
}
