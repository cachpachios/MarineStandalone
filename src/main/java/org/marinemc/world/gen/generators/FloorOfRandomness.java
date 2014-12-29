package org.marinemc.world.gen.generators;

import org.marinemc.util.Location;
import org.marinemc.world.BlockID;
import org.marinemc.world.Dimension;
import org.marinemc.world.Identifiers;
import org.marinemc.world.chunk.Chunk;
import org.marinemc.world.chunk.ChunkPos;
import org.marinemc.world.gen.LevelType;
import org.marinemc.world.gen.WorldGenerator;

public class FloorOfRandomness extends WorldGenerator {

	public FloorOfRandomness() {
		super(null);
	}

	@Override
	public LevelType getLevelType() {
		return LevelType.FLAT;
	}

	@Override
	public Dimension getDimension() {
		return Dimension.OVERWORLD;
	}

    @Override
    public Chunk generateChunkTerrain(final ChunkPos pos) {
        Chunk r = new Chunk(world, pos);
        for (int xx = 0; xx < 16; xx++)
            for (int zz = 0; zz < 16; zz++) {
            	
            	BlockID t = Identifiers.randomBlock();
            	
            	while(t == BlockID.AIR)
            		t = Identifiers.randomBlock();
            	
            	r.setPrivateType(xx, 0, zz, t);
                
                r.setPrivateLight(xx, 0, zz, (byte) -1);

            }
        return r;
    }


	@Override
	public Location getSafeSpawnPoint() {
		return new Location(world, 0, 5, 0);
	}

}
