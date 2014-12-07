package com.marine.net.login;

import com.marine.io.data.ByteData;
import com.marine.net.Packet;
import com.marine.net.PacketOutputStream;
import com.marine.net.States;

import java.io.IOException;

public class LoginPacket extends Packet {

    public String name;

    @Override
    public int getID() {
        return 0x00;
    }

    @Override
    public void writeToStream(PacketOutputStream stream) throws IOException {
        // SERVERBOUND PACKET
    }

    @Override
    public void readFromBytes(ByteData input) {
        name = input.readUTF8();
    }

    @Override
    public States getPacketState() {
        return States.LOGIN;
    }
}
