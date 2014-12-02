package com.marine.net.interceptors;

import com.marine.StandaloneServer;
import com.marine.game.LoginHandler;
import com.marine.io.data.ByteData;
import com.marine.net.Client;
import com.marine.net.login.DisconnectPacket;
import com.marine.net.login.LoginPacket;

public class LoginInterceptor implements PacketInterceptor {

	final StandaloneServer server;
	
	public LoginInterceptor(StandaloneServer server) {
		this.server = server;	
	}
	
	@Override
	public boolean intercept(ByteData data, Client c) {
		int ID = data.readVarInt();
		
		if(ID == 0x00) {
			LoginPacket packet = new LoginPacket();
			packet.readFromBytes(data);
			
			LoginHandler.LoginResponse loginReturn = server.getPlayerManager().getLoginManager().preJoin(packet.name, c);
			if(!loginReturn.succeed()) {
				DisconnectPacket nopePacket = new DisconnectPacket(loginReturn.response);
				c.sendPacket(nopePacket);
				server.getNetwork().cleanUp(c);
				return true;
			}
			
			server.getPlayerManager().getLoginManager().passPlayer(loginReturn.player.getUUID());
			
			return true;
		}
		
		//TODO: Encryption Packets
		
		return false;
	}

}
