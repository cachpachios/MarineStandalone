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

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

import org.marinemc.game.WorldManager;
import org.marinemc.game.player.Player;
import org.marinemc.util.Position;
import org.marinemc.world.chunk.Chunk;
import org.marinemc.world.chunk.ChunkPos;
import org.marinemc.world.entity.EntityHandler;
import org.marinemc.world.gen.LevelType;
import org.marinemc.world.gen.WorldGenerator;

/**
 * @author Fozie
 */
public class World { // TODO Save and unload chunks...	
	
    //Async stuff:
    private WorldThread thread;
    private List<Long> chunksToGenerate;
    
    //Identifiers:
    private final String name;
    private final byte uid;
    private final Dimension dimension;

    //Data:
    private Map<Long, Chunk> loadedChunks;
    private Position spawnPoint;
    
    private EntityHandler entities;

    private long age;
    private long time;

    private WorldGenerator generator;

    public World(final String name, WorldGenerator generator) {
    	this.loadedChunks = Collections.synchronizedMap(new HashMap<Long, Chunk>());
        this.generator = generator; // StandardGenerator
        this.generator.setGenerationWorld(this);
        this.spawnPoint = generator.getSafeSpawnPoint().getRelativePosition(); //TODO make this get loaded from world or generate random based on worldgenerator
        this.uid = WorldManager.getNextUID();
        this.name = name;
        this.dimension = Dimension.OVERWORLD;
        this.time = 0;
        this.age = 0;
        this.entities = new EntityHandler(this);
        
        chunksToGenerate = new CopyOnWriteArrayList<>();
        
        thread = new WorldThread(this);
        thread.start();
    }

    public boolean isChunkLoaded(int x, int y) {
        return loadedChunks.containsKey(ChunkPos.Encode(x, y));
    }

    public boolean isChunkLoaded(ChunkPos p) {
        return loadedChunks.containsKey(p.encode());
    }

    public void generateAsyncChunk(int x, int y) {
    	ChunkPos pos = new ChunkPos(x,y);

    	if(!loadedChunks.containsKey(pos.encode()))
    		chunksToGenerate.add(pos.encode());
    }
    
    public void generateAsyncChunk(ChunkPos pos) {
    	if(!loadedChunks.containsKey(pos.encode()))
    		chunksToGenerate.add(pos.encode());
    }
    
    public void generateAsyncRegion(int x, int y, int amtX, int amtY)  {
        for (int xx = amtX / 2 * -1; xx < amtX; xx++)
            for (int yy = amtY / 2 * -1; yy < amtY; yy++)
            	generateAsyncChunk(xx,yy);
    }
    
    private void forceGenerateChunk(int x, int z) {
        loadedChunks.put(ChunkPos.Encode(x, z), generator.generateChunk(new ChunkPos(x,z)));
    }

    private void forceGenerateChunk(ChunkPos p) {
        loadedChunks.put(p.encode(), generator.generateChunk(p));
    }
    
    public Chunk getChunkForce(int x, int z) { // Will generate/loadchunk if not loaded
        if (!isChunkLoaded(x, z))
        	forceGenerateChunk(x,z); // TODO Load world

            return loadedChunks.get(ChunkPos.Encode(x, z));
    }

    public Chunk getChunkForce(ChunkPos p) { // Will generate/loadchunk if not loaded
        if (!isChunkLoaded(p))
        	forceGenerateChunk(p); // TODO Load world

            return loadedChunks.get(p.encode());
    }

    public List<Chunk> getChunksForce(int x, int z, int amtX, int amtY) {

        List<Chunk> chunks = new ArrayList<Chunk>();

        for (int xx = amtX / 2 * -1; xx < amtX; xx++)
            for (int yy = amtY / 2 * -1; yy < amtY; yy++)
                chunks.add(getChunkForce(x + xx, z + yy));

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
    
    void generateRequested() {
    	for(Long pos : chunksToGenerate) {
    		if(loadedChunks.containsKey(pos)) {
    			chunksToGenerate.remove(pos);
    			continue;
    		}
    		
    		forceGenerateChunk(new ChunkPos(pos));
    		
    	}
    }

    void tick() {
        if (time < 24000)
            ++time;
        else
            time = 0;
        age++;
    }

    public long getTime() {
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
            getChunkForce(p).setBlock(pos.getX(), pos.getY(), pos.getZ(), target);
        }
    }

    public byte getUID() {
        return uid;
    }

    public WorldGenerator getGenerator() {
        return this.generator;
    }

	public EntityHandler getEntityHandler() {
        return entities;
    }

	public boolean hasChunksToGenerate() {
		return !chunksToGenerate.isEmpty();
	}

	public void putForDownload(Player p) {
		
	}
}
