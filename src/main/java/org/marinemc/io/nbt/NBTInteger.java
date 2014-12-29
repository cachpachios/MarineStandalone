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
public class NBTInteger extends NBTTag {
    private int data;

    public NBTInteger(String name, int v) {
        super(name, 3);
        data = v;
    }

    public NBTInteger(String name, ByteInput data) {
        this(name, data.readInt());
    }

    @Override
    public byte[] toByteArray() {
        ByteList d = new ByteList();
        d.writeByte(getTagID());
        d.writeUTF8Short(name);
        d.writeInt(data);
        return d.toBytes();
    }

    public int toInt() {
        return data;
    }

    @Override
    public byte[] toNonPrefixedByteArray() {
    	ByteList data = new ByteList();
        data.writeInt((this.data));
        return data.toBytes();
    }
}
