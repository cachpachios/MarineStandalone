package com.marine.game;

import com.marine.world.World;
import com.marine.world.generators.TotalFlatGrassGenerator;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class WorldManager {
    public Map<Byte, World> loadedWorlds;

    private byte mainWorld;

    public WorldManager() {
        loadedWorlds = Collections.synchronizedMap(new ConcurrentHashMap<Byte, World>());
        mainWorld = -1;
    }

    public List<World> getWorlds() {
        return new ArrayList<>(loadedWorlds.values());
    }

    // TODO World loading..

    public World getMainWorld() {
        if (mainWorld == -1) { // Temporary code when no world loader is implemented
            World w = new World("world", new TotalFlatGrassGenerator());
            w.generateChunk(0, 0);
            addWorld(w);
            mainWorld = w.getUID();
            return w;
        } else
            return loadedWorlds.get(mainWorld);
    }


    public void addWorld(World w) {
        if (loadedWorlds.containsKey(w.getUID()))
            return;

        loadedWorlds.put(w.getUID(), w);
    }

    public void tick() {
    	for (World w : loadedWorlds.values())
    		w.tick();
    }

    private static byte nextUID = -1;
    
	public static byte getNextUID() {
		return ++nextUID;
	}
}
