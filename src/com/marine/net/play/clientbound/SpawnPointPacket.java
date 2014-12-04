package com.marine.net.play.clientbound;

import java.io.IOException;

import com.marine.io.data.ByteData;
import com.marine.net.Packet;
import com.marine.net.PacketOutputStream;
import com.marine.net.States;
import com.marine.util.Position;

public class SpawnPointPacket extends Packet { // Only used to make the client know where the compass should point

	final Position spawnPoint;
	
	public SpawnPointPacket(Position spawnPoint) {
		this.spawnPoint = spawnPoint;
	}
	
	@Override
	public int getID() {
		return 0x05;
	}

	@Override
	public void writeToStream(PacketOutputStream stream) throws IOException {
		ByteData d = new ByteData();
		d.writeVarInt(getID());
		d.writePosition(spawnPoint);
		d.writePacketPrefix();
	}

	@Override
	public void readFromBytes(ByteData input) {} // Clientbound only

	@Override
	public States getPacketState() {
		return States.INGAME;
	}
}
