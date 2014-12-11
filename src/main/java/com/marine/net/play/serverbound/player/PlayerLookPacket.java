package com.marine.net.play.serverbound.player;

import com.marine.io.data.ByteData;
import com.marine.net.Packet;
import com.marine.net.PacketOutputStream;
import com.marine.net.States;

import java.io.IOException;

public class PlayerLookPacket extends Packet {

    private float yaw, pitch;
    private boolean onGround;

    public float getYaw() {
        return yaw;
    }

    public float getPitch() {
        return pitch;
    }

    public boolean getOnGround() {
        return onGround;
    }

    @Override
    public int getID() {
        return 0x05;
    }

    @Override
    public void writeToStream(PacketOutputStream stream) throws IOException {
    }

    @Override
    public void readFromBytes(ByteData input) {
        yaw = input.readFloat();
        pitch = input.readFloat();
        onGround = input.readBoolean();
    }

    @Override
    public States getPacketState() {
        return States.INGAME;
    }

}
