package org.marinemc.world.entity;

import java.util.HashMap;
import java.util.Map;

import org.marinemc.world.World;

/**
 * Stores all entities
 * 
 * @author Fozie
 */
public class EntityHandler {
	private int nextEntityID;

	private final World world;

	private volatile Map<Integer, Entity> loadedEntities;

	public EntityHandler(final World w) {
		world = w;
		nextEntityID = Integer.MIN_VALUE;
		loadedEntities = new HashMap<>();
	}

	public int generateID() {
		return ++nextEntityID;
	}

	public World getWorld() {
		return world;
	}

	public void removeEntity(final int id) {
	}
}
