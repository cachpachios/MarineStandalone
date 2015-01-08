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

package org.marinemc.world.chunk;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.marinemc.game.player.Player;
import org.marinemc.io.binary.ByteArray;
import org.marinemc.io.binary.ByteUtils;
import org.marinemc.server.Marine;
import org.marinemc.util.Position;
import org.marinemc.util.annotations.Cautious;
import org.marinemc.util.annotations.Hacky;
import org.marinemc.util.annotations.Serverside;
import org.marinemc.util.annotations.Unsafe;
import org.marinemc.world.BiomeID;
import org.marinemc.world.BlockID;
import org.marinemc.world.World;

/**
 * Storage unit for ingame chunks
 *
 * @author Fozie
 */
public class Chunk {

	public static final int WIDTH = 16, DEPTH = 16;

	private final World w;
	private final ChunkPos pos;

	private final ChunkSection[] sections;
	private final BiomeID[] biomes;

	private final short[] heightMap; // Sorted x + y * width
	private final Random random;

	// private List<Entity> entities;

	private final List<Short> subscribingPlayers;

	public Chunk(final World w, final ChunkPos pos) {
		this.w = w;
		this.pos = pos;
		sections = new ChunkSection[16];

		biomes = new BiomeID[WIDTH * DEPTH];
		heightMap = new short[WIDTH * DEPTH];

		subscribingPlayers = new ArrayList<>();
		random = w.getRandom();
	}

	public Random getRandom() {
		return random;
	}

	/**
	 * Checks if any players have loaded this chunk
	 * 
	 * @return However any players have this chunk loaded
	 */
	public boolean isActive() {
		return subscribingPlayers.size() != 0;
	}

	public void unsubscribePlayer(final Player p) { // Make player unsubscribe
													// to events within the
													// chunks(BlockUpdates,
													// entities etc)
		subscribingPlayers.remove(p.getUID());
	}

	public void subscribePlayer(final Player p) { // Make player unsubscribe to
													// events within the
													// chunks(BlockUpdates,
													// entities etc)
		if (!subscribingPlayers.contains(p.getUID()))
			subscribingPlayers.add(p.getUID());
	}

	public void unload() {
		for (final Short s : subscribingPlayers)
			if (Marine.getServer().getPlayerManager().getPlayer(s) != null)
				Marine.getServer().getPlayerManager().getPlayer(s)
						.unloadChunk(pos);
		subscribingPlayers.clear();
		// TODO Save chunk perhaps :S
	}

	public void unload(final Player p) {
		subscribingPlayers.remove(new Short(p.getUID()));
		p.unloadChunk(pos);
	}

	public void setBlock(final Position pos, final BlockID type) {
		setBlock(pos.x, pos.y, pos.z, type);
	}

	public void updateBlockChange(final Position pos, final BlockID type) {
		for (final Short s : subscribingPlayers)
			Marine.getPlayer(s).sendBlockUpdate(pos, type);
	}

	public void updateBlockChange(final int x, final int y, final int z,
			final BlockID type) {
		updateBlockChange(new Position(x, y, z), type);
	}

	// TODO: TileEntities, Entities

	protected void setType(final int x, final int y, final int z,
			final BlockID type) {
		final int section = y >> 4;

		if (sections[section] == null)
			if (type != BlockID.AIR) {
				sections[section] = new ChunkSection(this, section);

				if (section > 0)
					sections[section].setType(x, y / section, z, type);
				if (section == 0)
					sections[section].setType(x, y, z, type);
				setMaxHeight(x, z, (short) y);

				return;
			} else
				return;

		if (section > 0)
			sections[section].setType(x, y / section, z, type);
		if (section == 0)
			sections[section].setType(x, y, z, type);

		if (y > getMaxHeightAt(x, z))
			setMaxHeight(x, z, (short) y);
	}

	public BlockID getBlockTypeAt(final int x, final int y, final int z) {
		final int section = y >> 4;

		if (sections[section] == null)
			return BlockID.AIR;

		if (section > 0)
			return sections[section].getBlock(x, y / section, z);
		else
			return sections[section].getBlock(x, y, z);
	}

	@Cautious
	public boolean isTop(final int x, final int y, final int z) {
		for (int i = y; i < 256 - y; i++)
			if (getBlock(x, i, z) != 0)
				return false;
		return true;
	}

	protected void setLight(final int x, final int y, final int z,
			final Byte light) {
		setLight(x, y, z, light.byteValue());
	}

	protected void setLight(final int x, final int y, final int z,
			final byte light) {
		if (y > 255)
			return;

		final int s = y >> 4;

		if (sections[s] == null)
			return;
		else
			sections[s].setLight(x, y / 16, z, light);
	}

	@Hacky
	public int amountSections() {
		int x = 0;
		for (final ChunkSection s : sections)
			if (s != null)
				++x;
		return x;
	}

	public byte[] getBytes(final boolean biomes, final boolean skyLight) {
		byte[] d = new byte[0];

		for (final ChunkSection s : sections)
			if (s != null)
				d = ByteUtils.combine(d, s.getBlockData());

		for (final ChunkSection s : sections)
			if (s != null)
				d = ByteUtils.combine(d, s.getLightData());

		if (biomes)
			d = ByteUtils.combine(d, getBiomeData());

		return d;
	}

	public ByteArray getData(final boolean biomes, final boolean skyLight) {
		return new ByteArray(getBytes(biomes, skyLight));
	}

	public byte[] getBiomeData() {
		final byte[] d = new byte[16 * 16];
		int i = -1;
		for (final BiomeID b : biomes)
			if (b != null)
				d[++i] = b.getID();
			else
				d[++i] = BiomeID.PLAINS.getID();
		return d;
	}

	public ChunkPos getPos() {
		return pos;
	}

	public short getSectionBitMap() {
		short r = 0;
		for (final ChunkSection s : sections)
			if (s != null)
				r |= 1 << s.getID();
		return r;
	}

	public World getWorld() {
		return w;
	}

	public void setBlock(final Integer x, final Integer y, final Integer z,
			final BlockID type) {
		setType(x, y, z, type);
		updateBlockChange(new Position(x * pos.getX(), y, z * pos.getY()), type);
	}

	@Serverside
	public void setPrivateType(final int x, final int y, final int z,
			final BlockID type) {
		if (x > 15)
			return;
		if (z > 15)
			return;

		setType(x, y, z, type);
	}

	@Serverside
	public void setPrivateLight(final int x, final int y, final int z,
			final byte light) {
		setLight(x, y, z, light);
	}

	public short getBlock(final int x, final int y, final int z) {
		final int s = y >> 4;

		if (sections[s] == null)
			return (char) 0;

		return sections[s].getType(x / 16, y / 16, z / 16);
	}

	@Unsafe
	public final ChunkSection getSection(final int y) {
		return sections[y >> 4];
	}

	public void setPrivateCube(final int x, final int y, final int z,
			final int w, final int d, final int h, final BlockID type) {
		if (h == 0)
			return;
		if (w == 0)
			return;
		if (d == 0)
			return;

		final int section = y >> 4;

		if (sections[section] == null)
			if (type != BlockID.AIR)
				sections[section] = new ChunkSection(this, section);
		getSection(y).setPrivateCube(x, y, z, w, d, h, type);
	}

	private void setMaxHeight(final int x, final int z, final short y) {
		if (x > 15)
			return;
		if (z > 15)
			return;
		heightMap[index(x, z)] = y;
	}

	public short getMaxHeightAt(final int x, final int z) {
		if (x > 15)
			return -1;
		if (z > 15)
			return -1;
		return heightMap[index(x, z)];
	}

	private int index(final int x, final int y) {
		return x + y * WIDTH;
	}

	public int getDataSize(final boolean biomes, final boolean skylight) {
		int size = 0;

		for (final ChunkSection s : sections)
			if (s != null) {
				size += ChunkSection.DATA_SIZE * 3;
				if (skylight)
					size += ChunkSection.DATA_SIZE;
			}

		if (biomes)
			size += 256;

		return size;
	}

	public Player[] getSubscribingPlayers() {
		final Player[] pl = new Player[subscribingPlayers.size()];

		final int i = -1;
		for (final short s : subscribingPlayers)
			pl[i] = Marine.getPlayer(s);

		return pl;
	}
}
