package org.marinemc.world.entity;

import java.lang.ref.WeakReference;

import org.marinemc.util.Location;
import org.marinemc.world.World;

/**
 * Replaces setters for values to update to trackers
 * 
 * @author Fozie
 */
public class EntityLocation extends Location {

	WeakReference<Entity> ent;

	public EntityLocation(final Entity e, final World world, final double x,
			final double y, final double z, final float yaw, final float pitch,
			final boolean onGround) {
		super(world, x, y, z, yaw, pitch, onGround);
		ent = new WeakReference<>(e);
	}

	public EntityLocation(final Entity e, final Location l) {
		this(e, l.getWorld(), l.x, l.y, l.z, l.getYaw(), l.getPitch(), l
				.isOnGround());
	}

	@Override
	public void setX(final Double x) {
		if (!referenceCheck())
			ent.get().teleport(x, y, z);
		else
			self().setX(x);
	}

	public void localSetX(final double x) {
		this.x = x;
	}

	public void localSetY(final double y) {
		this.y = y;
	}

	public void localSetZ(final double z) {
		this.z = z;
	}

	@Override
	public void setY(final Double y) {
		if (referenceCheck())
			self().setY(y);
		else
			ent.get().teleport(x, y, z);
	}

	@Override
	public void setZ(final Double z) {
		if (referenceCheck())
			self().setZ(z);
		else
			ent.get().teleport(x, y, z);
	}

	@Override
	public void setPitch(final float pitch) {
		if (referenceCheck())
			self().setPitch(pitch);
		else
			ent.get().look(pitch, yaw);
	}

	@Override
	public void setYaw(final float yaw) {
		if (referenceCheck())
			self().setYaw(yaw);
		else
			ent.get().look(pitch, yaw);
	}

	private boolean referenceCheck() {
		return ent == null;
	}

	protected void localSetPitch(final float pitch) {
		self().setPitch(pitch);
	}

	protected void localSetYaw(final float yaw) {
		self().setYaw(yaw);
	}

}
