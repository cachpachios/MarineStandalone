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

import java.util.ArrayList;
import java.util.List;
/**
 * @author Fozie
 */
public class NBTList implements NBTTag {

    private final String name;
    byte type;
    int size;
    List<NBTTag> data;

    public NBTList(String name, ByteData data) {
        this.name = name;
        this.data = new ArrayList<NBTTag>();
        this.type = data.readByte();
        this.size = data.readInt();

        int i = 0;
        while (i < size) {
            this.data.add(NBT.parse(data, type));
        }

    }

    @Override
    public byte getTagID() {
        return 9;
    }

    @Override
    public byte[] toByteArray() {
        ByteData data = new ByteData();

        data.writeByte(getTagID());
        data.writeUTF8Short(name);
        data.writeByte(type);
        data.writeInt(size);

        for (NBTTag tag : this.data)
            data.writeend(tag.toByteArray());


        return data.getBytes();
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public byte[] toNonPrefixedByteArray() {
        ByteData data = new ByteData();
        data.writeByte(type);
        data.writeInt(size);

        for (NBTTag tag : this.data)
            data.writeend(tag.toByteArray());
        return data.getBytes();
    }
}
