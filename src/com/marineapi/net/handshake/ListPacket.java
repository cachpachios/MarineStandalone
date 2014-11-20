package com.marineapi.net.handshake;

import java.io.IOException;
import java.io.OutputStream;

import org.json.simple.JSONObject;

import com.marineapi.ServerProperties;
import com.marineapi.net.Packet;
import com.marineapi.net.States;
import com.marineapi.net.data.ByteData;
import com.marineapi.net.data.ByteEncoder;

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
        version.put("name", ServerProperties.Minecraft_Name);
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

