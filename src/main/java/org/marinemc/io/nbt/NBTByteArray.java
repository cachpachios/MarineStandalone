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

import org.marinemc.io.data.ByteData;
/**
 * @author Fozie
 */
public class NBTByteArray extends NBTTag<Byte[]> {

    private ByteData array;

    public NBTByteArray(String name, ByteData data) {
        super(name, 7);
        int l = data.readInt();
        array = data.readData(l);
    }

    public NBTByteArray(String name, byte[] v) {
        this(name, new ByteData(v));
    }

    @Override
    public byte[] toByteArray() {
        ByteData d = new ByteData();
        d.writeByte(getTagID());
        d.writeUTF8Short(getName());
        d.writeInt(array.getLength());
        d.writeend(array.getBytes());
        return d.getBytes();
    }

    @Override
    public byte[] toNonPrefixedByteArray() {
        ByteData data = new ByteData();
        data.writeInt(array.getLength());
        data.writeend(array.getBytes());
        return data.getBytes();
    }
}
