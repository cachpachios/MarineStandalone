package com.marine.net.play.clientbound;

import java.io.IOException;
import java.io.OutputStream;

import com.marine.io.data.ByteData;
import com.marine.net.Packet;
import com.marine.net.States;
import com.marine.player.PlayerAbilites;

public class PlayerAbilitesPacket extends Packet {

	final PlayerAbilites abilites;
	
	public PlayerAbilitesPacket(PlayerAbilites abilites) {
		this.abilites = abilites;
	}
	
	@Override
	public int getID() {
		return 0x39;
	}

	@Override
	public void writeToStream(OutputStream stream) throws IOException {
		ByteData d = new ByteData();
		
		d.writeVarInt(getID());
		

        @SuppressWarnings("unused")
		byte flags = (byte) ((abilites.isInCreativeMode() ? 8 : 0) | (abilites.canFly() ? 4 : 0) | (false ? 2 : 0) | (abilites.isInCreativeMode() ? 1 : 0));		
		d.writeByte(flags);
		d.writeFloat(abilites.getFlySpeed());
		d.writeFloat(abilites.getWalkSpeed());
		
		d.writePacketPrefix();
		
		stream.write(d.getBytes());
		
		
	}

	@Override
	public void readFromBytes(ByteData input) {}// Clientbound only

	@Override
	public States getPacketState() {
		return States.INGAME;
	}

}
