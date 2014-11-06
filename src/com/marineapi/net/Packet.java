package com.marineapi.net;

import java.io.IOException;
import java.io.OutputStream;

public abstract class Packet {
	
	public abstract int getID();
	
	public abstract void writeToStream(OutputStream stream) throws IOException;
	
	public abstract void readFromBytes(ByteData input);
	
	public static Packet createSimplePacket(byte ID, byte[] inputdata) {
		Packet p = new Packet() {
			
			ByteData data = new ByteData(inputdata);
			int id = ID;
			
			@Override
			public void writeToStream(OutputStream stream) throws IOException {
					stream.write(id);
					stream.write(data.getBytes());
			}

			@Override
			public void readFromBytes(ByteData input) {
				data = input;
			}

			@Override
			public int getID() {
				return ID;
			}
			
		};
		return p;
	}
	
}
