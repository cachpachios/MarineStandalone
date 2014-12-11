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

package com.marine.net.play.clientbound.player;

import com.marine.io.data.ByteData;
import com.marine.net.Packet;
import com.marine.net.PacketOutputStream;
import com.marine.net.States;
import com.marine.player.Player;

import java.io.IOException;

/**
 * Created 2014-12-07 for MarineStandalone
 *
 * @author Citymonstret
 */
public class ExperiencePacket extends Packet {

    private final Player player;

    public ExperiencePacket(final Player player) {
        this.player = player;
    }

    @Override
    public int getID() {
        return 0x1F;
    }

    @Override
    public void writeToStream(PacketOutputStream stream) throws IOException {
        ByteData data = new ByteData();
        data.writeFloat(player.getExp());
        data.writeVarInt(player.getLevels());
        data.writeVarInt((int) (player.getLevels() * 100 + (player.getExp() * 10)));
        stream.write(getID(), data.getBytes());
    }

    @Override
    public void readFromBytes(ByteData input) {

    }

    @Override
    public States getPacketState() {
        return States.INGAME;
    }
}
