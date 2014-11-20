package com.marineapi.net.interceptors;

import com.marineapi.net.Client;
import com.marineapi.net.data.ByteData;
import com.marineapi.net.handshake.ListPacket;

public class StatusInterceptor implements PacketInterceptor {

	@Override
	public boolean intercept(ByteData data, Client c) {
		int id = data.readVarInt();
		
		if(id == 0x00) {
			ListPacket packet = new ListPacket();
			packet.readFromBytes(data);
			c.sendPacket(packet);
		}
		return false;
	}

}
