package org.marinemc.world.entity;

import java.util.HashMap;
import java.util.Map;

import org.marinemc.server.Marine;
import org.marinemc.world.World;

/**
 * Stores all entities
 * 
 * @author Fozie
 */
public class EntityHandler {
    private int nextEntityID;
    
    private final World world;
    
    private volatile Map<Integer, Entity> entities;
    
    public EntityHandler(World w) {
    	this.world = w;
    	nextEntityID = Integer.MIN_VALUE;
    	entities = new HashMap<>();
    }
    
    public int generateID() {
    	return ++nextEntityID;
    }
    
    public World getWorld() {
    	return world;
    }
    
    public void removeEntity(int ID) {
    	Marine.getServer().getPlayerManager().getEntitySpawner();
    }
}
