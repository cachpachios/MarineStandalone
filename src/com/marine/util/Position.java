package com.marine.util;

public class Position extends Vector3i {
	
	public Position(int x, int y, int z) { // Used for relative posisitions (Blocks etc)
		super(x,y,z);
	}
	
	public Position() {
		this(0,0,0);
	}
	
	public void decode(long l) {
		 setX((int) (l >> 38));
		 setY((int) ((l >> 26) & 0xFFF));
		 setZ((int) (l << 38 >> 38));
	}
	
	public long encode() {
		return  ((getX() & 0x3FFFFFF) << 38) | ((getY() & 0xFFF) << 26) | (getZ() & 0x3FFFFFF);
	}

	public Location toLocation() {
		return new Location(null, getX(), getY(), getZ(),0,0);
	}
	
}
