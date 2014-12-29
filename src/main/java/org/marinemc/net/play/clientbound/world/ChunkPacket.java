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

package org.marinemc.net.play.clientbound.world;

import java.io.IOException;

import org.marinemc.io.binary.ByteList;
import org.marinemc.net.Packet;
import org.marinemc.net.PacketOutputStream;
import org.marinemc.net.States;
import org.marinemc.world.Dimension;
import org.marinemc.world.chunk.Chunk;
/**
 * @author Fozie
 */
public class ChunkPacket extends Packet {

    final Chunk c;

    public ChunkPacket(Chunk c) {
        super(0x21, States.INGAME);
        this.c = c;
    }

    @Override
    public void writeToStream(PacketOutputStream stream) throws IOException {
    	ByteList data = new ByteList();

        data.writeInt(c.getPos().getX());
        data.writeInt(c.getPos().getY());
        data.writeBoolean(true);
        data.writeShort(c.getSectionBitMap());

        final ByteList d = c.getData(true, c.getWorld().getDimension() == Dimension.OVERWORLD);

        data.writeVarInt(d.size());
        data.writeData(d);

        stream.write(getID(), data);
    }

}
