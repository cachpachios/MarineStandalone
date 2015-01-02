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
	public void add(Vector3<Byte> v2) {
		this.x = (byte) (x + v2.x);
		this.y = (byte) (y + v2.y);
		this.z = (byte) (z + v2.z);
	}

	@Override
	public void subtract(Vector3<Byte> v2) {
		this.x = (byte) (x - v2.x);
		this.y = (byte) (y - v2.y);
		this.z = (byte) (z - v2.z);
	}

	@Override
	public void multiply(int n) {
		this.x = (byte) (x * n);
		this.y = (byte) (y * n);
		this.z = (byte) (z * n);
	}

	@Override
	public void divide(int n) {
		this.x = (byte) (x / n);
		this.y = (byte) (y / n);
		this.z = (byte) (z / n);
	}

	@Override
	public void divide(Byte number) {
		this.x = (byte) (this.x / number);
		this.y = (byte) (this.y / number);
		this.z = (byte) (this.z / number);
	}

	@Override
	public void multiply(Byte number) {
		this.x = (byte) (this.x * number);
		this.y = (byte) (this.y * number);
		this.z = (byte) (this.z * number);
	}

	@Override
	public void add(int factor) {
		this.x = (byte) (this.x + factor);
		this.y = (byte) (this.y + factor);
		this.z = (byte) (this.z + factor);
	}

	@Override
	public void add(Byte number) {
		this.x = (byte) (this.x + number);
		this.y = (byte) (this.y + number);
		this.z = (byte) (this.z + number);
	}

	@Override
	public void sub(int factor) {
		this.x = (byte) (this.x - factor);
		this.y = (byte) (this.y - factor);
		this.z = (byte) (this.z - factor);
	}

	@Override
	public void sub(Byte number) {
		this.x = (byte) (this.x - number);
		this.y = (byte) (this.y - number);
		this.z = (byte) (this.z - number);
	}
}
