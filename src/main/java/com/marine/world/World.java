package com.marine.world;

import com.marine.util.Position;
import com.marine.world.chunk.Chunk;
import com.marine.world.chunk.ChunkPos;
import com.marine.world.generators.LevelType;
import com.marine.world.generators.WorldGenerator;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class World { // TODO Save and unload chunks...

    // Identifiers:
    private final String name;
    private final UUID uuid;
    private final Dimension dimension;
    
    //Data:
    private Map<Long, Chunk> loadedChunks;
    private Position spawnPoint;

    private int time;

    private WorldGenerator generator;

    public World(final String name) {
        this.generator = null; //this should use the default generator
        this.uuid = UUID.randomUUID();
        this.name = name;
        this.dimension = Dimension.OVERWORLD;
    }

    public World(final String name, WorldGenerator generator) { //TODO Make it able to load world

        this.generator = generator;
        this.generator.setWorld(this);

        uuid = UUID.randomUUID();
        this.name = name;

        loadedChunks = Collections.synchronizedMap(new ConcurrentHashMap<Long, Chunk>());

        spawnPoint = new Position(0, 3, 0); //TODO make this get loaded from world or generate random based on worldgenerator

        

        dimension = this.generator.getDimension();
    }

    public boolean isChunkLoaded(int x, int y) {
        return loadedChunks.containsKey(ChunkPos.Encode(x, y));
    }

    public void generateChunk(int x, int z) {
        loadedChunks.put(ChunkPos.Encode(x,z), generator.generateChunk(x, z));
    }

    public Chunk getChunk(int x, int z) {
    	if(!isChunkLoaded(x,z))
    		return generator.generateChunk(x, z); // TODO Load world
    	else
    		return loadedChunks.get(ChunkPos.Encode(x, z));
    }

    public List<Chunk> getChunks(int x, int z, int amtX, int amtY) {

        List<Chunk> chunks = new ArrayList<Chunk>();

        for (int xx = amtX / 2 * -1; xx < amtX; xx++)
            for (int yy = amtY / 2 * -1; yy < amtY; yy++)
                chunks.add(getChunk(x + xx, z + yy));

        return chunks;
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
        if (time < 24000)
            time++;
        else
            time = 0;
    }
    
    public int getTime() {
    	return time;
    }


    public LevelType getLevelType() {
        return generator.getLevelType();
    }

}
