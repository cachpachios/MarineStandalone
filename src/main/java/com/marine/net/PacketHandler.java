package com.marine.net;

import com.marine.StandaloneServer;
import com.marine.io.data.ByteData;
import com.marine.net.interceptors.*;


public class PacketHandler implements PacketInterceptor {

    HandshakeInterceptor handshake;
    StatusInterceptor status;
    LoginInterceptor login;
    IngameInterceptor ingame;

    public PacketHandler(StandaloneServer server) {
        handshake = new HandshakeInterceptor();
        status = new StatusInterceptor();
        login = new LoginInterceptor(server);
        ingame = new IngameInterceptor(server.getPlayerManager());
    }

    public boolean intercept(int id, ByteData data, Client c) {
    	switch(c.getState()) {
    		case HANDSHAKE	: return handshake	.intercept(id, data, c);
    		case INTRODUCE	: return status		.intercept(id, data, c);
    		case LOGIN 		: return login		.intercept(id, data, c);
    		case INGAME 	: return ingame		.intercept(id, data, c);
		default				: return false;
    	}
    }
}
