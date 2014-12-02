package com.marine.world;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;
import java.util.UUID;

import com.marine.Logging;
import com.marine.util.Position;
import com.marine.world.generators.TotalFlatGrassGenerator;
import com.marine.world.generators.WorldGenerator;

public class World { // TODO Save and unload chunks...
	
	private Map<ChunkPos, Chunk> loadedChunks;

	private Position spawnPoint;
	
	private final UUID uuid;
	
	private final Dimension dimension;
	
	private int time;
	
	private WorldGenerator generator;
	
	public <T extends WorldGenerator, W extends World> World(Class<T> generator) { //TODO Make it able to load world
		uuid = UUID.randomUUID();
		
		spawnPoint = new Position(0,0,0); //TODO make this get loaded from world or generate random based on worldgenerator
		
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
		
		dimension = this.generator.getDimension();
	}
	
	public Chunk loadChunk(int x, int y) {
		return null; // Temp code..
	}
	
	public boolean chunkExist(int x, int y) { // Check if chunk exists in region file (if that exists xD)
		return false;
	}
	
	public boolean isChunkLoaded(int x, int y) {
		return loadedChunks.containsKey(new ChunkPos(x,y));
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

	public Dimension getDimension() {
		return dimension;
	}

	public UUID getUUID() {
		return uuid;
	}

	public Position getSpawnPoint() {
		return spawnPoint;
	}

	public void tick() {
		if(time < 24000)
			time++;
		else
			time = 0;
	}

	
}
