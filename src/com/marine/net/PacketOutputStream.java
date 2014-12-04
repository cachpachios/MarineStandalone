package com.marine.net;

import java.io.IOException;
import java.io.OutputStream;

public class PacketOutputStream  { // Here we enable encryption and compression if needed
	
	private final OutputStream stream;
	
	private final Client c;
	
	public PacketOutputStream(Client c, OutputStream stream) {
			this.c = c;
			this.stream = stream;
	}

	public void write(byte[] b) throws IOException {
		//TODO Encryption and Compression
		stream.write(b);
	}
}
