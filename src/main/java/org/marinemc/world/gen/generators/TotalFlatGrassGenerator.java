///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// MarineStandalone is a minecraft server software and API.
// Copyright (C) MarineMC (marinemc.org)
//
// This program is free software; you can redistribute it and/or modify
// it under the terms of the GNU General Public License as published by
// the Free Software Foundation; either version 2 of the License, or
// (at your option) any later version.
//
// This program is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
// GNU General Public License for more details.
//
// You should have received a copy of the GNU General Public License along
// with this program; if not, write to the Free Software Foundation, Inc.,
// 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

package org.marinemc.world.gen.generators;

import org.marinemc.util.Location;
import org.marinemc.world.BlockID;
import org.marinemc.world.Dimension;
import org.marinemc.world.chunk.Chunk;
import org.marinemc.world.chunk.ChunkPos;
import org.marinemc.world.gen.ChunkPopulator;
import org.marinemc.world.gen.LevelType;
import org.marinemc.world.gen.WorldGenerator;
import org.marinemc.world.gen.pop.NaturePop;
/**
 * @author Fozie
 */
public final class TotalFlatGrassGenerator extends WorldGenerator {


    public TotalFlatGrassGenerator() {
    	super(new ChunkPopulator[]{new NaturePop()});
    }

    @Override
    public Location getSafeSpawnPoint() {
        return new Location(world, 8, 5, 8);
    }

    @Override
    public Chunk generateChunkTerrain(final ChunkPos pos) { // Really need to be improved i guess it could be made in a less laggy way :S
        Chunk r = new Chunk(world, pos);
        for (int xx = 0; xx < 16; xx++)
            for (int zz = 0; zz < 16; zz++) {
                r.setPrivateType(xx, 0, zz, BlockID.BEDROCK);
                r.setPrivateType(xx, 1, zz, BlockID.DIRT);
                r.setPrivateType(xx, 2, zz, BlockID.DIRT);
                r.setPrivateType(xx, 3, zz, BlockID.DIRT);
                r.setPrivateType(xx, 4, zz, BlockID.GRASS);

                r.setPrivateLight(xx, 0, zz, (byte) -1);
                r.setPrivateLight(xx, 1, zz, (byte) -1);
                r.setPrivateLight(xx, 2, zz, (byte) -1);
                r.setPrivateLight(xx, 3, zz, (byte) -1);
                r.setPrivateLight(xx, 4, zz, (byte) -1);

            }
        return r;
    }

    @Override
    public Dimension getDimension() {
        return Dimension.OVERWORLD;
    }

    @Override
    public LevelType getLevelType() {
        return LevelType.FLAT;
    }

}
