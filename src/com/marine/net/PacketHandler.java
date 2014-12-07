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

    public boolean intercept(ByteData data, Client c) {
        if (c.getState().equals(States.HANDSHAKE))
            return handshake.intercept(data, c);
        else if (c.getState().equals(States.INTRODUCE))
            return status.intercept(data, c);
        else if (c.getState().equals(States.LOGIN))
            return login.intercept(data, c);
        else if (c.getState().equals(States.INGAME))
            return ingame.intercept(data, c);

        return false;

    }
}
