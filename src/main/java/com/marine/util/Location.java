///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// MarineStandalone is a minecraft server software and API.
// Copyright (C) IntellectualSites (marine.intellectualsites.com)
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

package com.marine.util;

import com.marine.server.Marine;
import com.marine.world.World;
import org.json.simple.JSONAware;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

/**
 * Created 2014-12-01 for MarineStandalone
 *
 * @author Citymonstret
 */
public class Location extends Vector3d implements JSONAware, Cloneable { // Used for absolute positioning (Entites etc)

    private final World world;
    private float yaw, pitch;

    private boolean onGround;

    /**
     * Create a new world with default yaw and pitch (0, 0)
     *
     * @param world World
     * @param x     X Coord
     * @param y     Y Coord
     * @param z     Z Coord
     */
    public Location(World world, double x, double y, double z) {
        this(world, x, y, z, 0f, 0f);
    }

    /**
     * Get a new location based on a world and a vector
     *
     * @param world World
     * @param v     Vector3
     */
    @SuppressWarnings("rawtypes")
    public Location(World world, Vector3 v) {
        this(world, (double) v.getX(), (double) v.getY(), (double) v.getZ());
    }

    /**
     * Create a new location from a json string
     *
     * @param json JSON string
     */
    public Location(String json) {
        super(0d, 0d, 0d);
        JSONObject object = (JSONObject) JSONValue.parse(json);
        this.world = Marine.getWorld(object.get("world").toString());
        this.setX((double) object.get("x"));
        this.setY((double) object.get("y"));
        this.setZ((double) object.get("z"));
        this.yaw = (float) object.get("yaw");
        this.pitch = (float) object.get("pitch");
    }

    /**
     * Create a new location
     *
     * @param world World
     * @param x     X Coord
     * @param y     Y Coord
     * @param z     Z Coord
     * @param yaw   Yaw
     * @param pitch Pitch
     */

    public Location(World w, double x, double y, double z, float yaw, float pitch) {
        this(w, x, y, z, yaw, pitch, false);
    }

    public Location(World world, double x, double y, double z, float yaw, float pitch, boolean onGround) {
        super(x, y, z);
        this.yaw = yaw;
        this.pitch = pitch;
        this.world = world;
        this.onGround = onGround;
    }

    /**
     * Create a new location
     *
     * @param spawnLocation Spawn position
     * @param w             World
     */
    public Location(Position spawnLocation, World w) {
        this(w, (double) spawnLocation.getX(), (double) spawnLocation.getY(), (double) spawnLocation.getZ());
    }

    /**
     * Get the world
     *
     * @return world
     */
    public World getWorld() {
        return this.world;
    }

    /**
     * Get the yaw
     *
     * @return yaw
     */
    public float getYaw() {
        return this.yaw;
    }

    public void setYaw(float v) {
        this.yaw = v;
    }

    /**
     * Get the relative position
     *
     * @return Position at rounded values
     */
    public Position getRelativePosition() {
        return new Position(getX().intValue(), getY().intValue(), getZ().intValue());
    }

    /**
     * Get the pitch
     *
     * @return pitch
     */
    public float getPitch() {
        return this.pitch;
    }

    public void setPitch(float v) {
        this.pitch = v;
    }

    /**
     * Face the yaw towards the specified location
     *
     * @param p Location too look towards
     * @return New Location (this)
     */
    public Location lookAt(Location p) {
        double l = p.getX() - getX();
        double w = p.getZ() - getX();
        double c = Math.sqrt(l * l + w * w);
        double alpha1 = -Math.asin(l / c) / Math.PI * 180;
        double alpha2 = Math.acos(w / c) / Math.PI * 180;
        this.yaw = (float) ((alpha2 > 90) ? (180 - alpha1) : alpha1);
        return this;
    }

    @Override
    public String toJSONString() {
        JSONObject o = new JSONObject();
        o.put("x", getX());
        o.put("y", getY());
        o.put("z", getZ());
        o.put("yaw", getYaw());
        o.put("pitch", getPitch());
        o.put("world", getWorld().getName());
        return o.toJSONString();
    }

    /**
     * Turn the location into a JSON Object
     * <br>
     * Contains: x, y, z, yaw, pitch & world
     *
     * @return JSON Object
     */
    public org.json.JSONObject toJSONObject() {
        org.json.JSONObject o = new org.json.JSONObject();
        try {
            o.put("x", getX());
            o.put("y", getY());
            o.put("z", getZ());
            o.put("yaw", getYaw());
            o.put("pitch", getPitch());
            o.put("world", getWorld().getName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return o;
    }

    public JSONObject toSimpleJSONObject() {
        JSONObject o = new JSONObject();
        try {
            o.put("x", getX());
            o.put("y", getY());
            o.put("z", getZ());
            o.put("yaw", getYaw());
            o.put("pitch", getPitch());
            o.put("world", getWorld().getName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return o;
    }

    @Override
    public String toString() {
        return String.format("\"location\": {\"world\": \"%s\", \"x\": %f, \"y\": %f, \"z\": %f}", getWorld().getName(), getX(), getY(), getZ());
    }

    /**
     * Where the actual distance is important
     *
     * @param l2 Other location
     * @return distance (actual)
     */
    public double getEuclideanDistance(Location l2) {
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
     * @param l2 Other location
     * @return distance squared
     */
    public double getEuclideanDistanceSquared(Location l2) {
        double x = getX() - l2.getX(), y = getY() - l2.getY(), z = getZ() - l2.getZ();
        return (x * x) + (y * y) + (z * z);
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Location)) return false;
        Location loc = (Location) o;
        return (loc.getX().equals(getX()) && loc.getY().equals(getY()) && loc.getZ().equals(getZ())
                && loc.getYaw() == getYaw() && loc.getPitch() == getPitch() && loc.getWorld().getName().equals(getWorld().getName()));
    }

    @Override
    public int hashCode() {
        int hash = 127;
        int x = getX().intValue(), y = getY().intValue(), z = getZ().intValue();
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
        } catch (Throwable e) {
            object = null;
        }
        if (object == null || !object.getX().equals(getX()) || !object.getY().equals(getY()) || !object.getZ().equals(getZ()) || !getWorld().getName().equals(object.getWorld().getName())) {
            return new Location(getWorld(), getX(), getY(), getZ(), getYaw(), getPitch());
        } else {
            return object;
        }
    }

    public boolean isOnGround() {
        return onGround;
    }

    public void setOnGround(boolean v) {
        onGround = v;
    }
}
