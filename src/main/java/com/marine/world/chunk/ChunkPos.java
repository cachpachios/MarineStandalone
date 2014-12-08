package com.marine.world.chunk;

public class ChunkPos implements Comparable<ChunkPos> {
    private final int x, y;

    public ChunkPos(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public ChunkPos(long encoded) {
    	this((int)(encoded >> 32), (int) encoded);
    }
    
    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public long encode() {
    	return (long)x << 32 | y & 0xFFFFFFFFL;
    }
    public static long Encode(int x, int y) {
    	return (long)x << 32 | y & 0xFFFFFFFFL;
    }
    
    
    
    
    @Override
    public int compareTo(ChunkPos v) {
        if (x == v.x && y == v.y)
            return 0;
        if (x < v.x && y < v.y)
            return -1;
        else
            return 1;
    }
}
