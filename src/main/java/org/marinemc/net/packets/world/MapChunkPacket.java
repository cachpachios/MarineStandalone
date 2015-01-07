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

package org.marinemc.net.packets.world;

import java.io.IOException;
import java.util.List;

import org.marinemc.io.binary.ByteList;
import org.marinemc.io.binary.ByteUtils;
import org.marinemc.net.Packet;
import org.marinemc.net.PacketOutputStream;
import org.marinemc.net.States;
import org.marinemc.world.Dimension;
import org.marinemc.world.World;
import org.marinemc.world.chunk.Chunk;
/**
 * @author Fozie
 */
public class MapChunkPacket extends Packet {

    final List<Chunk> chunks;

    final World world;

    public MapChunkPacket(final World w, final List<Chunk> chunks) {
        super(0x26, States.INGAME);
        this.chunks = chunks;
        world = w;
    }

    @Override
    public void writeToStream(PacketOutputStream stream) throws IOException {
    	ByteList data = new ByteList();

        data.writeBoolean(world.getDimension() == Dimension.OVERWORLD);

        data.writeVarInt(chunks.size());

        for (Chunk c : chunks) {
            //Write Chunk metadata
            data.writeInt(c.getPos().getX());
            data.writeInt(c.getPos().getY());
            data.writeShort(c.getSectionBitMap());
        }
        
        byte[] bytes = new byte[0];
        
        for (Chunk c : chunks) {
        	bytes = ByteUtils.putFirst(bytes, c.getBytes(true, true));
        }

        //Disabled cuz its doesnt works :(
        //stream.write(getID(), ByteUtils.putFirst(data.toBytes(),bytes));
    }

}
