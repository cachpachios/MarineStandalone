package com.marine.net.handshake;

import com.marine.ServerProperties;
import com.marine.io.data.ByteData;
import com.marine.net.Packet;
import com.marine.net.States;
import org.json.simple.JSONObject;

import java.io.IOException;
import java.io.OutputStream;

public class ListPacket extends Packet {

	
	@Override
	public int getID() {
		return 0x00;
	}

	@Override
	public void writeToStream(OutputStream stream) throws IOException {
		ByteData data = new ByteData();
		
		data.writeVarInt(getID());
		data.writeUTF8(encode("MarineStandalone DEV", 20, 0));
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

        players.put("max", maxPlayers);
        players.put("online", onlinePlayers);
        //TODO: Player samples
        json.put("players", players);

        JSONObject description = new JSONObject();
        description.put("text", MOTD);
        json.put("description", description);
        
        //TODO: Faviicon
		return json.toJSONString();
		
	}
}

