package com.marine.net;

import java.io.IOException;
import java.io.OutputStream;

import com.marine.io.data.ByteData;

public class PacketOutputStream  { // Here we enable encryption and compression if needed
	
	private final OutputStream stream;
	
	private final Client c;
	
	public PacketOutputStream(Client c, OutputStream stream) {
			this.c = c;
			this.stream = stream;
	}

	public void write(int id, byte[] b) throws IOException {
			//TODO Compress and encrypt :D
			ByteData fD = new ByteData(b);
			fD.writeVarInt(0,id);
			fD.writePacketPrefix();
			stream.write(fD.getBytes());
	}
}
