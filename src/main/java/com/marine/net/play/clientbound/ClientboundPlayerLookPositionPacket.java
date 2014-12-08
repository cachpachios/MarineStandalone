package com.marine.net.play.clientbound;

import com.marine.io.data.ByteData;
import com.marine.net.Packet;
import com.marine.net.PacketOutputStream;
import com.marine.net.States;
import com.marine.net.play.serverbound.ServerboundPlayerLookPositionPacket;
import com.marine.util.Location;

import java.io.IOException;

public class ClientboundPlayerLookPositionPacket extends Packet { //TODO Relative positions

    final Location l;

    public ClientboundPlayerLookPositionPacket(Location l) {
        this.l = l;
    }

    public ClientboundPlayerLookPositionPacket(ServerboundPlayerLookPositionPacket l) {
        this.l = l.getLocation();
    }

    @Override
    public int getID() {
        return 0x08;
    }

    @Override
    public void writeToStream(PacketOutputStream stream) throws IOException {
        ByteData d = new ByteData();
        
        d.writeDouble(l.getX());
        d.writeDouble(l.getY());
        d.writeDouble(l.getZ());

        d.writeFloat(l.getYaw());
        d.writeFloat(l.getPitch());

        d.writeByte((byte) -1); // Bitmap TODO make it dynamic

        stream.write(getID(), d);
    }

    @Override
    public void readFromBytes(ByteData input) {

    }

    @Override
    public States getPacketState() {
        return States.INGAME;
    }
}
