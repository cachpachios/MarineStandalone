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

package org.marinemc.net.packets.status;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.marinemc.events.standardevents.ListEvent;
import org.marinemc.game.chat.ChatColor;
import org.marinemc.game.player.Player;
import org.marinemc.io.binary.ByteList;
import org.marinemc.net.Packet;
import org.marinemc.net.PacketOutputStream;
import org.marinemc.net.States;
import org.marinemc.server.Marine;
import org.marinemc.server.ServerProperties;
import org.marinemc.util.mojang.MojangTask;
import org.marinemc.util.mojang.MojangUtils;

import java.io.IOException;
import java.util.UUID;
/**
 * @author Fozie
 */
public class MultiplayerListPacket extends Packet {


    public MultiplayerListPacket() {
        super(0x00, States.INTRODUCE);
    }

    @SuppressWarnings("unchecked")
    @Override
    public void writeToStream(PacketOutputStream stream) throws IOException {
        JSONArray samples = new JSONArray();
        JSONObject player = new JSONObject();

        if (Marine.getServer().getPlayerCount() == 0) {
            player.put("id", UUID.fromString("1-1-3-3-7").toString());
            player.put("name", ChatColor.red + "There is nobody online!");
            samples.add(player);
        } else {
            for (Player p : Marine.getPlayers()) {
                player = new JSONObject();
                player.put("id", p.getUUID().toString());
                player.put("name", p.getUserName());
                samples.add(player);
            }
        }

        ListResponse response = new ListResponse(Marine.getMotd(), Marine.getPlayers().size(), Marine.getMaxPlayers(), samples, Marine.getServer().getFavicon());
        if (MojangTask.getStatus() != MojangUtils.Status.ONLINE) {
            String motd = response.getMOTD();
            if (motd.contains("\n")) {
                motd = motd.split("\n")[0];
            }
            response.setMotd(motd + "\nAuth Servers Are Down");
        }
        ListEvent event = new ListEvent(response);

        Marine.getServer().callEvent(event);

        ByteList data = new ByteList();
        data.writeUTF8(encode(event.getResponse()));
        stream.write(getID(), data);
    }

    @SuppressWarnings("unchecked")
    public String encode(ListResponse response) {
        JSONObject json = new JSONObject();

        JSONObject version = new JSONObject();
        version.put("name", ServerProperties.MINECRAFT_NAME);
        version.put("protocol", ServerProperties.PROTOCOL_VERSION);
        json.put("version", version);

        JSONObject players = new JSONObject();

        players.put("max", response.getMaxPlayers());
        players.put("online", response.getCurrentPlayers());
        players.put("sample", response.getSamplePlayers());
        json.put("players", players);

        JSONObject description = new JSONObject();
        description.put("text", response.getMOTD());
        json.put("description", description);

        if (response.getFavicon() != null && response.getFavicon().toString().length() > 0)
            json.put("favicon", "data:image/png;base64," + response.getFavicon());

        return json.toJSONString();

    }
}