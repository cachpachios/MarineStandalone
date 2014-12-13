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
 * Created 2014-12-12 for MarineStandalone
 *
 * @author Citymonstret
 */
public class Vector2i extends Vector2<Integer> {

    public Vector2i(Integer x, Integer y) {
        super(x, y);
    }

    @Override
    public void add(Vector2<Integer> v2) {
        setX(getX() + v2.getX());
        setY(getY() + v2.getY());
    }

    @Override
    public void subtract(Vector2<Integer> v2) {
        setX(getX() - v2.getX());
        setY(getY() - v2.getY());
    }

    @Override
    public void multiply(int n) {
        setX(getX() * n);
        setY(getY() * n);
    }

    @Override
    public void divide(int n) {
        setX(getX() / n);
        setY(getY() / n);
    }
}
