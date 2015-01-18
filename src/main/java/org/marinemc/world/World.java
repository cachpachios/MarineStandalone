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
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

import org.marinemc.game.player.Player;
import org.marinemc.server.Marine;
import org.marinemc.util.MathUtils;
import org.marinemc.util.Position;
import org.marinemc.util.annotations.Threaded;
import org.marinemc.world.chunk.Chunk;
import org.marinemc.world.chunk.ChunkPos;
import org.marinemc.world.entity.EntityHandler;
import org.marinemc.world.gen.LevelType;
import org.marinemc.world.gen.WorldGenerator;

/**
 * @author Fozie
 */


//TODO Synchronizing loadedChunks
public class World { // TODO Save and unload chunks...

	// Async stuff:
	private final WorldThread thread;
	private final List<Long> chunksToGenerate;

	// Identifiers:
	private final String name;
	private final byte uid;
	private final Dimension dimension;

	// Data:
	private volatile Map<Long, Chunk> loadedChunks;
	private final Position spawnPoint;

	private final EntityHandler entities;

	private long age;
	private long time;

	private final WorldGenerator generator;
	private final Random randomizer;

	/**
	 * Constructor with a random world seed
	 */
	public World(final String name, final WorldGenerator generator) {
		this(name, (long) (Math.random() * Long.MAX_VALUE), generator);
	}

	public World(final String name, final long seed,
			final WorldGenerator generator) {
		loadedChunks = new ConcurrentHashMap<Long, Chunk>();
		randomizer = new Random();
		this.generator = generator; // StandardGenerator
		this.generator.setGenerationWorld(this);
		spawnPoint = generator.getSafeSpawnPoint(); // TODO make this get loaded
													// from world or generate
													// random based on
													// worldgenerator
		uid = Marine.getServer().getWorldManager().getNextUID();
		this.name = name;
		dimension = Dimension.OVERWORLD;
		time = 0;
		age = 0;
		entities = new EntityHandler(this);

		chunksToGenerate = new CopyOnWriteArrayList<>();

		thread = new WorldThread(this);
		thread.start();
	}

	public boolean isChunkLoaded(final int x, final int y) {
		return loadedChunks.containsKey(ChunkPos.Encode(x, y));
	}

	public boolean isChunkLoaded(final ChunkPos p) {
		return loadedChunks.containsKey(p.encode());
	}

	public void generateAsyncChunk(final int x, final int y) {
		final ChunkPos pos = new ChunkPos(x, y);

		if (!loadedChunks.containsKey(pos.encode()))
			chunksToGenerate.add(pos.encode());
	}

	public void generateAsyncChunk(final ChunkPos pos) {
		if (!loadedChunks.containsKey(pos.encode()))
			chunksToGenerate.add(pos.encode());
	}

	public void generateAsyncRegion(final int x, final int y, final int amtX,
			final int amtY) {
		for (int xx = amtX / 2 * -1; xx < amtX; xx++)
			for (int yy = amtY / 2 * -1; yy < amtY; yy++)
				generateAsyncChunk(x + xx, y + yy);
	}

	public boolean isEntireRegionInMemory(final int x, final int y,
			final int amtX, final int amtY) {
		boolean r = true;
		for (int xx = amtX / 2 * -1; xx < amtX; xx++)
			for (int yy = amtY / 2 * -1; yy < amtY; yy++)
				if (!loadedChunks.containsKey(ChunkPos.Encode(x + xx, y + yy)))
					r = false;
		return r;
	}

	private void forceGenerateChunk(final int x, final int z) {
		synchronized(loadedChunks) {
		loadedChunks.put(ChunkPos.Encode(x, z),
				generator.generateChunk(new ChunkPos(x, z)));
		}
	}

	private void forceGenerateChunk(final ChunkPos p) {
		synchronized(loadedChunks) {
			loadedChunks.put(p.encode(), generator.generateChunk(p));
		}
	}

	public Chunk getChunkForce(final int x, final int z) { // Will
															// generate/loadchunk
															// if not loaded
		if (!isChunkLoaded(x, z))
			forceGenerateChunk(x, z); // TODO Load world

		return loadedChunks.get(ChunkPos.Encode(x, z));
	}

	public Chunk getChunkForce(final ChunkPos p) { // Will generate/loadchunk if
													// not loaded
		if (!isChunkLoaded(p))
			forceGenerateChunk(p); // TODO Load world

		return loadedChunks.get(p.encode());
	}

	public List<Chunk> getChunksForce(final int x, final int z, final int amtX,
			final int amtY) {

		final List<Chunk> chunks = new ArrayList<Chunk>();

		for (int xx = amtX / 2 * -1; xx < amtX; xx++)
			for (int yy = amtY / 2 * -1; yy < amtY; yy++)
				chunks.add(getChunkForce(x + xx, z + yy));

		return chunks;
	}

	public Dimension getDimension() {
		return dimension;
	}

	public Position getSpawnPoint() {
		if (spawnPoint != null)
			return spawnPoint;
		else
			return generator.getSafeSpawnPoint();
	}

	public String getName() {
		return name;
	}

	void generateRequested() {
		for (final Long pos : chunksToGenerate) {
			if (loadedChunks.containsKey(pos)) {
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

	public void setTypeAt(final Position blockPos, final BlockID target,
			final boolean loadIfEmpty) {
		final ChunkPos p = blockPos.getChunkPos();
		if (isChunkLoaded(p)) {
			final Position pos = blockPos.getChunkBlockPos();
			loadedChunks.get(p.encode()).setBlock(pos.getX(), pos.getY(),
					pos.getZ(), target);
		} else if (loadIfEmpty) {
			final Position pos = blockPos.getChunkBlockPos();
			getChunkForce(p).setBlock(pos.getX(), pos.getY(), pos.getZ(),
					target);
		}
	}

	public byte getUID() {
		return uid;
	}

	public WorldGenerator getGenerator() {
		return generator;
	}

	public EntityHandler getEntityHandler() {
		return entities;
	}

	public boolean hasChunksToGenerate() {
		return !chunksToGenerate.isEmpty();
	}

	void finalizeChunks() {
		synchronized(loadedChunks) {
		for (final Chunk c : loadedChunks.values())
			if (!c.isActive()
					&& c.getPos().getDistanceFromXY(spawnPoint.x/16, spawnPoint.y/16) > Marine.getServer().getViewDistance()*2) {
				c.unload();
				loadedChunks.remove(c);

			}
		}
	}

	/**
	 * 
	 * @return
	 */
	@Threaded
	public List<Chunk> getSpawnChunks(final Player p) {

		final int s = Marine.getServer().getViewDistance() / 2;

		final int x = (int) (p.getX() / 16), y = (int) (p.getZ() / 16);

		final List<Chunk> spawnChunks = new ArrayList<>(s * s);

		if (!isEntireRegionInMemory(x, y, s, s)) {
			generateAsyncRegion(x, y, s, s);
			// Wait for the chunks to be generated:
			while (hasChunksToGenerate())
				try {
					Thread.sleep(WorldThread.skipTime * 2);
				} catch (final InterruptedException e) {
				}
		}

		for (int xx = s / 2 * -1; xx < s; xx++)
			for (int yy = s / 2 * -1; yy < s; yy++)
				spawnChunks.add(getChunkForce(x + xx, y + yy));

		return spawnChunks;
	}

	public Random getRandom() {
		return randomizer;
	}

	public List<Chunk> getChunksForce(final List<ChunkPos> positions) {
		final List<Chunk> chunks = new ArrayList<Chunk>(positions.size()+1);
		
		for(final ChunkPos p : positions)
			chunks.add(getChunkForce(p));
		
		return chunks;
	}
}
