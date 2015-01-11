package org.marinemc.net.packets.player;

import java.io.IOException;

import org.marinemc.io.binary.ByteInput;
import org.marinemc.io.binary.ByteList;
import org.marinemc.net.Packet;
import org.marinemc.net.PacketOutputStream;
import org.marinemc.net.States;
import org.marinemc.util.Location;

/**
 * Warning the method getID()
 * Only gives the ID for the serverbound version
 * @author Caspar
 *
 */
public class PlayerLookPositionPacket extends Packet {

	Location location;
	
	public PlayerLookPositionPacket(Location l) {
		this();
		this.location = l;
	}
	
	public PlayerLookPositionPacket() {
		super(0x06, States.INGAME);
	}
	
	@Override
	public void writeToStream(final PacketOutputStream stream)	throws IOException {
		final ByteList d = new ByteList();
		
		d.writeDouble(location.getX());
		d.writeDouble(location.getY());
		d.writeDouble(location.getZ());

		d.writeFloat(location.getYaw());
		d.writeFloat(location.getPitch());

		d.writeBoolean(location.isOnGround());
		
		//Other id for outgoing
		stream.write(0x08, d);
	}

	@Override
	public void readFromBytes(final ByteInput input) {
		if(input.getRemainingBytes() < 30)
			return;
		final double x = input.readDouble();
		final double y = input.readDouble();
		final double z = input.readDouble();
		final float yaw = input.readFloat();
		final float pitch = input.readFloat();
		
		location = new Location(null, x, y, z, yaw, pitch);
		location.setOnGround(input.readBoolean()); // onGround
	}
	
	public Location getLocation() { return location; }
}
