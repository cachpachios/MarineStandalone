///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// MarineStandalone is a minecraft server software and API.
// Copyright (C) MarineMC (marinemc.org)
//
// This program is free software; you can redistribute it and/or modify
// it under the terms of the GNU General Public License as published by
// the Free Software Foundation; either version 2 of the License, or
// (at your option) any later version.
//
// This program is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
// GNU General Public License for more details.
//
// You should have received a copy of the GNU General Public License along
// with this program; if not, write to the Free Software Foundation, Inc.,
// 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

package org.marinemc.world;

import org.marinemc.game.WorldManager;
import org.marinemc.server.StandaloneServer;
import org.marinemc.util.Position;
import org.marinemc.world.chunk.Chunk;
import org.marinemc.world.chunk.ChunkPos;
import org.marinemc.world.gen.LevelType;
import org.marinemc.world.gen.WorldGenerator;
import org.marinemc.world.gen.generators.TotalFlatGrassGenerator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Fozie
 */
public class World { // TODO Save and unload chunks...	
	
    //Final Pointers:
    private final StandaloneServer server;
    
    //Identifiers:
    private final String name;
    private final byte uid;
    private final Dimension dimension;

    //Data:
    private Map<Long, Chunk> loadedChunks;
    private Position spawnPoint;

    private long age;
    private int time;

    private WorldGenerator generator;

    public World(StandaloneServer server, final String name) {
        this.server = server;
        loadedChunks = Collections.synchronizedMap(new ConcurrentHashMap<Long, Chunk>());
        this.generator = new TotalFlatGrassGenerator(); // StandardGenerator
        this.generator.setGenerationWorld(this);
        spawnPoint = generator.getSafeSpawnPoint().getRelativePosition(); //TODO make this get loaded from world or generate random based on worldgenerator
        this.uid = WorldManager.getNextUID();
        this.name = name;
        this.dimension = Dimension.OVERWORLD;
        this.time = 0;
        this.age = 0;
    }

    public World(StandaloneServer server, final String name, WorldGenerator generator) { //TODO Make it able to load world
        this.server = server;
        this.time = 0;
        this.age = 0;
        uid = WorldManager.getNextUID();
        this.name = name;
        loadedChunks = Collections.synchronizedMap(new ConcurrentHashMap<Long, Chunk>());
        
        this.generator = generator;
        this.generator.setGenerationWorld(this);

        spawnPoint = generator.getSafeSpawnPoint().getRelativePosition(); //TODO make this get loaded from world or generate random based on worldgenerator


        dimension = this.generator.getDimension();
    }

    public boolean isChunkLoaded(int x, int y) {
        return loadedChunks.containsKey(ChunkPos.Encode(x, y));
    }

    public boolean isChunkLoaded(ChunkPos p) {
        return loadedChunks.containsKey(p.encode());
    }

    public void generateChunk(int x, int z) {
        loadedChunks.put(ChunkPos.Encode(x, z), generator.generateChunk(new ChunkPos(x,z)));
    }

    public Chunk getChunk(int x, int z) { // Will generate/loadchunk if not loaded
        if (!isChunkLoaded(x, z))
            return generator.generateChunk(new ChunkPos(x,z)); // TODO Load world
        else
            return loadedChunks.get(ChunkPos.Encode(x, z));
    }

    public Chunk getChunk(ChunkPos p) { // Will generate/loadchunk if not loaded
        if (!isChunkLoaded(p))
            return generator.generateChunk(new ChunkPos(p.getX(), p.getY())); // TODO Load world
        else
            return loadedChunks.get(p.encode());
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

    public Position getSpawnPoint() {
    	if(spawnPoint!=null)
    		return spawnPoint;
    	else
    		return generator.getSafeSpawnPoint().getRelativePosition();
    }

    public String getName() {
        return name;
    }

    public void tick() {
        if (time < 24000)
            time++;
        else
            time = 0;
        age++;
    }

    public int getTime() {
        return time;
    }


    public LevelType getLevelType() {
        return generator.getLevelType();
    }

    public long getWorldAge() {
        return age;
    }

    public void setTypeAt(Position blockPos, BlockID target, boolean loadIfEmpty) {
        ChunkPos p = blockPos.getChunkPos();
        if (isChunkLoaded(p)) {
            Position pos = blockPos.getChunkBlockPos();
            loadedChunks.get(p.encode()).setBlock(pos.getX(), pos.getY(), pos.getZ(), target);
        } else if (loadIfEmpty) {
            Position pos = blockPos.getChunkBlockPos();
            getChunk(p).setBlock(pos.getX(), pos.getY(), pos.getZ(), target);
        }
    }

    public byte getUID() {
        return uid;
    }

    public WorldGenerator getGenerator() {
        return this.generator;
    }

    public StandaloneServer getServer() {
        return server;
    }
}
