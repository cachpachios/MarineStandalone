package org.marinemc.util.wrapper;

import org.marinemc.util.Location;
import org.marinemc.util.ObjectMeta;

public class Movment extends ObjectMeta<Location, Location> {

	public Movment(final Location pos, final Location target) {
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

	public Location getTargetLocation() {
		return getMeta();
	}

	public Location getStartLocation() {
		return get();
	}

}