package org.marinemc.net.play.clientbound.player;

import java.io.IOException;

import org.marinemc.game.player.Player;
import org.marinemc.io.data.ByteData;
import org.marinemc.net.Packet;
import org.marinemc.net.PacketOutputStream;
import org.marinemc.net.States;

/*
 * 
 * Used to spawn a player locally when they are in sight.
 * Warning this is not used to tell a client to spawn themself!
 * 
 * @author Fozie
 * 
 */
public class SpawnPlayerPacket extends Packet {
	final Player p;
	
	public SpawnPlayerPacket(final Player p) {
		this.p = p;
	}

	@Override
	public int getID() {
		return 0x0C;
	}

	@Override
	public void writeToStream(PacketOutputStream stream) throws IOException {
		ByteData d = new ByteData();
		
		d.writeVarInt(p.getEntityID());
		
		d.writeUUID(p.getUUID());
		
		d.writeInt((int) p.getX() * 32);
		d.writeInt((int) p.getY() * 32);
		d.writeInt((int) p.getZ() * 32);
		
		d.writeByte((byte) (((p.getLocation().getYaw() % 360) / 360) * 256));
		
		d.writeByte((byte) (((p.getLocation().getPitch() % 360) / 360) * 256));
		
		// WARNING FOLLOWING CANT BE -1 IT WILL CRASH THE CLIENT
		d.writeShort((short) 1); // TODO : In hand item like p.getInHand(); 
		
		d.writeByte((byte) 127); // TODO: Entity Metadata :p
		
		stream.write(getID(), d);
	}

	@Override
	public void readFromBytes(ByteData input) {
	}

	@Override
	public States getPacketState() { return States.INGAME; }
}
