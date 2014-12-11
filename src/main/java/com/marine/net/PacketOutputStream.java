package com.marine.net;

import com.marine.io.data.ByteData;

import java.io.IOException;
import java.io.OutputStream;

public class PacketOutputStream { // Here we enable encryption and compression if needed

    private final OutputStream stream;

    private final Client c;

    public PacketOutputStream(Client c, OutputStream stream) {
        this.c = c;
        this.stream = stream;
    }
    
    public OutputStream getStream() {
    	return stream;
    }

    public void write(int id, byte[] b) throws IOException {
        write(id, new ByteData(b));
    }
    
    public void write(int id, Byte[] b) throws IOException {
        write(id, new ByteData(b));
    }

    public void write(int id, ByteData data) throws IOException {
        //TODO Compress and encrypt :D
        data.writeVarInt(0, id);
        data.writePacketPrefix();
        stream.write(data.getBytes());
    }
}
