package com.marine.net.interceptors;

import com.marine.io.data.ByteData;
import com.marine.net.Client;
import com.marine.net.handshake.ClientHandshake;

public class HandshakeInterceptor implements PacketInterceptor {

    @Override
    public boolean intercept(int id, ByteData data, Client c) {
        if (id == 0x00) {
            ClientHandshake packet = new ClientHandshake();
            packet.readFromBytes(data);
            c.setState(packet.getState());
            return true;
        }

        return false;
    }
}
