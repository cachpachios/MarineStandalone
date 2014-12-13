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

package com.marine.world;

import com.marine.util.Location;
import com.marine.util.Position;
import com.marine.world.chunk.Chunk;

public class Block { // Help class for easier reading not used to save/set data

    private Position blockPos;
    private Chunk chunk;
    private int lighting;
    private BlockID type;

    public Block(Position blockPos, Chunk chunk, int lighting, BlockID type) {
        this.blockPos = blockPos;
        this.chunk = chunk;
        this.lighting = lighting;
        this.type = type;
    }

    public Block(Position pos, BlockID type) {
        this.blockPos = pos;
        // this.chunk = location.getWorld().getChunk(location.getX().intValue() >> 4, location.getZ().intValue() >> 4);
        this.type = type;
        this.lighting = -1;
    }

    public Block(Location location, BlockID type) {
        this.blockPos = location.getRelativePosition();
        this.chunk = location.getWorld().getChunk(location.getX().intValue() >> 4, location.getZ().intValue() >> 4);
        this.type = type;
        this.lighting = -1;
    }

    public static Block getAirBlock(Position pos, Chunk chunk) {
        return new Block(pos, chunk, 0, BlockID.AIR);
    }

    public Position getBlockPos() {
        return blockPos;
    }

    public void setChunk(Chunk chunk) {
        this.chunk = chunk;
    }

    public void setChunk(Chunk chunk) {
        this.chunk = chunk;
    }

    public Chunk getChunk() {
        return chunk;
    }

    public int getLighting() {
        return lighting;
    }

    public BlockID getType() {
        return type;
    }

    public int getNBTBlockPos() {
        return blockPos.getY() * 16 * 16 + blockPos.getZ() * 16 + blockPos.getX();
    }

    public int toPacketBlock() {
        return type.getID() << 4; // TODO Replace 0 with avalible metadata
    }

}

