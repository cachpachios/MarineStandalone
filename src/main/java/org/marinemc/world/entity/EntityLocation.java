package org.marinemc.world.entity;

import java.lang.ref.WeakReference;

import org.marinemc.util.Location;
import org.marinemc.world.World;
/**
 * Replaces setters for values to update to trackers
 * @author Fozie
 */
public class EntityLocation extends Location {

	WeakReference<Entity> ent;
	
	public EntityLocation(final Entity e, final World world, double x, double y, double z, float yaw, float pitch, boolean onGround) {
		super(world, x, y, z, yaw, pitch, onGround);
		this.ent = new WeakReference<>(e);
	}
	
	public EntityLocation(final Entity e, final Location l) {
		this(e, l.getWorld(), l.x, l.y, l.z, l.getYaw(), l.getPitch(), l.isOnGround());
	}
	
	@Override
	public void setX(Double x) {
		if(!referenceCheck())
			ent.get().teleport(x, y, z);
		else
			this.self().setX(x);
	}
	
	public void localSetX(double x) {this.self().setX(x);}
	public void localSetY(double y) {this.self().setX(y);}
	public void localSetZ(double z) {this.self().setX(z);}
	
	@Override
	public void setY(Double y) {
		if(referenceCheck())
			this.self().setY(y);
		else
			ent.get().teleport(x, y, z);
	}
	
	@Override
	public void setZ(Double z) {
		if(referenceCheck())
			this.self().setZ(z);
		else
			ent.get().teleport(x, y, z);
	}
	
	@Override
	public void setPitch(float pitch) {
		if(referenceCheck())
			this.self().setPitch(pitch);
		else
			ent.get().look(pitch, yaw);
	}
	
	@Override
	public void setYaw(float yaw) {
		if(referenceCheck())
			this.self().setYaw(yaw);
		else
			ent.get().look(pitch, yaw);
	}
	
	private boolean referenceCheck() {
		return ent == null;
	}

	protected void localSetPitch(float pitch) {
		this.self().setPitch(pitch);
	}
	protected void localSetYaw(float yaw) {
		this.self().setYaw(yaw);
	}
	
}
