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

public class NBTByte implements NBTTag {

    private final String name;
    private byte data;

    public NBTByte(String name, Byte v) {
        this.name = name;
        data = v;
    }

    public NBTByte(String name, ByteData data) {
        this(name, data.readByte());
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public byte getTagID() {
        return 1;
    }

    @Override
    public byte[] toByteArray() {
        ByteData d = new ByteData();
        d.writeByte(getTagID());
        d.writeUTF8Short(name);
        d.writeByte(data);
        return d.getBytes();
    }

    public byte toByte() {
        return data;
    }

    @Override
    public byte[] toNonPrefixedByteArray() {
        ByteData data = new ByteData();
        data.writeByte((this.data));
        return data.getBytes();
    }

}
