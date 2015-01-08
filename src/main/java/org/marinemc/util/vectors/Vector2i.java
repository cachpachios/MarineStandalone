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
 * Integer Implementation of a 2D Vector
 *
 * @author Citymonstret
 */
public class Vector2i extends Vector2<Integer> {

	public Vector2i(final Integer x, final Integer y) {
		super(x, y);
	}

	@Override
	public void add(final Vector2<Integer> v2) {
		setX(getX() + v2.getX());
		setY(getY() + v2.getY());
	}

	@Override
	public void subtract(final Vector2<Integer> v2) {
		setX(getX() - v2.getX());
		setY(getY() - v2.getY());
	}

	@Override
	public void multiply(final int n) {
		setX(getX() * n);
		setY(getY() * n);
	}

	@Override
	public void divide(final int n) {
		setX(getX() / n);
		setY(getY() / n);
	}

	@Override
	public void divide(final Integer number) {
		x /= number;
		y /= number;
	}

	@Override
	public void multiply(final Integer number) {
		x *= number;
		y *= number;
	}

	@Override
	public void add(final int factor) {
		x += factor;
		y += factor;
	}

	@Override
	public void add(final Integer number) {
		x += number;
		y += number;
	}

	@Override
	public void sub(final int factor) {
		x -= factor;
		y -= factor;
	}

	@Override
	public void sub(final Integer number) {
		x -= number;
		y -= number;
	}

	@Override
	public Integer[] asArray() {
		return new Integer[] { x, y };
	}
}
