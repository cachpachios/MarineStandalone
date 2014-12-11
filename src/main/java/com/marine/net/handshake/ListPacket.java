package com.marine.net.handshake;

import com.marine.ServerProperties;
import com.marine.events.standardevents.ListEvent;
import com.marine.io.Base64Encoding;
import com.marine.io.BinaryFile;
import com.marine.io.data.ByteData;
import com.marine.net.Packet;
import com.marine.net.PacketOutputStream;
import com.marine.net.States;
import com.marine.player.Player;
import com.marine.server.Marine;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

public class ListPacket extends Packet {

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

        if (Marine.getServer().getPlayerCount() < 1) {
            player.put("id", UUID.fromString("1-1-3-3-7").toString());
            player.put("name", "§cThere is nobody online!");
        }


        for (Player p : Marine.getPlayers()) {
            player = new JSONObject();
            player.put("id", p.getUUID().toString());
            player.put("name", p.getName());
            samples.add(player);
        }

        samples.add(player);

        ListResponse response = new ListResponse(Marine.getMOTD(), Marine.getPlayers().size(), Marine.getMaxPlayers(), samples, getImage());
        ListEvent event = new ListEvent(response);

        Marine.getServer().callEvent(event);

            ByteData data = new ByteData();
            data.writeUTF8(encode(event.getResponse()));
            stream.write(getID(), data.getBytes());
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

