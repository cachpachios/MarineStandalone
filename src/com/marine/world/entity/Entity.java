package com.marineapi.world.entity;

import com.marineapi.util.Position;
import com.marineapi.world.World;

public abstract class Entity {
	
	private static int nextEntityID;
	
	public int generateEntityID() {
		nextEntityID++;
		return nextEntityID;
	}
	
	private int entityID;
	private World world;
	
	private Position position;
	
	private int ticksLived;
	
	public abstract int getSendDistance();
	
	public Entity(int ID, World world, Position pos) {
		this.entityID = ID;
		this.world = world;
		this.position = pos;
		this.ticksLived = 0;
	}
	
	public void tick() {
		ticksLived++;
	}
	
	public int getEntityID() {
		return entityID;
	}
	
	public World getWorld() { return world; }

	public double getX() {return position.getX(); }
	public double getY() {return position.getY(); }
	public double getZ() {return position.getZ(); }
	public Position getPosition() { return position; }
	
	public int getSecoundsLived() {
		return ticksLived/20;
	}
	
	public int getTicksLived() {
		return ticksLived;
	}
	
}
