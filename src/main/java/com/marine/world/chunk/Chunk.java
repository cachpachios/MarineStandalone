///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// MarineStandalone is a minecraft server software and API.
// Copyright (C) IntellectualSites (marine.intellectualsites.com)
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

package com.marine.world.chunk;

import java.util.ArrayList;
import java.util.List;

import com.marine.io.data.ByteData;
import com.marine.player.Player;
import com.marine.util.Position;
import com.marine.util.Unsafe;
import com.marine.world.BiomeID;
import com.marine.world.BlockID;
import com.marine.world.World;

/**
* Storage unit for ingame chunks
*
* @author Fozie
*/ 
public class Chunk {
    private final World w;
    private final ChunkPos pos;

    private ChunkSection[] sections;
    private BiomeID[][] biomes;

//    private List<Entity> entities;
    
    private List<Short> subscribingPlayers;

    public Chunk(World w, ChunkPos pos) {
        this.w = w;
        this.pos = pos;
        this.sections = new ChunkSection[16];
        this.biomes = new BiomeID[16][16];
        this.subscribingPlayers = new ArrayList<Short>();
    }
    
    public void unsubscribePlayer(final Player p) { // Make player unsubscribe to events within the chunks(BlockUpdates, entities etc)
    	subscribingPlayers.remove(p.getUID());
    }
    
    public void subscribePlayer(final Player p) { // Make player unsubscribe to events within the chunks(BlockUpdates, entities etc)
    	if(!subscribingPlayers.contains(p.getUID()))
    	subscribingPlayers.add(p.getUID());
    }

    public void setBlock(Position pos, BlockID type) {
    	setBlock(pos.x, pos.y, pos.z, type);
    }
    
    public void updateBlockChange(Position pos, BlockID type) {
    	for(Short s : subscribingPlayers)
    		w.getServer().getPlayerManager().getPlayer(s).sendBlockUpdate(pos, type);
    }
    
    public void updateBlockChange(int x, int y, int z, BlockID type) {
    		updateBlockChange(new Position(x,y,z), type);
    }
    
    //TODO: TileEntities, Entities
    
    protected void setType(int x, int y, int z, BlockID type) {
        int section = y >> 4;
        
        if (sections[section] == null)
            if (type != BlockID.AIR)
                sections[section] = new ChunkSection(this,section);
            else return;
        
        if (section > 0)
            sections[section].setType(x, y / (section), z, type);
        if (section == 0)
            sections[section].setType(x, y, z, type);
    	
    }
    protected void setLight(int x, int y, int z, Byte light) {setLight(x,y,z,light.byteValue());}
    
    protected void setLight(int x, int y, int z, byte light) {
    	if(y > 255)
    		return;
    	
    	int s = y >> 4;
    	
    	if(sections[s] == null)
    		return;
    	else
    		sections[s].setLight(x, y/16, z, light);
    }

    public ByteData getData(boolean biomes, boolean skyLight) {
        ByteData d = new ByteData();

        for (ChunkSection s : sections) {
            if (s != null)
                d.writeData(s.getBlockData());
        }
        for (ChunkSection s : sections) {
            if (s != null)
                d.writeData(s.getLightData());
        }

        if (biomes)
            d.writeData(getBiomeData());

        return d;

    }

    public ByteData getBiomeData() {
        ByteData d = new ByteData();
        for (int z = 0; z < 16; z++)
            for (int x = 0; x < 16; x++)
                if (biomes[x][z] != null)
                    d.writeend(biomes[x][z].getID());
                else
                    d.writeend(BiomeID.PLAINS.getID());
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
    	setType(x, y, z,type);
    	updateBlockChange(new Position(x*pos.getX(),y,z*pos.getY()), type);
	}
	
	public void setPrivateType(int x, int y, int z, BlockID type) {
    	setType(x, y, z,type);
	}
	
	public void setPrivateLight(int x, int y, int z, byte light) {
    	setLight(x, y, z, light);
	}
	
	
	public char getBlock(int x, int y, int z) {
		int s = y >> 4;
	
        if(sections[s] == null)
			return (char) -1;
        
        return sections[s].getType(x/16, y/16, z/16);
	}
	@Unsafe
	public final ChunkSection getSection(int y) {
        return sections[y >> 4];
	}
	
	public void setPrivateCube(int x, int y, int z, int w, int d, int h, BlockID type) {
		if(h == 0) return;
		if(w == 0) return;
		if(d == 0) return;
				
		int numSections = h/16;
		
		if(numSections == 0) {
            if(getSection(y) == null)
            	return;
            getSection(y).setPrivateCube(x, y, z, w, d, h, type);
		}
		else {
			//TODO Fix this stuff :P
			
		}
		
	}
}
