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

package org.marinemc.net.handshake;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.marinemc.ServerProperties;
import org.marinemc.events.standardevents.ListEvent;
import org.marinemc.game.chat.ChatColor;
import org.marinemc.io.Base64Encoding;
import org.marinemc.io.BinaryFile;
import org.marinemc.io.data.ByteData;
import org.marinemc.net.Packet;
import org.marinemc.net.PacketOutputStream;
import org.marinemc.net.States;
import org.marinemc.player.Player;
import org.marinemc.server.Marine;

import java.io.File;
import java.io.IOException;
import java.util.UUID;
/**
 * @author Fozie
 */
public class MultiplayerListPacket extends Packet {

    private String img;

    private String getImage() {
        try {
            if (img == null || img.equals("")) {
                File file = new File("./res/favicon.png");
                if (file.exists()) {
                    BinaryFile f = new BinaryFile(file);
                    f.readBinary();
                    this.img = new String(Base64Encoding.encode(f.getData().getBytes()));
                } else {
                    this.img = "";
                }
                return img;
            }
        } catch (Throwable e) {
            e.printStackTrace();
            return "";
        }
        return img;
    }

    @Override
    public int getID() {
        return 0x00;
    }

    @SuppressWarnings("unchecked")
    @Override
    public void writeToStream(PacketOutputStream stream) throws IOException {
        JSONArray samples = new JSONArray();
        JSONObject player = new JSONObject();

        if (Marine.getServer().getPlayerCount() == 0) {
            player.put("id", UUID.fromString("1-1-3-3-7").toString());
            player.put("name", ChatColor.red + "There is nobody online!");
        } else {
            for (Player p : Marine.getPlayers()) {
                player = new JSONObject();
                player.put("id", p.getUUID().toString());
                player.put("name", p.getName());
                samples.add(player);
            }
        }

        ListResponse response = new ListResponse(Marine.getMOTD(), Marine.getPlayers().size(), Marine.getMaxPlayers(), samples, getImage());
        ListEvent event = new ListEvent(response);

        Marine.getServer().callEvent(event);

        ByteData data = new ByteData();
        data.writeUTF8(encode(event.getResponse()));
        stream.write(getID(), data);
    }

    @Override
    public void readFromBytes(ByteData input) {

    }

    @Override
    public States getPacketState() {
        return States.INTRODUCE;
    }


    @SuppressWarnings("unchecked")
    public String encode(ListResponse response) {
        JSONObject json = new JSONObject();

        JSONObject version = new JSONObject();
        version.put("name", ServerProperties.MINECRAFT_NAME);
        version.put("protocol", ServerProperties.PROTOCOL_VERSION);
        json.put("version", version);

        JSONObject players = new JSONObject();

        players.put("max", response.MAX_PLAYERS);
        players.put("online", response.CURRENT_PLAYERS);
        players.put("sample", response.SAMPLE_PLAYERS);
        json.put("players", players);

        JSONObject description = new JSONObject();
        description.put("text", response.MOTD);
        json.put("description", description);

        if (response.FAVICON != null && response.FAVICON.length() > 0)
            json.put("favicon", "data:image/png;base64," + response.FAVICON);

        return json.toJSONString();

    }
}

