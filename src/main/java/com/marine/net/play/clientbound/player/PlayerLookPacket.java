package com.marine.net.play.clientbound.player;

import com.marine.io.data.ByteData;
import com.marine.net.Packet;
import com.marine.net.PacketOutputStream;
import com.marine.net.States;
import com.marine.util.Location;

import java.io.IOException;

public class PlayerLookPacket extends Packet {

    private final Location loc;

    public PlayerLookPacket(Location l) {
        loc = l;
    }

    @Override
    public int getID() {
        return 0x05;
    }

    @Override
    public void writeToStream(PacketOutputStream stream) throws IOException {
        ByteData data = new ByteData();

        data.writeFloat(loc.getYaw());
        data.writeFloat(loc.getPitch());
        data.writeBoolean(loc.isOnGround());

        stream.write(getID(), data);
    }

    @Override
    public void readFromBytes(ByteData input) {
    }

    @Override
    public States getPacketState() {
        return States.INGAME;
    }

}
