package com.marineapi.net;

import com.marineapi.io.data.ByteData;
import com.marineapi.net.interceptors.HandshakeInterceptor;
import com.marineapi.net.interceptors.LoginInterceptor;
import com.marineapi.net.interceptors.PacketInterceptor;
import com.marineapi.net.interceptors.StatusInterceptor;


public class PacketHandler implements PacketInterceptor {
	
	HandshakeInterceptor handshake;
	StatusInterceptor status;
	LoginInterceptor login;
	
	public PacketHandler() {
		handshake = new HandshakeInterceptor();
		status = new StatusInterceptor();
		login = new LoginInterceptor();
	}
	
	public boolean intercept(ByteData data, Client c) {
		if(c.getState().equals(States.HANDSHAKE))
			return handshake.intercept(data, c);
		else
		if(c.getState().equals(States.INTRODUCE))
			return status.intercept(data, c);
		else
		if(c.getState().equals(States.LOGIN))
			return login.intercept(data, c);
				
		
		return false;
		
	}
}
