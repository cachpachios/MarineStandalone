package org.marinemc.util.wrapper;

import org.marinemc.util.Location;
import org.marinemc.util.ObjectMeta;
import org.marinemc.util.vectors.Vector3d;

public class Movment extends ObjectMeta<Location, Vector3d> {

	public Movment(final Location pos, final Vector3d target) {
		super(pos, target);
	}

	public double getDistance() {
		return get().getEuclideanDistance(getMeta());
	}

	public double getDistanceSquared() {
		return get().getEuclideanDistanceSquared(getMeta());
	}

	public double getHeightDifference() {
		return get().y - getMeta().getZ();
	}

	public Vector3d getTargetLocation() {
		return getMeta();
	}

	public Location getStartLocation() {
		return get();
	}

}
