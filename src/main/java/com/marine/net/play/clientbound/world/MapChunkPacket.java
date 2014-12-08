package com.marine.net.play.clientbound.world;

import com.marine.io.data.ByteData;
import com.marine.net.Packet;
import com.marine.net.PacketOutputStream;
import com.marine.net.States;
import com.marine.world.Dimension;
import com.marine.world.World;
import com.marine.world.chunk.Chunk;

import java.io.IOException;
import java.util.List;

public class MapChunkPacket extends Packet {

    List<Chunk> chunks;

    World world;

    public MapChunkPacket(World w, List<Chunk> chunks) {
        this.chunks = chunks;
        world = w;
    }

    @Override
    public int getID() {
        return 0x26;
    }

    @Override
    public void writeToStream(PacketOutputStream stream) throws IOException {
        ByteData data = new ByteData();

        data.writeBoolean(world.getDimension() == Dimension.OVERWORLD);

        data.writeVarInt(chunks.size());

        for (Chunk c : chunks) {
            //Write Chunk metadata
            data.writeInt(c.getPos().getX());
            data.writeInt(c.getPos().getY());
            data.writeShort(c.getSectionBitMap());
        }
        for (Chunk c : chunks) {
            // Write Chunk data
            data.writeData(c.getData(true, true));
        }

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
