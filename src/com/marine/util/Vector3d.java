package com.marine.util;

public class Vector3d extends Vector3<Double> {

    public Vector3d() {
		super(0d, 0d, 0d);
	}

    public Vector3d(int x, int y, int z) {
        super((double) x, (double) y, (double) z);
    }

    public Vector3d(double x, double y, double z) {
        super(x, y, z);
    }

	public Vector3d(double t) {
		super(t, t, t);
	}

	public double getLength() {
		return Math.sqrt(getX() * getX() + getY() * getY() + getZ() * getZ());
	}
	
	public Vector3i toIntVector() {
		return new Vector3i(getX().intValue(),getY().intValue(), getZ().intValue());
	}

    @Override
    public void add(Vector3<Double> v2) {
        setX(getX() + v2.getX());
        setY(getY() + v2.getY());
        setZ(getZ() + v2.getZ());
    }

    @Override
    public void subtract(Vector3<Double> v2) {
        setX(getX() - v2.getX());
        setY(getX() - v2.getY());
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
