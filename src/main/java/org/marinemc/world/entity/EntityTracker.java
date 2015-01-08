package org.marinemc.world.entity;

import org.marinemc.util.annotations.Cautious;
import org.marinemc.util.annotations.Clientside;
import org.marinemc.util.vectors.Vector3d;

public interface EntityTracker {
	@Clientside
	public void killLocalEntity			(final Entity e);
	@Clientside
	public void killLocalEntity			(final Integer e);
	@Clientside
	public void killLocalEntities		(final Entity[] e);
	@Clientside
	public void killLocalEntities		(final Integer[] e);
	
	public boolean doesTrackEntity		(final Entity e);
	
	@Clientside
	public void updateLocalEntityMove	(final Entity e, double x, double y, double z);
	@Clientside
	public void updateLocalEntityLook	(final Entity e);
	@Clientside
	public void teleportLocalEntity		(final Entity e, double x, double y, double z);
	
	@Cautious
	public Vector3d getLastLocalySeenPosition(final Entity e);
}
