package com.marine.net.login;

import com.marine.game.chat.ChatColor;
import com.marine.game.chat.RawChatMessage;
import com.marine.io.data.ByteData;
import com.marine.net.Packet;
import com.marine.net.States;

import java.io.IOException;
import java.io.OutputStream;

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
		data.writeUTF8(new RawChatMessage(msg,true,true,false,false, ChatColor.randomColor()).toString());
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
