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

/**
 * 
 */
package org.marinemc.net.packets.world;

import java.io.IOException;

import org.marinemc.io.binary.ByteList;
import org.marinemc.net.Packet;
import org.marinemc.net.PacketOutputStream;
import org.marinemc.net.States;
import org.marinemc.world.chunk.ChunkPos;

/**
 * 
 * Sent to tell client to unload a chunk. Same as ChunkPacket but without
 * information
 * 
 * @author Fozie
 *
 */
public class UnloadChunkPacket extends Packet {

	final ChunkPos pos;

	public UnloadChunkPacket(final ChunkPos c) {
		super(0x21, States.INGAME);
		pos = c;
	}

	@Override
	public void writeToStream(final PacketOutputStream stream)
			throws IOException {
		final ByteList data = new ByteList();

		data.writeInt(pos.getX());
		data.writeInt(pos.getY());
		data.writeBoolean(true);
		data.writeShort((short) 0);
		data.writeVarInt(0);

		stream.write(getID(), data);
	}

}
