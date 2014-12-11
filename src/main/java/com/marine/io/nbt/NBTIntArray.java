///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// MarineStandalone is a minecraft server software and API.
// Copyright (C) IntellectualSites (marine.intellectualsites.com)
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

package com.marine.io.nbt;

import com.marine.io.data.ByteData;

public class NBTIntArray implements NBTTag {

    private final String name;
    int[] array;
    private int size;

    public NBTIntArray(String name, ByteData data) {
        this.name = name;
        size = data.readInt();
        array = new int[size];
        for (int i = 0; i <= size; i++) {
            array[i] = data.readInt();
        }
    }

    public NBTIntArray(String name, int[] v) {
        this.name = name;
        array = v;
    }

    @Override
    public byte getTagID() {
        return 7;
    }

    public int[] getIntArray() {
        return array;
    }

    @Override
    public byte[] toByteArray() {
        ByteData d = new ByteData();
        d.writeByte(getTagID());
        d.writeUTF8Short(name);
        d.writeInt(array.length);
        for (int x : array)
            d.writeInt(x);
        return d.getBytes();
    }

    @Override
    public String getName() {
        return name;
    }


    @Override
    public byte[] toNonPrefixedByteArray() {
        ByteData d = new ByteData();
        d.writeInt(array.length);
        for (int x : array)
            d.writeInt(x);
        return d.getBytes();
    }
}
