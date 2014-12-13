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
import com.marine.world.BiomeID;
import com.marine.world.Block;
import com.marine.world.BlockID;
import com.marine.world.World;
import com.marine.world.entity.Entity;

import java.util.ArrayList;
import java.util.List;

public final class Chunk {
    private final World w;
    private final ChunkPos pos;

    private ChunkSection[] sections;
    private BiomeID[][] biomes;

    private List<Entity> entities;

    public Chunk(World w, ChunkPos pos) {
        this.w = w;
        this.pos = pos;
        this.sections = new ChunkSection[16];
        this.biomes = new BiomeID[16][16];
        this.entities = new ArrayList<Entity>();
    }

    public void addEntity(Entity e) {
        if (!(e.getX() / 16 == pos.getX() && e.getZ() / 16 == pos.getY())) // Check if Entity is inside chunk
            return;
        entities.add(e);
    }

    public void setBlock(int x, int y, int z, Block block) {
        block.getBlockPos().setX(x);
        block.getBlockPos().setY(y);
        block.getBlockPos().setZ(z);
        block.setChunk(this);
        int section = y >> 4;
        if (sections[section] == null)
            if (block.getType() != BlockID.AIR)
                sections[section] = new ChunkSection(section);
            else return;
        if (section > 0)
            sections[section].setBlock(block);
    }

    public void setBlock(int x, int y, int z, BlockID id) {
        int section = y >> 4;
        if (sections[section] == null)
            if (id != BlockID.AIR)
                sections[section] = new ChunkSection(section);
            else return;
        if (section > 0)
            sections[section].setBlock(x, y / (section), z, id);
        if (section == 0)
            sections[section].setBlock(x, y, z, id);
    }

    public Block getBlock(Position pos) {
        int section = pos.getY() >> 4;
        if (sections[section] == null)
            return null;
        return sections[section].getBlock(new Position(pos.getX(), section == 0 ? pos.getY() : pos.getY() / section, pos.getZ()));
    }

    public short getBlock(int x, int y, int z) {
        int section = y >> 4;
        if (sections[section] == null)
            return 0;
        else
            return (short) sections[section].getBlockID(x, (section == 0) ? y : y / section, z);
    }

    public ByteData getData(boolean biomes, boolean skyLight) {
        ByteData d = new ByteData();

        for (ChunkSection s : sections) {
            if (s != null)
                d.writeData(s.getBlockData());
        }
        for (ChunkSection s : sections) {
            if (s != null)
                d.writeData(s.getBlockLightData());
        }
        for (ChunkSection s : sections) {
            if (s != null)
                d.writeData(s.getSkyLightData());
        }

        if (biomes)
            d.writeData(getBiomeData());

        return d;

    }

    public ByteData getBiomeData() {
        ByteData d = new ByteData();
        for (int z = 0; z < 16; z++)
            for (int x = 0; x < 16; x++)
                if (biomes[x][z] != null)
                    d.writeend(biomes[x][z].getID());
                else
                    d.writeend(BiomeID.PLAINS.getID());
        return d;
    }

    public ChunkPos getPos() {
        return pos;
    }

    public short getSectionBitMap() {
        short r = 0;
        for (ChunkSection s : sections)
            if (s != null)
                r |= 1 << s.getID();
        return r;
    }

    public World getWorld() {
        return w;
    }

}
