///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// MarineStandalone is a minecraft server software and API.
// Copyright (C) MarineMC (marinemc.org)
//
// This program is free software; you can redistribute it and/or modify
// it under the terms of the GNU General Public License as published by
// the Free Software Foundation; either version 2 of the License, or
// (at your option) any later version.
//
// This program is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
// GNU General Public License for more details.
//
// You should have received a copy of the GNU General Public License along
// with this program; if not, write to the Free Software Foundation, Inc.,
// 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

package org.marinemc.util;

import org.json.simple.JSONAware;
import org.json.simple.JSONValue;
import org.marinemc.server.Marine;
import org.marinemc.util.vectors.Vector3;
import org.marinemc.util.vectors.Vector3d;
import org.marinemc.world.World;
import org.marinemc.world.chunk.Chunk;

/**
 * Location class Used for precise positioning (entity location etc)
 *
 * @author Citymonstret
 * @author Fozie
 */
public class Location extends Vector3d implements JSONAware, Cloneable,
		Comparable<Location> { //

	protected World world;
	protected float yaw;
	protected float pitch;

	private boolean onGround;

	/**
	 * Create a new world with default yaw and pitch (0, 0)
	 *
	 * @param world
	 *            World
	 * @param x
	 *            X Coord
	 * @param y
	 *            Y Coord
	 * @param z
	 *            Z Coord
	 */
	public Location(final World world, final double x, final double y,
			final double z) {
		this(world, x, y, z, 0f, 0f);
	}

	/**
	 * Get a new location based on a world and a vector
	 *
	 * @param world
	 *            World
	 * @param v
	 *            Vector3
	 */
	@SuppressWarnings("rawtypes")
	public Location(final World world, final Vector3 v) {
		this(world, (double) v.getX(), (double) v.getY(), (double) v.getZ());
	}

	/**
	 * Create a new location from a json string
	 *
	 * @param json
	 *            JSON string
	 */
	public Location(final String json) {
		super(0d, 0d, 0d);
		final JSONObject object = (JSONObject) JSONValue.parse(json);
		world = Marine.getWorld(object.get("world").toString());
		setX((double) object.get("x"));
		setY((double) object.get("y"));
		setZ((double) object.get("z"));
		yaw = (float) object.get("yaw");
		pitch = (float) object.get("pitch");
	}

	/**
	 * Create a new location
	 *
	 * @param w
	 *            World
	 * @param x
	 *            X Coord
	 * @param y
	 *            Y Coord
	 * @param z
	 *            Z Coord
	 * @param yaw
	 *            Yaw
	 * @param pitch
	 *            Pitch
	 */
	public Location(final World w, final double x, final double y,
			final double z, final float yaw, final float pitch) {
		this(w, x, y, z, yaw, pitch, false);
	}

	public Location(final World world, final double x, final double y,
			final double z, final float yaw, final float pitch,
			final boolean onGround) {
		super(x, y, z);
		this.yaw = yaw;
		this.pitch = pitch;
		this.world = world;
		this.onGround = onGround;
	}

	/**
	 * Create a new location
	 *
	 * @param spawnLocation
	 *            Spawn position
	 * @param w
	 *            World
	 */
	public Location(final Position spawnLocation, final World w) {
		this(w, spawnLocation.getX(), spawnLocation.getY(), spawnLocation
				.getZ());
	}

	/**
	 * Get the world
	 *
	 * @return world
	 */
	public World getWorld() {
		return world;
	}

	/**
	 * Sets the world the location are in
	 *
	 * @param w
	 *            World
	 */
	public void setWorld(final World w) {
		world = w;
	}

	/**
	 * Get the yaw
	 *
	 * @return yaw
	 */
	public float getYaw() {
		return yaw;
	}

	/**
	 * Set the yaw
	 *
	 * @param v
	 *            new yaw
	 */
	public void setYaw(final float v) {
		yaw = v;
	}

	/**
	 * Get the relative position
	 *
	 * @return Position at rounded values
	 */
	public Position getRelativePosition() {
		return new Position(getX().intValue(), getY().intValue(), getZ()
				.intValue());
	}

	/**
	 * Get the pitch
	 *
	 * @return pitch
	 */
	public float getPitch() {
		return pitch;
	}

	/**
	 * Set the pitch
	 *
	 * @param v
	 *            new pitch
	 */
	public void setPitch(final float v) {
		pitch = v;
	}

	/**
	 * Face the yaw towards the specified location
	 *
	 * @param p
	 *            Location too look towards
	 * @return New Location (this)
	 */
	public Location lookAt(final Location p) {
		final double l = p.getX() - getX();
		final double w = p.getZ() - getX();
		final double c = Math.sqrt(l * l + w * w);
		final double alpha1 = -Math.asin(l / c) / Math.PI * 180;
		final double alpha2 = Math.acos(w / c) / Math.PI * 180;
		yaw = (float) (alpha2 > 90 ? 180 - alpha1 : alpha1);
		return this;
	}

	@Override
	public String toJSONString() {
		final JSONObject o = new JSONObject();
		o.put("x", getX());
		o.put("y", getY());
		o.put("z", getZ());
		o.put("yaw", getYaw());
		o.put("pitch", getPitch());
		o.put("world", getWorld().getName());
		return o.toJSONString();
	}

	/**
	 * Turn the location into a JSON Object <br>
	 * Contains: x, y, z, yaw, pitch & world
	 *
	 * @return JSON Object
	 */
	public org.json.JSONObject toJSONObject() {
		final org.json.JSONObject o = new org.json.JSONObject();
		try {
			o.put("x", getX());
			o.put("y", getY());
			o.put("z", getZ());
			o.put("yaw", getYaw());
			o.put("pitch", getPitch());
			o.put("world", getWorld().getName());
		} catch (final Exception e) {
			e.printStackTrace();
		}
		return o;
	}

	public JSONObject toSimpleJSONObject() {
		final JSONObject o = new JSONObject();
		try {
			o.put("x", getX());
			o.put("y", getY());
			o.put("z", getZ());
			o.put("yaw", getYaw());
			o.put("pitch", getPitch());
			o.put("world", getWorld().getName());
		} catch (final Exception e) {
			e.printStackTrace();
		}
		return o;
	}

	@Override
	public String toString() {
		return String
				.format("\"location\": {\"world\": \"%s\", \"x\": %f, \"y\": %f, \"z\": %f}",
						getWorld().getName(), getX(), getY(), getZ());
	}

	/**
	 * Where the actual distance is important
	 *
	 * @param l2
	 *            Other location
	 * @return distance (actual)
	 */
	public double getEuclideanDistance(final Location l2) {
		double x = getX() - l2.getX();
		double y = getY() - l2.getY();
		double z = getZ() - l2.getZ();
		x *= x;
		y *= y;
		z *= z;
		return Math.sqrt(x + y + z);
	}

	/**
	 * Where distance isn't really important, you just want to compare them
	 *
	 * @param l2
	 *            Other location
	 * @return distance squared
	 */
	public double getEuclideanDistanceSquared(final Location l2) {
		final double x = getX() - l2.getX(), y = getY() - l2.getY(), z = getZ()
				- l2.getZ();
		return x * x + y * y + z * z;
	}

	@Override
	public boolean equals(final Object o) {
		if (!(o instanceof Location))
			return false;
		final Location loc = (Location) o;
		return loc.getX().equals(getX()) && loc.getY().equals(getY())
				&& loc.getZ().equals(getZ()) && loc.getYaw() == getYaw()
				&& loc.getPitch() == getPitch()
				&& loc.getWorld().getName().equals(getWorld().getName());
	}

	@Override
	public int hashCode() {
		int hash = 127;
		final int x = getX().intValue(), y = getY().intValue(), z = getZ()
				.intValue();
		hash = hash * 31 + x;
		hash = hash * 31 + y;
		hash = hash * 31 + z;
		hash = (int) (hash * 31 + getYaw());
		hash = (int) (hash * 31 + getPitch());
		return hash * 31 + (world == null ? 127 : world.getName().hashCode());
	}

	@Override
	public Object clone() throws CloneNotSupportedException {
		Location object;
		try {
			object = (Location) super.clone();
		} catch (final Throwable e) {
			object = null;
		}
		if (object == null || !object.getX().equals(getX())
				|| !object.getY().equals(getY())
				|| !object.getZ().equals(getZ())
				|| !getWorld().getName().equals(object.getWorld().getName()))
			return new Location(getWorld(), getX(), getY(), getZ(), getYaw(),
					getPitch());
		else
			return object;
	}

	/**
	 * Is this on ground?
	 *
	 * @return Is this on ground?
	 */
	public boolean isOnGround() {
		return onGround;
	}

	/**
	 * Decide if this is on ground
	 *
	 * @param v
	 *            On ground
	 */
	public void setOnGround(final boolean v) {
		onGround = v;
	}

	/**
	 * Check if the location is in a cube
	 *
	 * @param min
	 *            Min Loc
	 * @param max
	 *            Max Loc
	 * @return boolean
	 */
	public boolean isInAABB(final Location min, final Location max) {
		return x >= min.x && x <= max.x && y >= min.y && y <= max.y
				&& z >= max.z && z <= max.z;
	}

	/**
	 * Check if the location is in the specified sphere
	 *
	 * @param origin
	 *            Sphere origin
	 * @param radius
	 *            Sphere radius
	 * @return boolean
	 */
	public boolean isInSphere(final Location origin, final int radius) {
		return getEuclideanDistanceSquared(origin) < radius * radius;
	}

	/**
	 * Get the chunk at this location
	 *
	 * @return chunk at the specified location
	 */
	public Chunk getChunk() {
		return world.getChunkForce(x.intValue() >> 4, z.intValue() >> 4);
	}

	@Override
	public int compareTo(final Location o) {
		if (o == null)
			throw new NullPointerException("Object specified was null");
		final double x1 = getX(), x2 = o.getX(), y1 = getY(), y2 = o.getY(), z1 = getZ(), z2 = o
				.getZ();
		if (x1 == x2 && y1 == y2 && z1 == z2)
			return 0;
		if (x1 < x2 && y1 < y2 && z1 < z2)
			return -1;
		return 1;
	}

	protected Location self() {
		return this;
	}

}
