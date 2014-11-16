package com.marineapi.net.handshake;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import com.marineapi.net.Packet;
import com.marineapi.net.States;
import com.marineapi.net.data.ByteData;
import com.marineapi.net.data.ByteEncoder;

public class ListPacket extends Packet {

	
	@Override
	public int getID() {
		return 0x00;
	}

	@Override
	public void writeToStream(OutputStream stream) throws IOException {
		ByteData data = new ByteData();
		
		data.writeend(ByteEncoder.writeVarInt(getID()));
		data.writeend(ByteEncoder.writeUTFPrefixedString(EXAMPLE_SCRIPT));
		
		int l = data.getLength();
		
		data.write(0, ByteEncoder.writeVarInt(l));
		
		stream.write(data.getBytes());
	}

	@Override
	public void readFromBytes(ByteData input)  {
		
	}

	@Override
	public States getPacketState() {
		return States.INTRODUCE;
	}
}

