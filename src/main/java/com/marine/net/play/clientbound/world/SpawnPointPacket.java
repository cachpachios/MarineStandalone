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

package com.marine.net.play.clientbound.world;

import com.marine.io.data.ByteData;
import com.marine.net.Packet;
import com.marine.net.PacketOutputStream;
import com.marine.net.States;
import com.marine.util.Position;

import java.io.IOException;

public class SpawnPointPacket extends Packet { // Only used to make the client know where the compass should point

    final Position spawnPoint;

    public SpawnPointPacket(Position spawnPoint) {
        this.spawnPoint = spawnPoint;
    }

    @Override
    public int getID() {
        return 0x05;
    }

    @Override
    public void writeToStream(PacketOutputStream stream) throws IOException {
        ByteData d = new ByteData();
        d.writePosition(spawnPoint);
        d.writePacketPrefix();

        stream.write(getID(), d.getBytes());
    }

    @Override
    public void readFromBytes(ByteData input) {
    } // Clientbound only

    @Override
    public States getPacketState() {
        return States.INGAME;
    }
}
