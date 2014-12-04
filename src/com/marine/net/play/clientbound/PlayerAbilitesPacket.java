package com.marine.net.play.clientbound;

import java.io.IOException;

import com.marine.io.data.ByteData;
import com.marine.net.Packet;
import com.marine.net.PacketOutputStream;
import com.marine.net.States;
import com.marine.player.PlayerAbilities;

public class PlayerAbilitesPacket extends Packet {

	final PlayerAbilities abilites;
	
	public PlayerAbilitesPacket(PlayerAbilities abilites) {
		this.abilites = abilites;
	}
	
	@Override
	public int getID() {
		return 0x39;
	}

	@Override
	public void writeToStream(PacketOutputStream stream) throws IOException {
		ByteData d = new ByteData();

        @SuppressWarnings("unused")
		byte flags = (byte) ((abilites.isInCreativeMode() ? 8 : 0) | (abilites.canFly() ? 4 : 0) | (false ? 2 : 0) | (abilites.isInCreativeMode() ? 1 : 0));		
		d.writeByte(flags);
		d.writeFloat(abilites.getFlySpeed());
		d.writeFloat(abilites.getWalkSpeed());
		
		stream.write(getID(), d.getBytes());
		
		
	}

	@Override
	public void readFromBytes(ByteData input) {}// Clientbound only

	@Override
	public States getPacketState() {
		return States.INGAME;
	}

}
