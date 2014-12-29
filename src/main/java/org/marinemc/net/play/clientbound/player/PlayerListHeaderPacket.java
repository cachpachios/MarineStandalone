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

import java.io.IOException;

import org.json.simple.JSONObject;
import org.marinemc.game.chat.ChatColor;
import org.marinemc.io.binary.ByteList;
import org.marinemc.net.Packet;
import org.marinemc.net.PacketOutputStream;
import org.marinemc.net.States;

/**
 * Created 2014-12-04 for MarineStandalone
 *
 * @author Citymonstret
 */
public class PlayerListHeaderPacket extends Packet {

    private final String header, footer;

    public PlayerListHeaderPacket(final String header, final String footer) {
        super(0x047, States.INGAME);

        JSONObject headerObject = new JSONObject();
        headerObject.put("text", ChatColor.transform('&', header));
        this.header = headerObject.toJSONString();

        JSONObject footerObject = new JSONObject();
        footerObject.put("text", ChatColor.transform('&', footer));
        this.footer = footerObject.toJSONString();
    }

    @Override
    public void writeToStream(PacketOutputStream stream) throws IOException {
    	ByteList data = new ByteList();
        data.writeUTF8(header);
        data.writeUTF8(footer);
        stream.write(getID(), data);
    }
}
