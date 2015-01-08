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

package org.marinemc.net.play.serverbound.player;

import java.io.IOException;

import org.marinemc.io.binary.ByteInput;
import org.marinemc.net.Packet;
import org.marinemc.net.PacketOutputStream;
import org.marinemc.net.States;
import org.marinemc.net.play.clientbound.player.ClientboundPlayerLookPositionPacket;
import org.marinemc.util.Location;

/**
 * @author Fozie
 */
public class ServerboundPlayerLookPositionPacket extends Packet { // TODO
																	// Relative
																	// positions

	private Location l;

	public ServerboundPlayerLookPositionPacket() {
		super(0x06, States.INGAME);
	}

	@Override
	public void writeToStream(final PacketOutputStream stream)
			throws IOException {
	}

	@Override
	public void readFromBytes(final ByteInput input) {
		final double x = input.readDouble();
		final double y = input.readDouble();
		final double z = input.readDouble();

		final float yaw = input.readFloat();
		final float pitch = input.readFloat();

		input.readBoolean(); // onGround

		l = new Location(null, x, y, z, yaw, pitch);
	}

	public Location getLocation() {
		return l;
	}

	public ClientboundPlayerLookPositionPacket getClientBound() {
		return new ClientboundPlayerLookPositionPacket(l);
	}
}
