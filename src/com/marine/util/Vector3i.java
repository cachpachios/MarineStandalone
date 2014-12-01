package com.marine.util;

public class Vector3i {
	public int x,y,z;
	
	public Vector3i(int x, int y, int z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public Vector3i() {
		this(0,0,0);
	}
	
	public Vector3i(int t) {
		this(t,t,t);
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getZ() {
		return z;
	}

	public void setZ(int z) {
		this.z = z;
	}
	
	public double getLength() {
		return Math.sqrt(x*x + y*y + z*z);
	}
	
	public Vector3d toDoubleVector() {
		return new Vector3d(x,y,z);
	}
	
}
