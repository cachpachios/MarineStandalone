package com.marine.net.interceptors;

import com.marine.Logging;
import com.marine.game.PlayerManager;
import com.marine.io.data.ByteData;
import com.marine.net.Client;
import com.marine.net.play.serverbound.ServerboundPlayerLookPositionPacket;

public class IngameInterceptor implements PacketInterceptor{

	PlayerManager players;
	
	public IngameInterceptor(PlayerManager m) {
		players = m;
	}
	
	@Override
	public boolean intercept(ByteData data, Client c) {
		int id = data.readVarInt();
		
		Logging.instance().info("Ingame Packet Intercepted: " + id);
		
		if(id == 0x06) {
			ServerboundPlayerLookPositionPacket packet = new ServerboundPlayerLookPositionPacket();
			packet.readFromBytes(data);
			if(players.getLoginManager().clientExists(c)) { // Check if this packet is part by the login process
				
			}else {
				
			}
		}
		
		return false;
	}

}
