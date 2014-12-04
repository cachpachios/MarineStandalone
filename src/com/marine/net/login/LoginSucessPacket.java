package com.marine.net.login;

import java.io.IOException;

import com.marine.io.data.ByteData;
import com.marine.net.Packet;
import com.marine.net.PacketOutputStream;
import com.marine.net.States;
import com.marine.player.Player;

public class LoginSucessPacket extends Packet {

	public Player p;
	
	public LoginSucessPacket(Player p) {
		this.p = p;
	}
	
	@Override
	public int getID() {
		return 0x02;
	}

	@Override
	public void writeToStream(PacketOutputStream stream) throws IOException {
		ByteData d = new ByteData();
		d.writeVarInt(getID());
		
        d.writeUTF8(p.getUUID() == null ? "" : p.getUUID().toString());
		d.writeUTF8(p.getName());
	
		d.writePacketPrefix();
		
		stream.write(d.getBytes());
	}

	@Override
	public void readFromBytes(ByteData input) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public States getPacketState() {
		// TODO Auto-generated method stub
		return States.LOGIN;
	}
}
