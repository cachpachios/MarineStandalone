package com.marine.net.login;

import java.io.IOException;

import com.marine.game.chat.ChatColor;
import com.marine.game.chat.ChatComponent;
import com.marine.io.data.ByteData;
import com.marine.net.Packet;
import com.marine.net.PacketOutputStream;
import com.marine.net.States;

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
	public void writeToStream(PacketOutputStream stream) throws IOException {
		ByteData data = new ByteData();
		data.writeVarInt(getID());
		data.writeUTF8(new ChatComponent(msg,true,true,false,false, ChatColor.randomColor()).toString());
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
