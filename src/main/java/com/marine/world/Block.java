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

package com.marine.world;

import com.marine.util.Position;
import com.marine.world.chunk.Chunk;

public class Block implements Comparable<BlockID> {
	private final Chunk chunk;
	
	private BlockID type;
	
	private byte blockLight;
	private byte skyLight;
	
	private final byte localX,localZ; 	// Local position in Chunk 			Range: (0-15)
	private final short y;				// Y same both localy and globaly	Range: (0-255)
	
	public Block(Chunk c, int localX, int localY, int localZ, BlockID type, byte blockLight, byte skyLight) {
		this.chunk = c;
		this.localX = (byte) localX;
		this.y = (byte) localY;
		this.localZ = (byte) localZ;
		this.type = type;
		this.blockLight = blockLight;
		this.skyLight = skyLight;
	}
	
	public Block(Chunk c, int localX, int y, int localZ, BlockID type) {
		this(c,localX,y, localZ, type,(byte)-1,(byte)-1);
	}
	
	public Block(Chunk c, Position pos, BlockID type) {
		this(c,pos.getX()/c.getPos().getX(),pos.getY(), pos.getZ()/c.getPos().getY(),type);
	}
	
	public void setType(BlockID type) {
		this.type = type;
	}
	
	public void setBlockLight(byte light) {
		blockLight = light; 
	}
	
	public void setSkyLight(byte light) {
		skyLight = light; 
	}
	
	public Chunk getChunk() {
		return chunk;
	}

	public BlockID getType() {
		return type;
	}

	public byte getBlockLight() {
		return blockLight;
	}

	public byte getSkyLight() {
		return skyLight;
	}

	public byte getLocalX() {
		return localX;
	}

	public int getGlobalX() {
		return localX * chunk.getPos().getX();
	}
	public int getGlobalZ() {
		return localZ * chunk.getPos().getY();
	}
	
	public short getGlobalY() { // Dublicate of getY();
		return getY();
	}
	
	public short getY() {
		return y;
	}

	public byte getLocalZ() {
		return localZ;
	}
	
	public Position getGlobalPos() {
		return new Position(localX * chunk.getPos().getX(), y, localZ * chunk.getPos().getY());
	}

	public short getPacketBlock() {
		return (short)((type.getID() << 4) | type.getMetaBlock());
	}

	@Override
	public int compareTo(BlockID o) {
		return new Short(type.getPacketID()).compareTo(new Short(o.getPacketID()));
	}
}

