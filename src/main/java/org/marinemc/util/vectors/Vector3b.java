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
 * Byte Implementation of Vector3
 *
 * @author Fozie
 * @author Citymonstret
 */
public class Vector3b extends Vector3<Byte> {

	public Vector3b(final byte x, final byte y, final byte z) {
		super(x, y, z);
	}

	@Override
	public void add(final Vector3<Byte> v2) {
		x = (byte) (x + v2.x);
		y = (byte) (y + v2.y);
		z = (byte) (z + v2.z);
	}

	@Override
	public void subtract(final Vector3<Byte> v2) {
		x = (byte) (x - v2.x);
		y = (byte) (y - v2.y);
		z = (byte) (z - v2.z);
	}

	@Override
	public void multiply(final int n) {
		x = (byte) (x * n);
		y = (byte) (y * n);
		z = (byte) (z * n);
	}

	@Override
	public void divide(final int n) {
		x = (byte) (x / n);
		y = (byte) (y / n);
		z = (byte) (z / n);
	}

	@Override
	public void divide(final Byte number) {
		x = (byte) (x / number);
		y = (byte) (y / number);
		z = (byte) (z / number);
	}

	@Override
	public void multiply(final Byte number) {
		x = (byte) (x * number);
		y = (byte) (y * number);
		z = (byte) (z * number);
	}

	@Override
	public void add(final int factor) {
		x = (byte) (x + factor);
		y = (byte) (y + factor);
		z = (byte) (z + factor);
	}

	@Override
	public void add(final Byte number) {
		x = (byte) (x + number);
		y = (byte) (y + number);
		z = (byte) (z + number);
	}

	@Override
	public void sub(final int factor) {
		x = (byte) (x - factor);
		y = (byte) (y - factor);
		z = (byte) (z - factor);
	}

	@Override
	public void sub(final Byte number) {
		x = (byte) (x - number);
		y = (byte) (y - number);
		z = (byte) (z - number);
	}
}
