package com.marine.net.play.clientbound;

import java.io.IOException;

import com.marine.io.data.ByteData;
import com.marine.net.Packet;
import com.marine.net.PacketOutputStream;
import com.marine.net.States;
import com.marine.player.Player;

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
	public void writeToStream(PacketOutputStream stream) throws IOException {
		ByteData d = new ByteData();
		d.writeVarInt(getID());
		
		d.writeInt(p.getEntityID());
		
		d.writeByte(p.getGamemode().getID());

		d.writeByte(p.getWorld().getDimension().getID());
		d.writeByte((byte)p.getPlayerManager().getServer().getMaxPlayers());
		d.writeUTF8(p.getWorld().getLevelType().getName());
		d.writeBoolean(false);
		
		d.writePacketPrefix();
	}

	@Override
	public void readFromBytes(ByteData input) {} //Client Side Only

	@Override
	public States getPacketState() {
		return States.INGAME;
	}
}
