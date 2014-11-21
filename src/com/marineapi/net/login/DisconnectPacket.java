package com.marineapi.net.login;

import java.io.IOException;
import java.io.OutputStream;

import com.marineapi.net.Packet;
import com.marineapi.net.States;
import com.marineapi.net.data.ByteData;
import com.marineapi.player.ChatMessage;

public class DisconnectPacket extends Packet {

	String msg;
	
	public DisconnectPacket(String msg) {
		this.msg = msg;
	}
	
	@Override
	public int getID() {
		return 0x00;
	}

	@Override
	public void writeToStream(OutputStream stream) throws IOException {
		ByteData data = new ByteData();
		data.writeVarInt(getID());
		data.writeUTF8(new ChatMessage(msg).toString());
		data.writePacketPrefix();
		
		stream.write(data.getBytes());
	}

	@Override
	public void readFromBytes(ByteData input) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public States getPacketState() {
		// TODO Auto-generated method stub
		return null;
	}
}
