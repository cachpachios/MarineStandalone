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

package org.marinemc.net.packets.player;

import java.io.IOException;

import org.marinemc.io.binary.ByteInput;
import org.marinemc.io.binary.ByteList;
import org.marinemc.net.Packet;
import org.marinemc.net.PacketOutputStream;
import org.marinemc.net.States;
import org.marinemc.util.Location;

/**
 * @author Fozie
 */
public class PlayerPositionPacket extends Packet {

	double x,y,z;
	boolean onGround;

	public PlayerPositionPacket() {
		super(0x04, States.INGAME);
	}
	
	public PlayerPositionPacket(Location l) {
		super(0x04, States.INGAME);
		x = l.x;
		y = l.y;
		z = l.z;
		onGround = l.isOnGround();
	}

	@Override
	public void readFromBytes(final ByteInput input) {
		x = input.readDouble();
		y = input.readDouble() + 1.62; // To turn the givin feet position to headposition
		z = input.readDouble();
		
		onGround = input.readBoolean();
	}
	
	public void writeToStream(PacketOutputStream stream) throws IOException {
		ByteList list = new ByteList();
		
		list.writeDouble(x);
		list.writeDouble(y);
		list.writeDouble(z);
		
		list.writeBoolean(onGround);
		
		list = null;
	}

	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}

	public double getZ() {
		return z;
	}

	public void setZ(double z) {
		this.z = z;
	}

	public boolean isOnGround() {
		return onGround;
	}

	public void setOnGround(boolean onGround) {
		this.onGround = onGround;
	}

	public Location getLocation() { return new Location(null,x,y,z); }
	

}
