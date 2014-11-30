package com.marineapi.net.interceptors;

import com.marineapi.io.data.ByteData;
import com.marineapi.net.Client;
import com.marineapi.net.handshake.ListPacket;
import com.marineapi.net.handshake.PingPacket;

public class StatusInterceptor implements PacketInterceptor {

	@Override
	public boolean intercept(ByteData data, Client c) {
		int id = data.readVarInt();
		
		if(id == 0x00) {
			ListPacket packet = new ListPacket();
			c.sendPacket(packet);
		}
		else
		if(id == 0x01) {
				PingPacket packet = new PingPacket();
				packet.readFromBytes(data);
				c.sendPacket(packet);
		}
		return false;
	}

}
