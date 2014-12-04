package com.marine.net;

import com.marine.io.data.ByteData;

import java.io.IOException;
import java.io.OutputStream;

public abstract class Packet {

	public abstract int getID();
	
	public abstract void writeToStream(PacketOutputStream stream) throws IOException;
	
	public abstract void readFromBytes(ByteData input);
	
	public static Packet createSimplePacket(final byte ID, final byte[] inputdata, final States state) {
		Packet p = new Packet() {
			
			States s = state;
			
			ByteData data = new ByteData(inputdata);
			int id = ID;
			
			@Override
			public void writeToStream(PacketOutputStream stream) throws IOException {
					data.writeVarInt(0, id);
					data.writePacketPrefix();
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
