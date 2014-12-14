///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// MarineStandalone is a minecraft server software and API.
// Copyright (C) IntellectualSites (marine.intellectualsites.com)
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

package com.marine.world.generators;

import com.marine.util.Location;
import com.marine.world.BlockID;
import com.marine.world.Dimension;
import com.marine.world.chunk.Chunk;
import com.marine.world.chunk.ChunkPos;

public final class TotalFlatGrassGenerator extends WorldGenerator {


    public TotalFlatGrassGenerator() {
    }

    @Override
    public Location getSafeSpawnPoint() {
        return new Location(world, 8, 5, 8);
    }

    @Override
    public Chunk generateChunk(int x, int y) { // Really need to be improved i guess it could be made in a less laggy way :S
        Chunk r = new Chunk(world, new ChunkPos(x, y));
        for (int xx = 0; xx < 16; xx++)
            for (int zz = 0; zz < 16; zz++) {
                r.setBlock(xx, 0, zz, BlockID.BEDROCK);
                r.setBlock(xx, 1, zz, BlockID.DIRT);
                r.setBlock(xx, 2, zz, BlockID.DIRT);
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
    public LevelType getLevelType() {
        return LevelType.FLAT;
    }

}
