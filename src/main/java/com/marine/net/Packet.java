package com.marine.net;

import com.marine.io.data.ByteData;

import java.io.IOException;

public abstract class Packet {

    public static Packet createSimplePacket(final byte ID, final byte[] inputdata, final States state) {
        Packet p = new Packet() {

            States s = state;

            ByteData data = new ByteData(inputdata);

            @Override
            public void writeToStream(PacketOutputStream stream) throws IOException {
                stream.write(ID, data.getBytes());
            }

            @Override
            public void readFromBytes(ByteData input) {
            }

            @Override
            public int getID() {
                return ID;
            }

            @Override
            public States getPacketState() {
                return s;
            }

        };
        return p;
    }

    public abstract int getID();

    public abstract void writeToStream(PacketOutputStream stream) throws IOException;

    public abstract void readFromBytes(ByteData input);

    public abstract States getPacketState();

}
