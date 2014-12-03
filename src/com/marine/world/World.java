package com.marine.world;

import java.util.Collections;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import com.marine.util.Position;
import com.marine.world.generators.LevelType;
import com.marine.world.generators.WorldGenerator;

public class World { // TODO Save and unload chunks...
	
	private Map<ChunkPos, Chunk> loadedChunks;
	
	// Identifiers:
	private final String name;
	private final UUID uuid;
	
	private final Dimension dimension;
	
	
	private Position spawnPoint;
	
	private int time;
	
	private WorldGenerator generator;
	
	public World(final String name, WorldGenerator generator) { //TODO Make it able to load world
		
		this.generator = generator;
		this.generator.setWorld(this);
		
		uuid = UUID.randomUUID();
		this.name = name;
		
		loadedChunks = Collections.synchronizedMap(new ConcurrentHashMap<ChunkPos, Chunk>());
		
		spawnPoint = new Position(0,0,0); //TODO make this get loaded from world or generate random based on worldgenerator
		
		
		
		dimension = this.generator.getDimension();
	}
	
	
	public boolean isChunkLoaded(int x, int y) {
		return loadedChunks.containsKey(new ChunkPos(x,y));
	}
	
	public void generateChunk(int x, int z) {
		loadedChunks.put(new ChunkPos(x,z), generator.generateChunk(x, z));
	}
	
	public Chunk getChunk(int x, int z)  {
		return generator.generateChunk(x, z);
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
	
	public String getName() {
		return name;
	}

	public void tick() {
		if(time < 24000)
			time++;
		else
			time = 0;
	}

	
	public LevelType getLevelType() {
		return generator.getLevelType();
	}
	
}
