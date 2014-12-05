package com.marine.net.interceptors;

import com.marine.game.PlayerManager;
import com.marine.io.data.ByteData;
import com.marine.net.Client;
import com.marine.net.play.KeepAlivePacket;
import com.marine.net.play.serverbound.IncomingChat;
import com.marine.net.play.serverbound.ServerboundPlayerLookPositionPacket;

public class IngameInterceptor implements PacketInterceptor{

	PlayerManager players;
	
	int lastID; // Just for debugging shall be removed later
	
	public IngameInterceptor(PlayerManager m) {
		players = m;
	}
	
	@Override
	public boolean intercept(ByteData data, Client c) {
		int id = data.readVarInt();

		if(id != lastID) {
			System.out.println("ID: " + id);
			lastID = id;
		}
		
		if(id == 0x00) {
            KeepAlivePacket p = new KeepAlivePacket();
            p.readFromBytes(data);
            players.keepAlive(c.getUserName(), p.getID());
        } else if(id == 0x01) {
            new IncomingChat(c).readFromBytes(data);
        } else if(id == 0x06) {
			ServerboundPlayerLookPositionPacket packet = new ServerboundPlayerLookPositionPacket();
			packet.readFromBytes(data);
			//if(players.getLoginManager().clientExists(c)) { // Check if this packet is part by the login process
				
			//}else {
				
			//}
		}
		
		return false;
	}

}
