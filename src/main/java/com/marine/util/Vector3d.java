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
        return new Vector3i(getX().intValue(), getY().intValue(), getZ().intValue());
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
