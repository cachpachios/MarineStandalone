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

/**
 * Created 2014-12-12 for MarineStandalone
 *
 * @author Citymonstret
 */
public abstract class Vector2<T extends Number> {

    public T x, y;

    public Vector2(final T x, final T y) {
        this.x = x;
        this.y = y;
    }

    public abstract void add(Vector2<T> v2);

    public abstract void subtract(Vector2<T> v2);

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

    @Override
    public int hashCode() {
        int hash = 37;
        hash = hash * 3 + getX().intValue();
        hash = hash * 3 + getY().intValue();
        return hash;
    }
}
