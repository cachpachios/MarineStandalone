package com.marineapi.net;

import com.marineapi.net.data.ByteData;
import com.marineapi.net.interceptors.HandshakeInterceptor;
import com.marineapi.net.interceptors.StatusInterceptor;


public class PacketHandler {
	
	HandshakeInterceptor handshake;
	StatusInterceptor status;
	
	public PacketHandler() {
		handshake = new HandshakeInterceptor();
		status = new StatusInterceptor();
	}
	
	public boolean intercept(ByteData data, Client c) {
		
		if(c.getState().equals(States.HANDSHAKE))
			return handshake.intercept(data, c);
		if(c.getState().equals(States.INTRODUCE))
			return status.intercept(data, c);
			
		
		return true;
		
	}
}
