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

import org.marinemc.game.chat.ChatColor;
import org.marinemc.game.chat.builder.Chat;
import org.marinemc.io.data.ByteData;
import org.marinemc.net.Packet;
import org.marinemc.net.PacketOutputStream;
import org.marinemc.net.States;

import java.io.IOException;

public class DisconnectPacket extends Packet {

    String msg;

    public DisconnectPacket(String msg) {
        this.msg = msg;
    }

    @Override
    public int getID() {
        return 0x00;
    }

    @Override
    public void writeToStream(PacketOutputStream stream) throws IOException {
        ByteData data = new ByteData();
        data.writeUTF8(new Chat(msg).format(ChatColor.BOLD).color(ChatColor.WHITE).toString() /* new ChatComponent(msg, true, true, false, false, ChatColor */);

        stream.write(getID(), data);
    }

    @Override
    public void readFromBytes(ByteData input) {
        // TODO Auto-generated method stub

    }

    @Override
    public States getPacketState() {
        // TODO Auto-generated method stub
        return null;
    }
}
