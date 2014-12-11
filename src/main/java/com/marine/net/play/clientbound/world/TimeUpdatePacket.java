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
import com.marine.world.World;

import java.io.IOException;

public class TimeUpdatePacket extends Packet {
    final long worldTime;
    final long worldAge;

    public TimeUpdatePacket(long worldTime, long worldAge) {
        super();
        this.worldTime = worldTime;
        this.worldAge = worldAge;
    }

    public TimeUpdatePacket(World w) {
        this(w.getTime(), w.getWorldAge());
    }

    @Override
    public int getID() {
        return 0x03;
    }

    @Override
    public void writeToStream(PacketOutputStream stream) throws IOException {
        ByteData d = new ByteData();

        d.writeLong(worldAge);
        d.writeLong(worldTime);

        stream.write(getID(), d.getBytes());
    }

    @Override
    public void readFromBytes(ByteData input) {
    }

    @Override
    public States getPacketState() {
        // TODO Auto-generated method stub
        return null;
    }

}
