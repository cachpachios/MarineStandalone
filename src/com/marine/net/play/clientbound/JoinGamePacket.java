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
		
		d.writeInt(p.getEntityID());
		
		d.writeByte(p.getGamemode().getID()); // Gamemode

		d.writeByte(p.getWorld().getDimension().getID()); // Dimension
		d.writeByte(p.getPlayerManager().getServer().getDifficulty().getID()); // Difficulty
		
		d.writeByte((byte)20); // MaxPlayers
		d.writeUTF8("flat");
		d.writeBoolean(false);
		
		stream.write(getID(), d.getBytes());
	}

	@Override
	public void readFromBytes(ByteData input) {} //Client Side Only

	@Override
	public States getPacketState() {
		return States.INGAME;
	}
}
