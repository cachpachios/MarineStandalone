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

package org.marinemc.net.login;

import org.marinemc.io.data.ByteData;
import org.marinemc.net.Packet;
import org.marinemc.net.PacketOutputStream;
import org.marinemc.net.States;
import org.marinemc.player.Player;

import java.io.IOException;
import java.util.UUID;
/**
 * @author Fozie
 */
public class LoginSucessPacket extends Packet {

    public final Player p;

    public LoginSucessPacket(final Player p) {
        this.p = p;
    }

    @Override
    public int getID() {
        return 0x02;
    }

    @Override
    public void writeToStream(PacketOutputStream stream) throws IOException {
        ByteData d = new ByteData();

        d.writeUTF8(UUID.randomUUID().toString());
        d.writeUTF8(p.getName());


        stream.write(getID(), d);
    }

    @Override
    public void readFromBytes(ByteData input) {
        // TODO Auto-generated method stub

    }

    @Override
    public States getPacketState() {
        // TODO Auto-generated method stub
        return States.LOGIN;
    }
}