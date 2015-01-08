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

import org.marinemc.io.binary.ByteList;
import org.marinemc.net.Packet;
import org.marinemc.net.PacketOutputStream;
import org.marinemc.net.States;
import org.marinemc.util.Position;
import org.marinemc.world.Block;
import org.marinemc.world.BlockID;
/**
 * @author Fozie
 */
public class BlockChangePacket extends Packet {
    public Position pos;
    public int newBlock;

    public BlockChangePacket(Block toBlock) {
        this(toBlock.getGlobalPos(), toBlock.getType().getPacketID());
    }

    public BlockChangePacket(Position pos, short b) {
        super(0x23, States.INGAME);
        this.pos = pos;
        this.newBlock = (int)b;
    }

    public BlockChangePacket(Position p, BlockID b) {
        this(p, b.getID());
    }

    @Override
    public void writeToStream(PacketOutputStream stream) throws IOException {

    	ByteList data = new ByteList();

        data.writePosition(pos);
        data.writeVarInt	(newBlock);

        stream.write(getID(), data);

    }

}
