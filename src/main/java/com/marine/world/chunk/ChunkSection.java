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

package com.marine.world.chunk;

import com.marine.io.data.ByteData;
import com.marine.util.Position;
import com.marine.world.Block;
import com.marine.world.BlockID;

public class ChunkSection {
    public Chunk chunk;
    private int sectionID;
    private Block[] blockMap;

    public ChunkSection(int y) {
        this.sectionID = y;
        this.blockMap = new Block[16 * 16 * 16];
    }

    public ChunkSection(int y, Chunk chunk) {
        this.sectionID = y;
        this.chunk = chunk;
        this.blockMap = new Block[16 * 16 * 16];
    }

    public static int getIndex(int x, int y, int z) {
        return ((y & 0xf) << 8) | (z << 4) | x;
    }

    public ByteData getBlockData() {
        ByteData data = new ByteData();
        for (Block id : blockMap) {
            data.writeend((byte) (getID(id) & 0xff));
            data.writeend((byte) (getID(id) >> 8));
        }
        return data;
    }

    public ByteData getSkyLightData() {
        ByteData data = new ByteData();
        boolean skip = false;
        for (Block id : blockMap) { //TODO LightMap
            if (!skip) {
                data.writeend((byte) -1);
                skip = true;
            } else
                skip = false;
        }
        return data;
    }

    public ByteData getBlockLightData() {
        ByteData data = new ByteData();
        boolean skip = false;
        for (Block id : blockMap) { //TODO LightMap
            if (!skip) {
                data.writeend((byte) -1);
                skip = true;
            } else
                skip = false;
        }
        return data;
    }

    public void setBlock(int x, int y, int z, BlockID id) {
        // blockMap[getIndex(x, y, z)] = (char) (id.getID() << 4);
        setBlock(new Position(x, y, z), new Block(new Position(x, y, z), chunk, -1, id));
    }

    public void setBlock(Position pos, Block block) {
        blockMap[getIndex(pos.getX(), pos.getY(), pos.getZ())] = block;
    }

    public void setBlock(Block block) {
        setBlock(block.getBlockPos(), block);
    }

    public char getBlockID(int x, int y, int z) {
        return getID(blockMap[getIndex(x, y, z)]);
    }

    public BlockID getBlock(int x, int y, int z) {
        return BlockID.BEDROCK;
    }

    public Block getBlock(Position pos) {
        return blockMap[getIndex(pos.getX(), pos.getY(), pos.getZ())];
    }

    public int getID() {
        return sectionID;
    }

    public char getID(Block block) {
        if (block == null) return (char) 0;
        return (char) (block.getType().getID() << 4);
    }

}