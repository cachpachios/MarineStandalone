package com.marineapi.net.interceptors;

import com.marineapi.net.Client;
import com.marineapi.net.data.ByteData;
import com.marineapi.net.login.DisconnectPacket;
import com.marineapi.net.login.LoginPacket;

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
