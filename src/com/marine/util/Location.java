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

    private float yaw, pitch;
    private final World world;

    public Location(World world, double x, double y, double z) {
        this(world, x, y, z, 0f, 0f);
    }
    
	@SuppressWarnings("rawtypes")
	public Location(World world, Vector3 v) {
        this(world, (double) v.getX(), (double) v.getY(), (double)  v.getZ());
    }
	
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

    public Location(World world, double x, double y, double z, float yaw, float pitch) {
        super(x, y, z);
        this.yaw = yaw;
        this.pitch = pitch;
        this.world = world;
    }

    public Location(Position spawnLocation, World w) {
    	this(w,(double) spawnLocation.getX(), (double) spawnLocation.getY(), (double) spawnLocation.getZ());
	}

	public World getWorld() {
        return this.world;
    }

    public float getYaw() {
        return this.yaw;
    }
    
    public Position getRelativePosition() {
    	return new Position(getX().intValue(), getY().intValue(), getZ().intValue());
    }

    public float getPitch() {
        return this.pitch;
    }

    public Location lookAt(Location p) {
    	double l = p.getX()-getX();
    	double w = p.getZ()-getX();
    	double c = Math.sqrt(l*l + w*w);
    	double alpha1 = -Math.asin(l/c)/Math.PI*180;
    	double alpha2 =  Math.acos(w/c)/Math.PI*180;
    	if (alpha2 > 90)
    		yaw = (float) (180 - alpha1);
    	else
    		yaw = (float) alpha1;
    	
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

    public org.json.JSONObject toJSONObject() {
        org.json.JSONObject o = new org.json.JSONObject();
        try {
            o.put("x", getX());
            o.put("y", getY());
            o.put("z", getZ());
            o.put("yaw", getYaw());
            o.put("pitch", getPitch());
            o.put("world", getWorld().getName());
        } catch(Exception e) {
            e.printStackTrace();
        }
        return o;
    }

    /**
     * Where the actual distance is important
     * @param l2 Other location
     * @return distance (actual)
     */
    public double getEuclideanDistance(Location l2) {
        double x = getX() - l2.getX();
        double y = getY() - l2.getY();
        double z = getZ() - l2.getZ();
        x *= x; y *= y; z *= z;
        return Math.sqrt(x + y + z);
    }

    /**
     * Where distance isn't really important, you just want to compare them
     * @param l2 Other location
     * @return distance squared
     */
    public double getEuclideanDistanceSquared(Location l2) {
        double x = getX() - l2.getX(), y = getY() - l2.getY(), z = getZ() - l2.getZ();
        return (x * x) + (y * y) + (z * z);
    }

    @Override
    public boolean equals(Object o) {
        if(!(o instanceof Location)) return false;
        Location loc = (Location) o;
        return (loc.getX().equals(getX()) && loc.getY().equals(getY()) && loc.getZ().equals(getZ())
                && loc.getYaw() == getYaw() && loc.getPitch() == getPitch() && loc.getWorld().getName().equals(getWorld().getName()));
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        Location object;
        try {
            object = (Location) super.clone();
        } catch(Throwable e) {
            object = null;
        }
        if(object == null || !object.getX().equals(getX()) || !object.getY().equals(getY()) || !object.getZ().equals(getZ()) || !getWorld().getName().equals(object.getWorld().getName())) {
            return new Location(getWorld(), getX(), getY(), getZ(), getYaw(), getPitch());
        } else {
            return object;
        }
    }
}
