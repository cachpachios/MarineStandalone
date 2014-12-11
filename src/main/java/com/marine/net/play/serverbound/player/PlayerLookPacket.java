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

import java.io.IOException;

public class PlayerLookPacket extends Packet {

    private float yaw, pitch;
    private boolean onGround;

    public float getYaw() {
        return yaw;
    }

    public float getPitch() {
        return pitch;
    }

    public boolean getOnGround() {
        return onGround;
    }

    @Override
    public int getID() {
        return 0x05;
    }

    @Override
    public void writeToStream(PacketOutputStream stream) throws IOException {
    }

    @Override
    public void readFromBytes(ByteData input) {
        yaw = input.readFloat();
        pitch = input.readFloat();
        onGround = input.readBoolean();
    }

    @Override
    public States getPacketState() {
        return States.INGAME;
    }

}
