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

package org.marinemc.net.play.clientbound;

import java.io.IOException;

import org.marinemc.io.binary.ByteList;
import org.marinemc.net.Packet;
import org.marinemc.net.PacketOutputStream;
import org.marinemc.net.States;

/**
 * Created 2014-12-06 for MarineStandalone
 *
 * @author Citymonstret
 */
public class GameStateChangePacket extends Packet {

	private final Reason reason;
	private final float value;

	public GameStateChangePacket(final Reason reason, final float value) {
		super(0x2B, States.INGAME);
		this.reason = reason;
		this.value = value;
	}

	@Override
	public void writeToStream(final PacketOutputStream stream)
			throws IOException {
		final ByteList data = new ByteList();
		data.writeByte((byte) reason.ordinal());
		if (value > -1f)
			data.writeFloat(value);
		stream.write(getID(), data);
	}

	public static enum Reason {
		INVALID_BED, END_RAINING, BEGIN_RAINING, CHANGE_GAMEMODE, ENTER_CREDITS, DEMO_MESSAGES, ARROW_HITTING_PLAYER, FADE_VALUE, FADE_TIME
	}
}
