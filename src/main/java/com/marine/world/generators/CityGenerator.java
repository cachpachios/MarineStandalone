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

/**
 * Created 2014-12-12 for MarineStandalone
 *
 * @author Citymonstret
 */
public class CityGenerator extends WorldGenerator {

    private static final int H = 32;

    @Override
    public LevelType getLevelType() {
        return LevelType.FLAT;
    }

    @Override
    public Dimension getDimension() {
        return Dimension.OVERWORLD;
    }

    private int getRandom100() {
        return 1 + (int) (Math.random() * 100);
    }

    @Override
    public Location getSafeSpawnPoint() {
        return new Location(world, 8, H + 2, 8);
    }

    @Override
    public Chunk generateChunk(int xCo, int yCo) {
        Chunk chunk = new Chunk(world, new ChunkPos(xCo, yCo));
        BlockID id;
        for (int y = 1; y < H; y++) {
            for (int x = 0; x < 16; x++) {
                for (int z = 0; z < 16; z++) {
                    if (y == 1) {
                        id = BlockID.BEDROCK;
                    } else if (y == 2) {
                        id = BlockID.STONE;
                    } else if (y <= 10) {
                        if (chunk.getBlock(x, y - 1, z) == BlockID.AIR.getNumericID() || getRandom100() < 25)
                            id = BlockID.AIR;
                        else
                            id = BlockID.STONE;
                    } else if (x <= 16) {
                        if (chunk.getBlock(x, y - 1, z) == BlockID.AIR.getNumericID() || getRandom100() < 50)
                            id = BlockID.AIR;
                        else
                            id = BlockID.STONE;
                    } else {
                        if (chunk.getBlock(x, y - 1, z) == BlockID.AIR.getNumericID() || isAirDrr(chunk, x, y, z) > .75 || getRandom100() < 75)
                            id = BlockID.AIR;
                        else
                            id = BlockID.GLASS;
                    }
                    chunk.setPrivateType(x, y, z, id);
                    chunk.setPrivateLight(x, y, z, (byte) -1);
                }
            }
        }
        return chunk;
    }

    private boolean isAir(Chunk c, int x, int y, int z) {
        return !(x < 1 || y < 1 || z < 1) && c.getBlock(x, y, z) == BlockID.AIR.getNumericID();
    }

    private double isAirDrr(Chunk c, int x, int y, int z) {
        double d = 0;
        if (isAir(c, x - 1, y, z)) d += 0.25;
        if (isAir(c, x, y, z - 1)) d += 0.25;
        if (isAir(c, x + 1, y, z)) d += 0.25;
        if (isAir(c, x, y, z + 1)) d += 0.25;
        return d;
    }
}
