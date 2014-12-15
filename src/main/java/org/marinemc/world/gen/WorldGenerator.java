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

package org.marinemc.world.gen;

import org.marinemc.util.Location;
import org.marinemc.world.Dimension;
import org.marinemc.world.World;
import org.marinemc.world.chunk.Chunk;
import org.marinemc.world.chunk.ChunkPos;

/**
 * @author Fozie
 */
public abstract class WorldGenerator {

	public final static ChunkPopulator[] NO_POPULATION = new ChunkPopulator[]{new ChunkPopulator() {@Override public void populate(Chunk c) {} }};
	
    protected World world;
    
    private ChunkPopulator[] populators;

    public WorldGenerator(final ChunkPopulator[] populators) {
    	this.populators = populators;
    }
    
    public void setGenerationWorld(World w) {
    	world = w;
    }
    
    
    public Chunk[] generateRegion(int x, int y) {
        return generateRegion(x, y, 16, 16);
    }

    public abstract LevelType getLevelType();

    public Chunk[] generateRegion(int x, int y, int width, int height) {
        Chunk[] r = new Chunk[width * height];
        int i = 0;
        for (int xx = -(width / 2); xx < width / 2; xx++)
            for (int yy = -(width / 2); yy < width / 2; yy++) {
                r[i] = generateChunk(new ChunkPos(x + xx, y + yy));
                i++;
            }
        return r;
    }

    public abstract Dimension getDimension(); //TODO Enum for dimensions

    public void populateChunk(Chunk c) {
    	if(populators == null)
    		return;
    	for(ChunkPopulator pop : populators)
    		pop.populate(c);
    }

    public Chunk generateChunk(final ChunkPos pos) {
    	Chunk c = generateChunkTerrain(pos);
    	populateChunk(c);
    	return c;
    }
    
    public abstract Chunk generateChunkTerrain(final ChunkPos pos);
    
    public abstract Location getSafeSpawnPoint();


}
