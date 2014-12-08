package com.marine.net.play.serverbound.player;

import com.marine.io.data.ByteData;
import com.marine.net.Packet;
import com.marine.net.PacketOutputStream;
import com.marine.net.States;

import java.io.IOException;

public class PlayerPositionPacket extends Packet {

    public double X, Y, Z; // Absolute Position (Y is feets, head = Y+1.62)
    public boolean onGround; // Inverted isAirborn

    @Override
    public int getID() {
        return 0x04;
    }

    @Override
    public void writeToStream(PacketOutputStream stream) throws IOException {
    } // Serverbound packet only!

    @Override
    public void readFromBytes(ByteData input) {
        X = input.readDouble();
        Y = input.readDouble();
        Z = input.readDouble();

        onGround = input.readBoolean();
    }

    @Override
    public States getPacketState() {
        return States.INGAME;
    }

}
