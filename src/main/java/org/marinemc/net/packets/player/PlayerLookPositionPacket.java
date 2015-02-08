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

	double x,y,z;
	float yaw,pitch;
	
	boolean onGround;
	
	public PlayerLookPositionPacket(final Location l) {
		this();
		this.x = l.getX();
		this.y = l.getY();
		this.z = l.getZ();
		this.yaw = l.getYaw();
		this.pitch = l.getPitch();
	}
	
	public PlayerLookPositionPacket() {
		super(0x06, States.INGAME);
	}
	
	@Override
	public void writeToStream(final PacketOutputStream stream)	throws IOException {
		final ByteList d = new ByteList();
		
		d.writeDouble(x);
		d.writeDouble(y);
		d.writeDouble(z);

		d.writeFloat(yaw);
		d.writeFloat(pitch);

		d.writeBoolean(onGround);
		
		//Other id for outgoing
		stream.write(0x08, d);
	}

	@Override
	public void readFromBytes(final ByteInput input) {
		if(input.getRemainingBytes() < 30)
			return;
		x = input.readDouble();
		y = input.readDouble();
		z = input.readDouble();
		
		yaw = input.readFloat();
		pitch = input.readFloat();
		onGround = input.readBoolean(); // onGround
	}

	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}

	public double getZ() {
		return z;
	}

	public void setZ(double z) {
		this.z = z;
	}

	public float getYaw() {
		return yaw;
	}

	public void setYaw(float yaw) {
		this.yaw = yaw;
	}

	public float getPitch() {
		return pitch;
	}

	public void setPitch(float pitch) {
		this.pitch = pitch;
	}

	public boolean isOnGround() {
		return onGround;
	}

	public void setOnGround(boolean onGround) {
		this.onGround = onGround;
	}
}
