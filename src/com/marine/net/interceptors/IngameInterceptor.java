package com.marine.net.interceptors;

import com.marine.game.PlayerManager;
import com.marine.game.async.ChatManagment;
import com.marine.io.data.ByteData;
import com.marine.net.Client;
import com.marine.net.play.KeepAlivePacket;
import com.marine.net.play.serverbound.IncomingChatPacket;
import com.marine.net.play.serverbound.ServerboundPlayerLookPositionPacket;
import com.marine.server.Marine;

public class IngameInterceptor implements PacketInterceptor{

	PlayerManager players;
	
	//int lastID; // Just for debugging
	
	public IngameInterceptor(PlayerManager m) {
		players = m;
	}
	
	@Override
	public boolean intercept(ByteData data, Client c) {
		int id = data.readVarInt();

		//if(id != lastID) {
		//	System.out.println("ID: " + id);
		//	lastID = id;
		//}
		
		if(id == 0x00) {
            KeepAlivePacket p = new KeepAlivePacket();
            p.readFromBytes(data);
            players.keepAlive(c.getUserName(), p.getID());
        } else if(id == 0x01) {
        	IncomingChatPacket p = new IncomingChatPacket();
        	p.readFromBytes(data);
        	 if(p.getMessage().startsWith("/")) {
                 players.getPlayerByClient(c).sendMessage("Commands are not implemented yet");
             } else {
                 ChatManagment.getInstance().sendChatMessage(
                         players.getPlayerByClient(c).getName(), p.getMessage());
             }
        } else if(id == 0x06) {
			ServerboundPlayerLookPositionPacket packet = new ServerboundPlayerLookPositionPacket();
			packet.readFromBytes(data);
			
		}
		
		return false;
	}

}
