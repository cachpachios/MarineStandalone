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

import java.lang.ref.WeakReference;

import org.marinemc.util.Position;
import org.marinemc.world.chunk.Chunk;
/**
 * @author Fozie
 */
public class Block implements Comparable<BlockID> {
    private WeakReference<Chunk> chunk;
    private final byte localX, localZ;	   // Local position in Chunk 			Range: (0-15)
    private final short y;                // Y same both localy and globaly	Range: (0-255)

    public Block(final Chunk c, int localX, int localY, int localZ) {
        this.chunk = new WeakReference<>(c);
        this.localX = (byte) localX;
        this.y = (byte) localY;
        this.localZ = (byte) localZ;
    }

    public Block(Chunk c, Position pos, BlockID type) {
        this(c, pos.getX() / c.getPos().getX(), pos.getY(), pos.getZ() / c.getPos().getY());
    }

    public Chunk getChunk() {
        return chunk.get();
    }

    boolean referenceCheck() {
    	return chunk.get() == null;
    }
    
    public BlockID getType() {
    	if(referenceCheck()) return BlockID.AIR;
        return chunk.get().getBlockTypeAt(localX, y, localZ);
    }
    
    public byte getBlockLight() {
    	if(referenceCheck()) return (byte)-1;
        return (byte)-1; //TODO
    }
    
    public byte getSkyLight() {
    	if(referenceCheck()) return (byte)-1;
        return (byte)-1; //TODO
    }

    public byte getLocalX() {
        return localX;
    }

    public int getX() {
    	if(referenceCheck()) return 0;
        return localX * chunk.get().getPos().getX();
    }

    public int getZ() {
    	if(referenceCheck()) return 0;
        return localZ * chunk.get().getPos().getY();
    }

    public short getLocalY() { // Dublicate of getY();
        return getY();
    }

    public short getY() {
        return y;
    }

    public byte getLocalZ() {
        return localZ;
    }

    public Position getGlobalPos() {
    	if(referenceCheck()) return null;
        return new Position(localX * chunk.get().getPos().getX(), y, localZ * chunk.get().getPos().getY());
    }

    public short getPacketID() {
    	if(referenceCheck()) return (short) 0;
		return (short) (((getType().getIntID() << 4) & 0xfff0) | getType().getMetaBlock());
    }

    @Override
    public int compareTo(BlockID o) {
    	return o.compareTo(getType());
    }
}

