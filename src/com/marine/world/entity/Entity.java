package com.marine.world.entity;

import com.marine.util.Position;
import com.marine.world.World;

public abstract class Entity {
	
	private static int nextEntityID;
	
	public static int generateEntityID() {
		nextEntityID++;
		return nextEntityID;
	}
	
	private final int entityID;
	private final World world;
	
	private final Position position;
	
	private int ticksLived;
	
	public abstract int getSendDistance();
	
	public Entity(int entityID, World world, Position pos) {
		this.entityID = entityID;
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
