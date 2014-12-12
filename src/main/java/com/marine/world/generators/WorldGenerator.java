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
import com.marine.world.Dimension;
import com.marine.world.World;
import com.marine.world.chunk.Chunk;

public abstract class WorldGenerator {

    protected World world;

    public WorldGenerator() {
    }

    public void setWorld(World w) {
        world = w;
    }

    public Chunk[] generateRegion(int x, int y) {
        return generateRegion(x, y, 16, 16);
    }

    public abstract LevelType getLevelType();

    public abstract Chunk[] generateRegion(int x, int y, int width, int height);

    public abstract Dimension getDimension(); //TODO Enum for dimensions

    public abstract Chunk generateChunk(int x, int y);

    public abstract Location getSafeSpawnPoint();

}
