package com.marineapi.net;

import com.marineapi.net.data.ByteData;
import com.marineapi.net.interceptors.HandshakeInterceptor;


public class PacketHandler {
	
	HandshakeInterceptor handshake;
	
	public PacketHandler() {
		handshake = new HandshakeInterceptor();
	}
	
	public boolean intercept(ByteData data, Client c) {
		
		if(c.getState().equals(States.HANDSHAKE))
			return handshake.intercept(data, c);
			
		
		return true;
		
	}
}
