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
import com.marine.player.PlayerAbilites;

import java.io.IOException;

public class PlayerAbilitesPacket extends Packet {

    final PlayerAbilites abilites;

    public PlayerAbilitesPacket(PlayerAbilites abilites) {
        this.abilites = abilites;
    }

    @Override
    public int getID() {
        return 0x39;
    }

    @Override
    public void writeToStream(PacketOutputStream stream) throws IOException {
        ByteData d = new ByteData();

        byte flags = (byte) ((abilites.isInCreativeMode() ? 8 : 0) | (abilites.canFly() ? 4 : 0) | (abilites.isFlying() ? 2 : 0) | (abilites.isInCreativeMode() ? 1 : 0));
        d.writeByte(flags);
        d.writeFloat(abilites.getFlySpeed());
        d.writeFloat(abilites.getWalkSpeed());

        stream.write(getID(), d.getBytes());


    }

    @Override
    public void readFromBytes(ByteData input) {
    }// Clientbound only

    @Override
    public States getPacketState() {
        return States.INGAME;
    }

}
