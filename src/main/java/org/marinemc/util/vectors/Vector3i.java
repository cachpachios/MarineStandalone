///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// MarineStandalone is a minecraft server software and API.
// Copyright (C) MarineMC (marinemc.org)
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

package org.marinemc.util.vectors;

/**
 * Integer version of a 3D Vector
 *
 * @author Citymonstret
 */
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