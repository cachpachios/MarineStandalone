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

import org.marinemc.io.data.ByteData;
import org.marinemc.net.Packet;
import org.marinemc.net.PacketOutputStream;
import org.marinemc.net.States;
import org.marinemc.net.play.serverbound.player.ServerboundPlayerLookPositionPacket;
import org.marinemc.util.Location;
import org.marinemc.util.Position;

import java.io.IOException;
/**
 * @author Fozie
 */
public class ClientboundPlayerLookPositionPacket extends Packet { //TODO Relative positions

    final Location l;
    final Position p;

    public ClientboundPlayerLookPositionPacket(Location l) {
        this(null, l);
    }

    public ClientboundPlayerLookPositionPacket(Position p) {
        this(p, null);
    }

    public ClientboundPlayerLookPositionPacket(Position p, Location l) {
        super(0x08, States.INGAME);
        this.l = l;
        this.p = p;
    }

    public ClientboundPlayerLookPositionPacket(ServerboundPlayerLookPositionPacket l) {
        this(l.getLocation());
    }

    @Override
    public void writeToStream(PacketOutputStream stream) throws IOException {
        ByteData d = new ByteData();
        if (p == null) {
            d.writeDouble(l.getX());
            d.writeDouble(l.getY());
            d.writeDouble(l.getZ());

            d.writeFloat(l.getYaw());
            d.writeFloat(l.getPitch());
        } else {
            d.writeDouble(p.getX());
            d.writeDouble(p.getY());
            d.writeDouble(p.getZ());

            d.writeFloat(l.getYaw());
            d.writeFloat(l.getPitch());
        }

        d.writeByte((byte) 0);

        stream.write(getID(), d);
    }

    @Override
    public void readFromBytes(ByteData input) {

    }

}
