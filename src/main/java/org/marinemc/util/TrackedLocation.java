package org.marinemc.util;

import org.marinemc.util.vectors.Vector3;
import org.marinemc.util.vectors.Vector3b;
import org.marinemc.util.vectors.Vector3d;
import org.marinemc.world.World;

public class TrackedLocation extends Location {

	private double oldX,oldY,oldZ;
	
	public TrackedLocation(World world, double x, double y, double z, float yaw, float pitch, boolean onGround) {
		super(world, x, y, z, yaw, pitch, onGround);
		this.oldX = x;
		this.oldY = x;
		this.oldZ = x;
	}
	
	public TrackedLocation(Location l) {
		super(l.getWorld(),l.getX(), l.getY(), l.getZ(), l.getYaw(), l.getPitch(), l.isOnGround());
		this.oldX = x;
		this.oldY = x;
		this.oldZ = x;
	}
	
	@Override
	public void setX(Double d) {
		oldX = x;
		this.x = d;
	}
	
	@Override
	public void setY(Double d) {
		oldX = y;
		this.y = d;
	}
	
	@Override
	public void setZ(Double d) {
		oldX = z;
		this.z = d;
	}

	public double getOldX() {
		return oldX;
	}

	public void setOldX(double oldX) {
		this.oldX = oldX;
	}

	public double getOldY() {
		return oldY;
	}

	public void setOldY(double oldY) {
		this.oldY = oldY;
	}

	public double getOldZ() {
		return oldZ;
	}

	public void setOldZ(double oldZ) {
		this.oldZ = oldZ;
	}
	
	public Vector3d getOldPosition() {
		return new Vector3d(oldX, oldY, oldZ);
	}
	
	public Vector3d getPosition() {
		return new Vector3d(x,y,z);
	}
	
	public Vector3<Byte> getDifferentialFixed32() {
		final Vector3<Double> v = getPosition();
		v.subtract(getOldPosition());
		return new Vector3b((byte)(v.getX().byteValue()*32), (byte)(v.getY().byteValue()*32), (byte)(v.getZ().byteValue()*32));
	}
	
}
