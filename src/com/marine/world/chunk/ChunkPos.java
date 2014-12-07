package com.marine.world.chunk;

public class ChunkPos implements Comparable<ChunkPos>{
	private final int x,y;
	
	public ChunkPos(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	@Override
	public int compareTo(ChunkPos v) {
		if(x==v.x && y == v.y)
			return 0;
		if(x<v.x && y < v.y)
			return -1;
		else
			return 1;
	}
}
