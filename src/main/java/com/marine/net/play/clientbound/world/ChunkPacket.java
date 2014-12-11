package com.marine.net.play.clientbound.world;

import com.marine.io.data.ByteData;
import com.marine.net.Packet;
import com.marine.net.PacketOutputStream;
import com.marine.net.States;
import com.marine.world.Dimension;
import com.marine.world.chunk.Chunk;

import java.io.IOException;

public class ChunkPacket extends Packet {

    final Chunk c;

    public ChunkPacket(Chunk c) {
        this.c = c;
    }

    @Override
    public int getID() {
        return 0x21;
    }

    @Override
    public void writeToStream(PacketOutputStream stream) throws IOException {
        ByteData data = new ByteData();

        data.writeInt(c.getPos().getX());
        data.writeInt(c.getPos().getY());
        data.writeBoolean(true);
        data.writeShort(c.getSectionBitMap());

        ByteData d = c.getData(true, c.getWorld().getDimension() == Dimension.OVERWORLD);

        d.writeVarInt(d.getLength());
        data.writeData(d);

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
