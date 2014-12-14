package com.marine.world.chunk;

import java.util.Arrays;

import com.marine.io.data.ByteData;
import com.marine.util.Hacky;
import com.marine.util.Position;
import com.marine.world.BlockID;
import com.marine.world.Identifiers;

/**
* Final storage of blocks,
*
* @author Fozie
*/ 
public final class ChunkSection {
	
    private final int sectionID;

    private final ChunkPos chunkPos;
    
    private final static int DATA_SIZE = 16*16*16;
    
    private char[] blockMap;
    private byte[] lightMap;

    
    public ChunkSection(final Chunk c, int y) {
    	this.chunkPos = c.getPos();
        this.sectionID = y;
        this.blockMap = new char[DATA_SIZE];
        this.lightMap = new byte[DATA_SIZE];
    }

    public ByteData getBlockData() { //TODO: Optimize
        ByteData data = new ByteData();
        for (char id : blockMap) {
            data.writeend((byte) (id & 0xff));
            data.writeend((byte) (id >> 8));
        }
    	return data;
    }
   
    public ByteData getLightData() { //TODO: Optimize
    	return new ByteData(lightMap);
    }

    public void setType(int x, int y, int z, BlockID id) {
        blockMap[getIndex(x,y,z)] = (char) (id.getID() << 4);
    }
    
    public void setLight(int x, int y, int z, byte light) {
        lightMap[getIndex(x,y,z)] = light;
    }

    public char getType(int x, int y, int z) {
        return blockMap[getIndex(x,y,z)];
    }

    @Hacky
    public BlockID getBlock(int x, int y, int z) {
        return Identifiers.getBlockID((byte)(getType(x,y,z)& 0xff));
    }

    public int getID() {
        return sectionID;
    }
    
    public void setPrivateCube(int x, int y, int z, int w, int d, int h, BlockID type) {
    	for(int xx = clamp(-(w/2),0,15); xx < (w/2); xx++)
        	for(int yy = clamp(-(h/2),0,15); yy < (h/2); yy++)
            	for(int zz = clamp(-(d/2),0,15); yy < (d/2); zz++)
            		setType(x+xx,y+yy,z+zz, type);
    }
    
    public GlobalBlock[] setCube(int x, int y, int z, int w, int d, int h, BlockID type) {
    	final GlobalBlock[] r = new GlobalBlock[w*d*h];
    	int i = 0;
    	for(int xx = clamp(-(w/2),0,15); xx < (w/2); xx++)
        	for(int yy = clamp(-(h/2),0,15); yy < (h/2); yy++)
            	for(int zz = clamp(-(d/2),0,15); yy < (d/2); zz++) {
            		setType(x+xx,y+yy,z+zz, type);
            		r[++i] = new GlobalBlock(getGlobalBlockPos(x+xx,y+yy,z+zz),type);
            	}
    	return r;
    }
    
    public Position getGlobalBlockPos(int x, int y, int z) {
    	int cX = chunkPos.getX() + 1;
    	int cY = chunkPos.getX() + 1;
    	
    	if(cX == 0) cX = -1;
    	if(cY == 0) cY = -1;
    	
    	return new Position(x*cX, y * (sectionID + 1), z * cY);
    }
    
    public void fillSection(BlockID type) {
    	Arrays.fill(blockMap, (char) (type.getID() << 4));
    }
    
    public static int getIndex(int x, int y, int z) {
        return ((y & 0xf) << 8) | (z << 4) | x;
    }
    
    public static final <T extends Comparable<T>> T clamp(T v, T min, T max) {
    	if(v.compareTo(max) == 1)
    		return max;
    	else if(v.compareTo(min) == -1)
    		return min;
    	else
    		return v;	
    }
}