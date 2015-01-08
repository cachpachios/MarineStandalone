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

package org.marinemc.io.nbt;

import org.marinemc.io.binary.ByteInput;
import org.marinemc.io.binary.ByteList;

/**
 * @author Fozie
 */
public class NBTIntArray extends NBTTag<Integer[]> {

	int[] array;
	private int size;

	public NBTIntArray(final String name, final ByteInput data) {
		super(name, 7);
		size = data.readInt();
		array = new int[size];
		for (int i = 0; i <= size; i++)
			array[i] = data.readInt();
	}

	public NBTIntArray(final String name, final int[] v) {
		super(name, 7);
		array = v;
	}

	public int[] getIntArray() {
		return array;
	}

	@Override
	public byte[] toByteArray() {
		final ByteList d = new ByteList();
		d.writeByte(getTagID());
		d.writeUTF8Short(name);
		d.writeInt(array.length);
		for (final int x : array)
			d.writeInt(x);
		return d.toBytes();
	}

	@Override
	public byte[] toNonPrefixedByteArray() {
		final ByteList d = new ByteList();
		d.writeInt(array.length);
		for (final int x : array)
			d.writeInt(x);
		return d.toBytes();
	}
}
