package com.marine.world.entity;

import com.marine.Logging;
import com.marine.util.Location;
import com.marine.util.Position;
import com.marine.util.Vector3d;
import com.marine.world.World;

public abstract class Entity {
	
	private static int nextEntityID;
	
	public int generateEntityID() {
		nextEntityID++;
		return nextEntityID;
	}
	
	private int entityID;
	private World world;
	
	private Location position;
	
	private int ticksLived;
	
	public abstract int getSendDistance();
	
	public Entity(int ID, World world, Location pos) {
		this.entityID = ID;
		this.world = world;
		this.position = pos;
		this.ticksLived = 0;
	}
	
	public abstract void update(); // Called each tick for ai/other updating
	
	public final void tick() {
		if(ticksLived >= Integer.MAX_VALUE -2) {
			Logging.getLogger().error("Entity lived more than 3.6 years impressive! but now we have to remove some age because of memory:/");
			ticksLived = -1;
		}
			ticksLived++;
	}
	
	public int getEntityID() {
		return entityID;
	}
	
	public World getWorld() { return world; }

	public double getX() {return position.getX(); }
	public double getY() {return position.getY(); }
	public double getZ() {return position.getZ(); }
	public Position getRelativePosition() { return (Position) position.toIntVector(); }
	public Vector3d getAbsolutePosition() { return position; }
	
	
	public int getSecoundsLived() {
		return ticksLived/20;
	}
	
	public int getTicksLived() {
		return ticksLived;
	}
	
}
