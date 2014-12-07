package com.marine.util;

/**
 * Created 2014-12-01 for MarineStandalone
 *
 * @author Citymonstret
 */
public abstract class Vector3<T> {

    private T x;
    private T y;
    private T z;

    public Vector3(T x, T y, T z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public abstract void add(Vector3<T> v2);

    public abstract void subtract(Vector3<T> v2);

    public abstract void multiply(int n);

    public abstract void divide(int n);

    public T getX() {
        return this.x;
    }

    public void setX(T x) {
        this.x = x;
    }

    public T getY() {
        return this.y;
    }

    public void setY(T y) {
        this.y = y;
    }

    public T getZ() {
        return this.z;
    }

    public void setZ(T z) {
        this.z = z;
    }
}
