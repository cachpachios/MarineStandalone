package com.marine.net.login;

import com.marine.io.data.ByteData;
import com.marine.net.Packet;
import com.marine.net.PacketOutputStream;
import com.marine.net.States;
import com.marine.player.Player;

import java.io.IOException;
import java.util.UUID;

public class LoginSucessPacket extends Packet {

    public Player p;

    public LoginSucessPacket(Player p) {
        this.p = p;
    }

    @Override
    public int getID() {
        return 0x02;
    }

    @Override
    public void writeToStream(PacketOutputStream stream) throws IOException {
        ByteData d = new ByteData();

        d.writeUTF8(UUID.randomUUID().toString());
        d.writeUTF8(p.getName());


        stream.write(getID(), d);
    }

    @Override
    public void readFromBytes(ByteData input) {
        // TODO Auto-generated method stub

    }

    @Override
    public States getPacketState() {
        // TODO Auto-generated method stub
        return States.LOGIN;
    }
}
