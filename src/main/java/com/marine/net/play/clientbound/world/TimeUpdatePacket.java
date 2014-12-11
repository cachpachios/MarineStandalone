package com.marine.net.play.clientbound.world;

import com.marine.io.data.ByteData;
import com.marine.net.Packet;
import com.marine.net.PacketOutputStream;
import com.marine.net.States;
import com.marine.world.World;

import java.io.IOException;

public class TimeUpdatePacket extends Packet {
    final long worldTime;
    final long worldAge;

    public TimeUpdatePacket(long worldTime, long worldAge) {
        super();
        this.worldTime = worldTime;
        this.worldAge = worldAge;
    }

    public TimeUpdatePacket(World w) {
        this(w.getTime(), w.getWorldAge());
    }

    @Override
    public int getID() {
        return 0x03;
    }

    @Override
    public void writeToStream(PacketOutputStream stream) throws IOException {
        ByteData d = new ByteData();

        d.writeLong(worldAge);
        d.writeLong(worldTime);

        stream.write(getID(), d.getBytes());
    }

    @Override
    public void readFromBytes(ByteData input) {
    }

    @Override
    public States getPacketState() {
        // TODO Auto-generated method stub
        return null;
    }

}
