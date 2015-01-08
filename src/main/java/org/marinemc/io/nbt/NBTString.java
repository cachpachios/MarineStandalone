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
import org.marinemc.io.binary.ByteUtils;

/**
 * @author Fozie
 */
public class NBTString extends NBTTag<String> {

	String string;

	public NBTString(final String name, final String v) {
		super(name, 8);
		string = v;
	}

	public NBTString(final String name, final ByteInput data) {
		this(name, ByteUtils.readUTF8Short(data));
	}

	@Override
	public byte[] toByteArray() {
		final ByteList d = new ByteList();
		d.writeByte(getTagID());
		d.writeUTF8Short(name);
		d.writeUTF8Short(string);
		return d.toBytes();
	}

	@Override
	public byte[] toNonPrefixedByteArray() {
		final ByteList data = new ByteList();
		data.writeUTF8Short(string);
		return data.toBytes();
	}

}
