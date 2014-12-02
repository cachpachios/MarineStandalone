package com.marine.util;

public class Vector3i extends Vector3<Integer> {

	public Vector3i() {
		super(0, 0, 0);
	}
	
	public Vector3i(int t) {
		super(t, t, t);
	}

    public Vector3i(int x, int y, int z) {
        super(x, y, z);
    }

	public double getLength() {
		return Math.sqrt(getX() * getX() + getY() * getY() + getZ() * getZ());
	}
	
	public Vector3d toDoubleVector() {
		return new Vector3d(getX(), getY(), getZ());
	}

    @Override
    public void add(Vector3<Integer> v2) {
        setX(getX() + v2.getX());
        setY(getY() + v2.getY());
        setZ(getZ() + v2.getZ());
    }

    @Override
    public void subtract(Vector3<Integer> v2) {
        setX(getX() - v2.getX());
        setY(getY() - v2.getY());
        setZ(getZ() - v2.getZ());
    }

    @Override
    public void multiply(int n) {
        setX(getX() * n);
        setY(getY() * n);
        setZ(getZ() * n);
    }

    @Override
    public void divide(int n) {
        setX(getX() / n);
        setY(getY() / n);
        setZ(getZ() / n);
    }

}
