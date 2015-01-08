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

package org.marinemc.net.play.clientbound.player;

import java.io.IOException;

import org.marinemc.game.player.Player;
import org.marinemc.io.binary.ByteList;
import org.marinemc.net.Packet;
import org.marinemc.net.PacketOutputStream;
import org.marinemc.net.States;

/**
 * @author Fozie
 */
public class PlayerAbilitesPacket extends Packet {

	final Player p;

	public PlayerAbilitesPacket(final Player p) {
		super(0x39, States.INGAME);
		this.p = p;
	}

	@Override
	public void writeToStream(final PacketOutputStream stream)
			throws IOException {
		final ByteList d = new ByteList();

		final byte flags = (byte) ((p.isInCreativeMode() ? 8 : 0)
				| (p.canFly() ? 4 : 0) | (p.isFlying() ? 2 : 0) | (p
				.isInCreativeMode() ? 1 : 0));
		d.writeByte(flags);
		d.writeFloat(p.getFlySpeed());
		d.writeFloat(p.getWalkSpeed());

		stream.write(getID(), d);

	}
}
