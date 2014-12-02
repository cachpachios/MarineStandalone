package com.marine.util;

import com.marine.world.World;

/**
 * Created 2014-12-01 for MarineStandalone
 *
 * @author Citymonstret
 * @param <T>
 */
public class Location extends Vector3d { // Used for absolute positioning (Entites etc)

    private final float yaw, pitch;
    private final World world;

    public Location(World world, double x, double y, double z) {
        this(world, x, y, z, 0f, 0f);
    }
    
	@SuppressWarnings("rawtypes")
	public Location(World world, Vector3 v) {
        this(world, (double) v.getX(), (double) v.getY(), (double)  v.getZ());
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
}
