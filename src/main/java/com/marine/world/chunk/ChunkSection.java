package com.marine.world.chunk;

import com.marine.io.data.ByteData;
import com.marine.world.BlockID;

public class ChunkSection {
    private int sectionID;

    private short[][][] blockMap;

    public ChunkSection(int y) {
        this.sectionID = y;
        this.blockMap = new short[16][16][16];
    }

    public ByteData getData(boolean skyLight) {
        ByteData data = new ByteData();
        int i = 0;
        for (int y = 0; y < 16; y++)
            for (int z = 0; z < 16; z++)
                for (int x = 0; x < 16; x++) {
                    data.writeShort(getBlockID(x,y,z));
                    if (skyLight)
                        data.writeend((byte) -1);
                    else if ((i & 2) == 0)
                        data.writeend((byte) -1);
                    i++;
                }


        return data;
    }

    public void setBlock(int x, int y, int z, BlockID id) {
        blockMap[x][y][z] = id.getNumericID();
    }

    public short getBlockID(int x, int y, int z) {
        return blockMap[x][y][z];
    }

    public BlockID getBlock(int x, int y, int z) {
        return BlockID.BEDROCK;
    }

    public int getID() {
        return sectionID;
    }

}