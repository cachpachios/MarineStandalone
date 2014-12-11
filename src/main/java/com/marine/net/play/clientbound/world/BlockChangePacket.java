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
import com.marine.util.Position;
import com.marine.world.Block;
import com.marine.world.BlockID;

import java.io.IOException;

public class BlockChangePacket extends Packet {
    public Position pos;
    public int newBlock;

    public BlockChangePacket(Block toBlock) {
        this(toBlock.getBlockPos(), toBlock.getType().getPacketID());
    }

    public BlockChangePacket(Position pos, int b) {
        this.pos = pos;
        this.newBlock = b;
    }

    public BlockChangePacket(Position p, BlockID b) {
        this(p, b.getID());
    }

    @Override
    public int getID() {
        return 0x23;
    }

    @Override
    public void writeToStream(PacketOutputStream stream) throws IOException {

        ByteData data = new ByteData();

        data.writePosition(pos);
        data.writeVarInt(newBlock);

        stream.write(getID(), data.getBytes());

    }

    @Override
    public void readFromBytes(ByteData input) {
        // Serversent packet :)
    }

    @Override
    public States getPacketState() {
        return States.INGAME;
    }
}
