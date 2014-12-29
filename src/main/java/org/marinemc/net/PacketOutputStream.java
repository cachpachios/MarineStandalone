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

package org.marinemc.net;

import java.io.IOException;
import java.io.OutputStream;

import org.marinemc.io.binary.ByteUtils;
import org.marinemc.io.binary.SortedByteOutput;
/**
 * @author Fozie
 */
public class PacketOutputStream { // Here we enable encryption and compression if needed

    private final OutputStream stream;

    public PacketOutputStream(OutputStream stream) {
        this.stream = stream;
    }

    public OutputStream getStream() {
        return stream;
    }

    public void write(int id, final byte[] b) throws IOException {
    	byte[] data = ByteUtils.putFirst(ByteUtils.VarInt(id), b);
        stream.write(ByteUtils.putFirst(ByteUtils.VarInt(data.length), data));
    }

    public void write(int id, SortedByteOutput data) throws IOException {
        //TODO Compress and encrypt :D
    	data.writeVarInt(0, id);
    	data.writeVarInt(0, data.size());
    	
        stream.write(data.toBytes());
    }
}
