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
import org.marinemc.io.binary.ByteList;
import org.marinemc.server.Marine;
import org.marinemc.util.Position;
import org.marinemc.util.annotations.Cautious;
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
	
	public static final int WIDTH = 16, HEIGHT = 16;

    private final World w;
    private final ChunkPos pos;

    private ChunkSection[] sections;
    private BiomeID[] biomes;

    private short[] heightMap; // Sorted x + y * width
    private Random random;
    
//    private List<Entity> entities;

    private List<Short> subscribingPlayers;

    public Chunk(World w, ChunkPos pos) {
        this.w = w;
        this.pos = pos;
        this.sections = new ChunkSection[16];
        
        this.biomes = new BiomeID	[WIDTH*HEIGHT];
        this.heightMap = new short	[WIDTH*HEIGHT];

        this.subscribingPlayers = new ArrayList<>();
        this.random = new Random();
    }

    public Random getRandom() {
        return this.random;
    }

    public void unsubscribePlayer(final Player p) { // Make player unsubscribe to events within the chunks(BlockUpdates, entities etc)
        subscribingPlayers.remove(p.getUID());
    }

    public void subscribePlayer(final Player p) { // Make player unsubscribe to events within the chunks(BlockUpdates, entities etc)
        if (!subscribingPlayers.contains(p.getUID()))
            subscribingPlayers.add(p.getUID());
    }

    public void unload() {
    	for(Short s : subscribingPlayers)
    		if(Marine.getServer().getPlayerManager().getPlayer(s) != null)
    			Marine.getServer().getPlayerManager().getPlayer(s).unloadChunk(pos);
    	subscribingPlayers.clear();
    	//TODO Save chunk perhaps :S
    }
    
    public void unload(final Player p) {
    	subscribingPlayers.remove(p.getUID());
		p.unloadChunk(pos);
    }
    
    public void setBlock(Position pos, BlockID type) {
        setBlock(pos.x, pos.y, pos.z, type);
    }

    public void updateBlockChange(Position pos, BlockID type) {
        for (Short s : subscribingPlayers)
            Marine.getPlayer(s).sendBlockUpdate(pos, type);
    }

    public void updateBlockChange(int x, int y, int z, BlockID type) {
        updateBlockChange(new Position(x, y, z), type);
    }

    //TODO: TileEntities, Entities

    protected void setType(int x, int y, int z, BlockID type) {
        int section = y >> 4;

        if (sections[section] == null)
            if (type != BlockID.AIR) {
                sections[section] = new ChunkSection(this, section);
                
                if (section > 0)
                    sections[section].setType(x, y / (section), z, type);
                if (section == 0)
                    sections[section].setType(x, y, z, type);
                setMaxHeight(x,z,(short) y);
                
                return;
            }
            else return;

        if (section > 0)
            sections[section].setType(x, y / (section), z, type);
        if (section == 0)
            sections[section].setType(x, y, z, type);
        
        if(y > getMaxHeightAt(x,z)) {
            setMaxHeight(x,z,(short) y);
        }
    }    
    
    public BlockID getBlockTypeAt(int x, int y, int z) {
        int section = y >> 4;

        if (sections[section] == null)
            return BlockID.AIR;

        if (section > 0)
            return sections[section].getBlock(x, y/section, z);
        else
            return sections[section].getBlock(x, y, z);
    }
    
    @Cautious
    public boolean isTop(int x, int y, int z) {
        for(int i = y; i < (256 - y); i++){
        	if((int)getBlock(x,i,z) != 0)
        		return false;
        }
        return true;
    }

    protected void setLight(int x, int y, int z, Byte light) {
        setLight(x, y, z, light.byteValue());
    }

    protected void setLight(int x, int y, int z, byte light) {
        if (y > 255)
            return;

        int s = y >> 4;

        if (sections[s] == null)
            return;
        else
            sections[s].setLight(x, y / 16, z, light);
    }

    public ByteList getData(boolean biomes, boolean skyLight) {
        ByteList d = new ByteList();

        for (ChunkSection s : sections) {
            if (s != null)
                d.write(s.getBlockData());
        }
        for (ChunkSection s : sections) {
            if (s != null)
            	d.writeData(s.getLightData());
        }

        if (biomes)
            d.writeData(getBiomeData());

        return d;
    }
    
    public byte[] getBytes(boolean biomes, boolean skyLight) { return getData(biomes, skyLight).toBytes(); }
    
    public ByteList getBiomeData() {
        ByteList d = new ByteList();
        for(BiomeID b : biomes)
        	if(b != null)
        		d.writeByte(b.getID());
            else
            	d.writeByte(BiomeID.PLAINS.getID());
        return d;
    }

    public ChunkPos getPos() {
        return pos;
    }

    public short getSectionBitMap() {
        short r = 0;
        for (ChunkSection s : sections)
            if (s != null)
                r |= 1 << s.getID();
        return r;
    }

    public World getWorld() {
        return w;
    }

    public void setBlock(Integer x, Integer y, Integer z, BlockID type) {
        setType(x, y, z, type);
        updateBlockChange(new Position(x * pos.getX(), y, z * pos.getY()), type);
    }

    @Serverside
    public void setPrivateType(int x, int y, int z, BlockID type) {
        setType(x, y, z, type);
    }

    @Serverside
    public void setPrivateLight(int x, int y, int z, byte light) {
        setLight(x, y, z, light);
    }


    public char getBlock(int x, int y, int z) {
        int s = y >> 4;

        if (sections[s] == null)
            return (char) 0;

        return sections[s].getType(x / 16, y / 16, z / 16);
    }
    
    @Unsafe
    public final ChunkSection getSection(int y) {
        return sections[y >> 4];
    }

    public void setPrivateCube(int x, int y, int z, int w, int d, int h, BlockID type) {
        if (h == 0) return;
        if (w == 0) return;
        if (d == 0) return;
        
        final int section = y >> 4;
        
        if (sections[section] == null)
        	if (type != BlockID.AIR)
        		sections[section] = new ChunkSection(this, section);
        getSection(y).setPrivateCube(x, y, z, w, d, h, type);
    }
    
    private void setMaxHeight(int x, int z, short y) {
    	if(x > 15) return;
    	if(z > 15) return;
    	heightMap[index(x,z)] = y;
    }
    
    public short getMaxHeightAt(int x, int z) {
    	if(x > 15) return -1;
    	if(z > 15) return -1;
    	return heightMap[index(x,z)];
    }
   
    
    private int index(int x, int y) {
    	return (x + (y * WIDTH));
    }

    public int getDataSize(boolean biomes, boolean skylight) {
        int size = 0;

        for (ChunkSection s : sections)
            if (s != null) {
                size += ChunkSection.DATA_SIZE * 3;
                if (skylight)
                    size += ChunkSection.DATA_SIZE;
            }

        if (biomes) size += 256;

        return size;
    }

	public Player[] getSubscribingPlayers() {
		final Player[] pl = new Player[subscribingPlayers.size()];
		
		int i = -1;
		for(final short s : subscribingPlayers)
            pl[i] = Marine.getPlayer(s);

        return pl;
    }
}
