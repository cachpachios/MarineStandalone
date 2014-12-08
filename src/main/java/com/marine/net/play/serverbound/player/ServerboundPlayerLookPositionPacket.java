package com.marine.net.play.serverbound.player;

import com.marine.io.data.ByteData;
import com.marine.net.Packet;
import com.marine.net.PacketOutputStream;
import com.marine.net.States;
import com.marine.net.play.clientbound.player.ClientboundPlayerLookPositionPacket;
import com.marine.util.Location;

import java.io.IOException;

public class ServerboundPlayerLookPositionPacket extends Packet { //TODO Relative positions

    private Location l;

    public ServerboundPlayerLookPositionPacket() {
    }

    @Override
    public int getID() {
        return 0x06;
    }

    @Override
    public void writeToStream(PacketOutputStream stream) throws IOException {
    }

    @Override
    public void readFromBytes(ByteData input) {
        double x = input.readDouble();
        double y = input.readDouble();
        double z = input.readDouble();

        float yaw = input.readFloat();
        float pitch = input.readFloat();

        input.readBoolean(); // onGround

        l = new Location(null, x, y, z, yaw, pitch);
    }

    public Location getLocation() {
        return l;
    }

    @Override
    public States getPacketState() {
        return States.INGAME;
    }

    public ClientboundPlayerLookPositionPacket getClientBound() {
        return new ClientboundPlayerLookPositionPacket(l);
    }
}
