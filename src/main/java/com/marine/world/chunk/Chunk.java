package com.marine.world.chunk;

import com.marine.io.data.ByteData;
import com.marine.world.BiomeID;
import com.marine.world.BlockID;
import com.marine.world.World;

import java.util.Random;

public final class Chunk {
    private final World w;
    private final ChunkPos pos;

    private ChunkSection[] sections;
    private BiomeID[][] biomes;

    public Chunk(World w, ChunkPos pos) {
        this.w = w;
        this.pos = pos;
        this.sections = new ChunkSection[16];
        this.biomes = new BiomeID[16][16];
    }

    public void setBlock(int x, int y, int z, BlockID id) {
        int section = y >> 4;
        if (sections[section] == null)
            if (id != BlockID.AIR)
                sections[section] = new ChunkSection(section);
            else return;
        if(section > 0)
        	sections[section].setBlock(x, y / (section ), z, id);
        if(section == 0)
        	sections[section].setBlock(x, y, z, id);
    }

    public short getBlock(int x, int y, int z) {
        int section = y >> 4;
        if (sections[section] == null)
            return 0;
        else
            return sections[section].getBlockID(x, y / section, z);
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
        for(ChunkSection s : sections)
        	if(s != null)
        		r |= 1 << s.getID();
        return r;
    }

    public World getWorld() {
        return w;
    }

}
