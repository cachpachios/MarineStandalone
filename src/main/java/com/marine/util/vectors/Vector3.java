///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// MarineStandalone is a minecraft server software and API.
// Copyright (C) IntellectualSites (marine.intellectualsites.com)
//
// This program is free software; you can redistribute it and/or modify
// it under the terms of the GNU General Public License as published by
// the Free Software Foundation; either version 2 of the License, or
// (at your option) any later version.
//
// This program is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
// GNU General Public License for more details.
//
// You should have received a copy of the GNU General Public License along
// with this program; if not, write to the Free Software Foundation, Inc.,
// 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

package com.marine.util.vectors;

/**
 * 3 Dimensional Vector
 *
 * @author Citymonstret
 */
public abstract class Vector3<T extends Number> implements Vector {

    public T x, y, z;

    /**
     * Constructor
     *
     * @param x X Value
     * @param y Y Value
     * @param z Z Value
     */
    public Vector3(T x, T y, T z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    /**
     * Subtract all values of the specified vector
     * from the current instance
     *
     * @param v2 Other instance
     */
    public abstract void add(Vector3<T> v2);

    /**
     * Subtract all values of the specified vector
     * from the current instance
     *
     * @param v2 Other instance
     */
    public abstract void subtract(Vector3<T> v2);

    @Override
    public abstract void multiply(int n);

    @Override
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

    @Override
    public int hashCode() {
        int hash = 37;
        hash = hash * 3 + getX().intValue();
        hash = hash * 3 + getY().intValue();
        hash = hash * 3 + getZ().intValue();
        return hash;
    }
}
