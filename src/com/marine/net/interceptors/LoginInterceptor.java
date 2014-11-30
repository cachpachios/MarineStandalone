package com.marine.net.interceptors;

import com.marine.io.data.ByteData;
import com.marine.net.Client;
import com.marine.net.login.DisconnectPacket;
import com.marine.net.login.LoginPacket;

public class LoginInterceptor implements PacketInterceptor {

	@Override
	public boolean intercept(ByteData data, Client c) {
		int ID = data.readVarInt();
		
		if(ID == 0x00) {
			LoginPacket packet = new LoginPacket();
			packet.readFromBytes(data);
			
			DisconnectPacket nopePacket = new DisconnectPacket("Underdevelopment :)");
			c.sendPacket(nopePacket);
		}
		
		return false;
	}

}
