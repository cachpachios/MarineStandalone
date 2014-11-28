package com.marineapi.net.login;

import java.io.IOException;
import java.io.OutputStream;

import com.marineapi.game.chat.ChatColor;
import com.marineapi.game.chat.RawChatMessage;
import com.marineapi.io.data.ByteData;
import com.marineapi.net.Packet;
import com.marineapi.net.States;

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
		data.writeUTF8(new RawChatMessage(msg,true,true,false,false,ChatColor.AQUA).toString());
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
