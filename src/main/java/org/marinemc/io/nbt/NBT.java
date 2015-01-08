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

import java.io.File;
import java.io.IOException;

import org.marinemc.io.BinaryFile;
import org.marinemc.io.binary.ByteInput;
import org.marinemc.io.binary.ByteList;
import org.marinemc.io.binary.ByteUtils;

/**
 * @author Fozie
 * @author Citymonstret
 */
public class NBT {

	NBTCompound tag;

	public NBT(final ByteInput data) throws IOException {
		final NBTTag preTag = parse(data);
		if (!(preTag instanceof NBTCompound))
			throw new IOException("File was in wrong format");
		else
			tag = (NBTCompound) preTag;
	}

	public NBT(final String name) {
		tag = new NBTCompound(name);
	}

	public NBT(final File f) throws IOException {
		this(new BinaryFile(f).readGZIPBinary().getData());
	}

	public static NBTTag parse(final ByteInput data) {
		return parse(data, data.readByte());
	}

	public static NBTTag parse(final ByteInput data, final byte id) {
		final String s = ByteUtils.readUTF8Short(data);
		switch (id) {
		case 1:
			return new NBTByte(s, data);
		case 2:
			return new NBTShort(s, data);
		case 3:
			return new NBTInteger(s, data);
		case 4:
			return new NBTLong(s, data);
		case 5:
			return new NBTFloat(s, data);
		case 6:
			return new NBTDouble(s, data);
		case 7:
			return new NBTByteArray(s, data);
		case 8:
			return new NBTString(s, data);
		case 9:
		case 11:
			return new NBTList(s, data);
		case 10:
			return new NBTCompound(s, data);
		default:
			return null;
		}
	}

	public void save(final File fPath, final boolean compress)
			throws IOException {
		final ByteList data = new ByteList();
		data.write(tag.toByteArray());
		final BinaryFile f = new BinaryFile(fPath, data);
		if (compress)
			f.writeGZIPBinary();
		else
			f.writeBinary();
	}

	public void setMainCompound(final NBTCompound tag) {
		this.tag = tag;
	}

}
