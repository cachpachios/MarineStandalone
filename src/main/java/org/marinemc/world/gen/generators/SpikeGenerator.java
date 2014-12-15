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
import org.marinemc.world.World;
import org.marinemc.world.chunk.Chunk;
import org.marinemc.world.chunk.ChunkPos;
import org.marinemc.world.gen.LevelType;
import org.marinemc.world.gen.WorldGenerator;

/**
 * Created 2014-12-12 for MarineStandalone
 *
 * @author Citymonstret
 * @author Fozie
 */
public class SpikeGenerator extends WorldGenerator {

    private static final int H = 32;

    public SpikeGenerator(final World w) {
    	super(WorldGenerator.NO_POPULATION);
    }
    
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
    public Chunk generateChunkTerrain(ChunkPos pos) {
        Chunk chunk = new Chunk(world, pos);
        BlockID id = BlockID.AIR;
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
