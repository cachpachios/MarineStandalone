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

import java.util.ArrayList;
import java.util.List;

import org.marinemc.io.binary.ByteInput;
import org.marinemc.io.binary.ByteList;

/**
 * @author Fozie
 */
public class NBTList extends NBTTag {

	byte type;
	int size;
	List<NBTTag> data;

	public NBTList(final String name, final ByteInput data) {
		super(name, 9);
		this.data = new ArrayList<NBTTag>();
		type = data.readByte();
		size = data.readInt();

		final int i = 0;
		while (i < size)
			this.data.add(NBT.parse(data, type));

	}

	@Override
	public byte[] toByteArray() {
		final ByteList data = new ByteList();

		data.writeByte(getTagID());
		data.writeUTF8Short(name);
		data.writeByte(type);
		data.writeInt(size);

		for (final NBTTag tag : this.data)
			data.write(tag.toByteArray());

		return data.toBytes();
	}

	@Override
	public byte[] toNonPrefixedByteArray() {
		final ByteList data = new ByteList();
		data.writeByte(type);
		data.writeInt(size);

		for (final NBTTag tag : this.data)
			data.write(tag.toByteArray());
		return data.toBytes();
	}
}
