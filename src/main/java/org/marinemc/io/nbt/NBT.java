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

import org.marinemc.io.BinaryFile;
import org.marinemc.io.data.ByteData;

import java.io.File;
import java.io.IOException;

public class NBT {

    NBTCompound tag;

    public NBT(ByteData data) throws IOException {
        NBTTag Pretag = parse(data);

        if (Pretag instanceof NBTCompound) {
            throw new IOException("File was in wrong format");
        } else tag = (NBTCompound) Pretag;

    }

    public NBT(String name) {
        tag = new NBTCompound(name);
    }

    public NBT(File f) throws IOException {
        this(new BinaryFile(f).readGZIPBinary().getData());
    }

    public static NBTTag parse(ByteData data) {
        byte id = data.readByte();
        return parse(data, id);
    }

    public static NBTTag parse(ByteData data, byte id) {
        if (id == 0) return null;
        else if (id == 1) return new NBTByte(data.readUTF8Short(), data);
        else if (id == 2) return new NBTShort(data.readUTF8Short(), data);
        else if (id == 3) return new NBTInteger(data.readUTF8Short(), data);
        else if (id == 4) return new NBTLong(data.readUTF8Short(), data);
        else if (id == 5) return new NBTFloat(data.readUTF8Short(), data);
        else if (id == 6) return new NBTDouble(data.readUTF8Short(), data);
        else if (id == 7) return new NBTByteArray(data.readUTF8Short(), data);
        else if (id == 8) return new NBTString(data.readUTF8Short(), data);
        else if (id == 9) return new NBTList(data.readUTF8Short(), data);
        if (id == 10) return new NBTCompound(data.readUTF8Short(), data);
        else if (id == 11) return new NBTList(data.readUTF8Short(), data);

        return null;
    }

    public void save(File fPath, boolean compress) throws IOException {
        ByteData data = new ByteData();
        data.writeend(tag.toByteArray());
        BinaryFile f = new BinaryFile(fPath, data);
        if (compress)
            f.writeGZIPBinary();
        else
            f.writeBinary();
    }

    public void setMainCompund(NBTCompound tag) {
        this.tag = tag;
    }

}
