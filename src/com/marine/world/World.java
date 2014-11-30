package com.marineapi.world;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

import com.marineapi.Logging;
import com.marineapi.world.generators.TotalFlatGrassGenerator;
import com.marineapi.world.generators.WorldGenerator;

public class World { // TODO Save and unload chunks...
	
	private Map<ChunkPos, Chunk> loadedChunks;

	
	private WorldGenerator generator;
	
	public <T extends WorldGenerator, W extends World> World(Class<T> generator) { //TODO Make it able to load world
		Constructor<T> c = null;
		try {
			c = generator.getConstructor(World.class);
		} catch (NoSuchMethodException e) {
			Logging.getLogger().fatal("World construction failed, generator type had an unknowned constructor!");
		} catch (SecurityException e) {}
		
		if(c == null)
			this.generator = new TotalFlatGrassGenerator(this);
		else {
			try {
				this.generator = c.newInstance(this);
			} catch (InstantiationException | IllegalAccessException
					| IllegalArgumentException | InvocationTargetException e) {
				Logging.getLogger().fatal("World construction failed, generator type had an unknowned constructor!");
			}
		}
	}
	
	public void generateChunk(int x, int z) {
		if(loadedChunks.containsKey(new ChunkPos(x,z)))
			return;
		
		loadedChunks.put(new ChunkPos(x,z), generator.generateChunk(x, z));
	}
	
	public Chunk getChunk(int x, int z)  {
		if(!loadedChunks.containsKey(new ChunkPos(x,z)))
			generateChunk(x,z);
			
		return loadedChunks.get(new ChunkPos(x,z));
	}
	
}
