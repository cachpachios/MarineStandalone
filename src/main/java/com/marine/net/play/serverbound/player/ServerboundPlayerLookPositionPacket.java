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

package com.marine.net.play.serverbound.player;

import com.marine.io.data.ByteData;
import com.marine.net.Packet;
import com.marine.net.PacketOutputStream;
import com.marine.net.States;
import com.marine.net.play.clientbound.player.ClientboundPlayerLookPositionPacket;
import com.marine.util.Location;

import java.io.IOException;

public class ServerboundPlayerLookPositionPacket extends Packet { //TODO Relative positions

    private Location l;

    public ServerboundPlayerLookPositionPacket() {
    }

    @Override
    public int getID() {
        return 0x06;
    }

    @Override
    public void writeToStream(PacketOutputStream stream) throws IOException {
    }

    @Override
    public void readFromBytes(ByteData input) {
        double x = input.readDouble();
        double y = input.readDouble();
        double z = input.readDouble();

        float yaw = input.readFloat();
        float pitch = input.readFloat();

        input.readBoolean(); // onGround

        l = new Location(null, x, y, z, yaw, pitch);
    }

    public Location getLocation() {
        return l;
    }

    @Override
    public States getPacketState() {
        return States.INGAME;
    }

    public ClientboundPlayerLookPositionPacket getClientBound() {
        return new ClientboundPlayerLookPositionPacket(l);
    }
}
