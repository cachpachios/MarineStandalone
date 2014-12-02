package com.marine.net.play.clientbound;

import com.marine.io.data.ByteData;
import com.marine.net.Packet;
import com.marine.net.States;
import com.marine.player.Player;

import java.io.IOException;
import java.io.OutputStream;

public class JoinGamePacket extends Packet{
	
	final Player p;
	
	public JoinGamePacket(Player p) {
		this.p = p;
	}

	@Override
	public int getID() {
		return 0x01;
	}

	@Override
	public void writeToStream(OutputStream stream) throws IOException {
		ByteData d = new ByteData();
		d.writeVarInt(getID());
		d.writeByte(p.getGamemode().getID());
		d.writeByte(p.getWorld().getDimension().getID());
		
	}

	@Override
	public void readFromBytes(ByteData input) {} //Client Side Only

	@Override
	public States getPacketState() {
		return States.INGAME;
	}
}
