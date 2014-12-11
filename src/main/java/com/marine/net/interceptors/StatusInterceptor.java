package com.marine.net.interceptors;

import com.marine.io.data.ByteData;
import com.marine.net.Client;
import com.marine.net.handshake.ListPacket;
import com.marine.net.handshake.PingPacket;

public class StatusInterceptor implements PacketInterceptor {

    @Override
    public boolean intercept(int id, ByteData data, Client c) {
        if (id == 0x00) {
            ListPacket packet = new ListPacket();
            c.sendPacket(packet);
        } else if (id == 0x01) {
            PingPacket packet = new PingPacket();
            packet.readFromBytes(data);
            c.sendPacket(packet);
        }
        return false;
    }

}
