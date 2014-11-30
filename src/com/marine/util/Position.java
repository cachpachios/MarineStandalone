package com.marineapi.util;

public class Position extends Vector {
	
	public Position(int x, int y, int z) {
		super(x,y,z);
	}
	
	public Position() {
		this(0,0,0);
	}
	
	public void decode(long l) {
		 x = (int) (l >> 38);
		 y = (int) ((l >> 26) & 0xFFF);
		 z = (int) (l << 38 >> 38);
	}
	
	public long encode() {
		return  ((x & 0x3FFFFFF) << 38) | ((y & 0xFFF) << 26) | (z & 0x3FFFFFF);
	}
	
}
