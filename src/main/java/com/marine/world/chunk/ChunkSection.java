package com.marine.world.chunk;

import com.marine.io.data.ByteData;
import com.marine.world.BlockID;

public class ChunkSection {
    private int sectionID;

    private char[] blockMap;

    public ChunkSection(int y) {
        this.sectionID = y;
        this.blockMap = new char[16*16*16];
    }

    public ByteData getBlockData() {
        ByteData data = new ByteData();
        for (char id : blockMap) {
            data.writeend((byte) (id & 0xff));
            data.writeend((byte) (id >> 8));
        }
    	return data;
    }
   
    public ByteData getSkyLightData() {
    	ByteData data = new ByteData();
    	boolean skip = false;
        for (char id : blockMap) { //TODO LightMap
        	if(!skip) {
        	data.writeend((byte) -1);
        	skip = true;
        	}
        	else 
        		skip = false;
        }
    	return data;
    }
    public ByteData getBlockLightData() {
    	ByteData data = new ByteData();
    	boolean skip = false;
        for (char id : blockMap) { //TODO LightMap
        	if(!skip) {
        	data.writeend((byte) -1);
        	skip = true;
        	}
        	else 
        		skip = false;
        }
    	return data;
    }

    public void setBlock(int x, int y, int z, BlockID id) {
        blockMap[getIndex(x,y,z)] = (char) (id.getID() << 4);
    }

    public char getBlockID(int x, int y, int z) {
        return blockMap[getIndex(x,y,z)];
    }

    public BlockID getBlock(int x, int y, int z) {
        return BlockID.BEDROCK;
    }

    public int getID() {
        return sectionID;
    }
    
    public static int getIndex(int x, int y, int z) {
        return ((y & 0xf) << 8) | (z << 4) | x;
    }

}