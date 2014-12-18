package org.marinemc.net.play.clientbound.world.entities;

import java.io.IOException;

import org.marinemc.io.data.ByteData;
import org.marinemc.net.Packet;
import org.marinemc.net.PacketOutputStream;
import org.marinemc.net.States;
import org.marinemc.util.vectors.Vector3;
import org.marinemc.world.entity.Entity;

/**
 * @author Fozie
 */
public class EntityLookMovePacket extends Packet {
	
	final Entity e;
	
	public EntityLookMovePacket(Entity ent) {
		this.e = ent;
	}
	
	@Override
	public int getID() {
		return 0x17;
	}

	@Override
	public void writeToStream(PacketOutputStream stream) throws IOException {
		ByteData data = new ByteData();
		
		data.writeVarInt(e.getEntityID());
		
		final Vector3<Byte> sub = e.getTrackedPosition().getDifferentialFixed32();
		
		data.writeByte(sub.x);
		data.writeByte(sub.y);
		data.writeByte(sub.z);

		data.writeByte((byte) (((e.getPosition().getYaw() % 360) / 360) * 256));
		data.writeByte((byte) (((e.getPosition().getPitch() % 360) / 360) * 256));
		
		data.writeBoolean(e.getPosition().isOnGround());
		
		stream.write(getID(), data);
	}

	@Override
	public void readFromBytes(ByteData input) {}

	@Override
	public States getPacketState() {
		return States.INGAME;
	}

}
