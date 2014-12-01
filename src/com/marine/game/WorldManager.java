package com.marine.game;

import java.util.Collections;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import com.marine.world.World;
import com.marine.world.generators.TotalFlatGrassGenerator;

public class WorldManager {
	public Map<UUID, World> loadedWorlds;
	
	private UUID mainWorld;
	
	public WorldManager() {
		loadedWorlds = Collections.synchronizedMap(new ConcurrentHashMap<UUID, World>());
	}

	// TODO World loading..
	
	public World getMainWorld() {
		if(mainWorld == null) { // Temporary code when no world loader is implemented
			World w = new World(TotalFlatGrassGenerator.class);
			w.generateChunk(0, 0);
		}
		
		return loadedWorlds.get(mainWorld);
	}
	
	
	public void addWorld(World w) {
		if(loadedWorlds.containsKey(w.getUUID()))
			return;
		
		loadedWorlds.put(w.getUUID(), w);
	}
	
}
