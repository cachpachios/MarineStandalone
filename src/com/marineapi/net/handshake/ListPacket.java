package com.marineapi.net.handshake;

import java.io.IOException;
import java.io.OutputStream;

import org.json.simple.JSONObject;

import com.marineapi.StandaloneServer;
import com.marineapi.net.Packet;
import com.marineapi.net.States;
import com.marineapi.net.data.ByteData;
import com.marineapi.net.data.ByteEncoder;
import com.marineapi.player.PlayerID;

public class ListPacket extends Packet {

	
	@Override
	public int getID() {
		return 0x00;
	}

	@Override
	public void writeToStream(OutputStream stream) throws IOException {
		ByteData data = new ByteData();
		
		data.writeend(ByteEncoder.writeVarInt(getID()));
		data.writeend(ByteEncoder.writeUTFPrefixedString(encode("Test",10,1,null)));
		
		int l = data.getLength();
		
		data.write(0, ByteEncoder.writeVarInt(l));
		
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
	public String encode(String MOTD, int maxPlayers, int onlinePlayers, PlayerID[] samples) { //TODO: Favicon :)
		JSONObject obj = new JSONObject();
		
		JSONObject version = new JSONObject();
		version.put("name", StandaloneServer.Minecraft_Name);
		version.put("protocol", StandaloneServer.PROTOCOL_VERSION);
		
		obj.put("version", version);
		
		JSONObject players = new JSONObject();
		players.put("max", maxPlayers);
		players.put("online", onlinePlayers);
		
		obj.put("players", players);
		
		JSONObject description = new JSONObject();
		players.put("text", onlinePlayers); // Should be a ChatMessage
		
		obj.put("description", description);

		
		return obj.toJSONString();
		
	}
}

