package com.marineapi.net;

import java.io.IOException;
import java.io.OutputStream;

import com.marineapi.io.data.ByteData;

public abstract class Packet {

	public abstract int getID();
	
	public abstract void writeToStream(OutputStream stream) throws IOException;
	
	public abstract void readFromBytes(ByteData input);
	
	public static Packet createSimplePacket(byte ID, byte[] inputdata, States state) {
		Packet p = new Packet() {
			
			States s = state;
			
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

			@Override
			public States getPacketState() {
				return s;
			}
			
		};
		return p;
	}
	
	public abstract States getPacketState();
	
}
