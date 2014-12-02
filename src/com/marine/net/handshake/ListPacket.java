package com.marine.net.handshake;

import com.marine.ServerProperties;
import com.marine.io.data.ByteData;
import com.marine.net.Packet;
import com.marine.net.States;
import com.marine.player.Player;
import com.marine.server.Marine;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.UUID;

public class ListPacket extends Packet {

    private String img;
	synchronized private String getImage() {
        try {
            if (img == null || img.equals("")) {
                File file = new File("./favicon.ico");
                if(file.exists()) {
                    // TODO: Get this working, it sorta worked before but...
                }
                return "";
            }
        } catch(Throwable e) {
            e.printStackTrace();
            return "";
        }
        return img;
    }

	@Override
	public int getID() {
		return 0x00;
	}

	@Override
	public void writeToStream(OutputStream stream) throws IOException {
		ByteData data = new ByteData();
		
		data.writeVarInt(getID());
		data.writeUTF8(encode(Marine.getMOTD(), 20, Marine.getPlayers().size()));
		data.writePacketPrefix();
		
		stream.write(data.getBytes());
	}

	@Override
	public void readFromBytes(ByteData input)  {
		
	}

	@Override
	public States getPacketState() {
		return States.INTRODUCE;
	}
	
	
	@SuppressWarnings("unchecked")
	public String encode(String MOTD, int maxPlayers, int onlinePlayers) {
		JSONObject json = new JSONObject();

        JSONObject version = new JSONObject();
        version.put("name", ServerProperties.MINECRAFT_NAME);
        version.put("protocol", ServerProperties.PROTOCOL_VERSION);
        json.put("version", version);

        JSONObject players = new JSONObject();

        JSONArray samples = new JSONArray();
        JSONObject player = new JSONObject();

        player.put("id", UUID.fromString("1-1-3-3-7").toString());
        player.put("name", "§cThere is nobody online!");

        samples.add(player);

        for (Player p : Marine.getPlayers()) {
            player = new JSONObject();
            player.put("id", p.getUUID().toString());
            player.put("name", p.getName());
            samples.add(player);
        }

        players.put("max", maxPlayers);
        players.put("online", onlinePlayers);
        players.put("sample", samples);
        json.put("players", players);

        JSONObject description = new JSONObject();
        description.put("text", MOTD);
        json.put("description", description);
        
        //TODO: Faviicon
        //json.put("favicon", "data:image/png;base64," + getImage());

		return json.toJSONString();
		
	}
}

