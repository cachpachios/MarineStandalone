package com.marine.game;

import com.marine.world.World;
import com.marine.world.generators.TotalFlatGrassGenerator;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class WorldManager {
	public Map<UUID, World> loadedWorlds;
	
	private UUID mainWorld;
	
	public WorldManager() {
		loadedWorlds = Collections.synchronizedMap(new ConcurrentHashMap<UUID, World>());
	}

    public List<World> getWorlds() {
        return new ArrayList<>(loadedWorlds.values());
    }

	// TODO World loading..
	
	public World getMainWorld() {
		if(mainWorld == null) { // Temporary code when no world loader is implemented
			World w = new World("world", new TotalFlatGrassGenerator());
			w.generateChunk(0, 0);
			addWorld(w);
			mainWorld = w.getUUID();
			return w;
		}
		else
		return loadedWorlds.get(mainWorld);
	}
	
	
	public void addWorld(World w) {
		if(loadedWorlds.containsKey(w.getUUID()))
			return;
		
		loadedWorlds.put(w.getUUID(), w);
	}

	public void tick() {
		synchronized(loadedWorlds) {
			for(World w : loadedWorlds.values())
				w.tick();
		}
	}
	
}
