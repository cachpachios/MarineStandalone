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

package com.marine.net;

import com.marine.io.data.ByteData;

import java.io.IOException;
import java.io.OutputStream;

public class PacketOutputStream { // Here we enable encryption and compression if needed

    private final OutputStream stream;

    private final Client c;

    public PacketOutputStream(Client c, OutputStream stream) {
        this.c = c;
        this.stream = stream;
    }
    
    public OutputStream getStream() {
    	return stream;
    }

    public void write(int id, byte[] b) throws IOException {
        write(id, new ByteData(b));
    }
    
    public void write(int id, Byte[] b) throws IOException {
        write(id, new ByteData(b));
    }

    public void write(int id, ByteData data) throws IOException {
        //TODO Compress and encrypt :D
        data.writeVarInt(0, id);
        data.writePacketPrefix();
        stream.write(data.getBytes());
    }
}
