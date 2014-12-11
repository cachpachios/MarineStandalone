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

package com.marine.net.play.clientbound.world;

import com.marine.io.data.ByteData;
import com.marine.net.Packet;
import com.marine.net.PacketOutputStream;
import com.marine.net.States;
import com.marine.world.Dimension;
import com.marine.world.World;
import com.marine.world.chunk.Chunk;

import java.io.IOException;
import java.util.List;

public class MapChunkPacket extends Packet {

    List<Chunk> chunks;

    World world;

    public MapChunkPacket(World w, List<Chunk> chunks) {
        this.chunks = chunks;
        world = w;
    }

    @Override
    public int getID() {
        return 0x26;
    }

    @Override
    public void writeToStream(PacketOutputStream stream) throws IOException {
        ByteData data = new ByteData();

        data.writeBoolean(world.getDimension() == Dimension.OVERWORLD);

        data.writeVarInt(chunks.size());

        for (Chunk c : chunks) {
            //Write Chunk metadata
            data.writeInt(c.getPos().getX());
            data.writeInt(c.getPos().getY());
            data.writeShort(c.getSectionBitMap());
        }
        for (Chunk c : chunks) {
            // Write Chunk data
            data.writeData(c.getData(true, true));
        }

        stream.write(getID(), data);
    }

    @Override
    public void readFromBytes(ByteData input) {
    }

    @Override
    public States getPacketState() {
        return States.INGAME;
    }

}
