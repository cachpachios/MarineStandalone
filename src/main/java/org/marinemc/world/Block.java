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

import org.marinemc.util.Position;
import org.marinemc.world.chunk.Chunk;
/**
 * @author Fozie
 */
public class Block implements Comparable<BlockID> {
    private final Chunk chunk;
    private final byte localX, localZ;    // Local position in Chunk 			Range: (0-15)
    private final short y;                // Y same both localy and globaly	Range: (0-255)

    public Block(Chunk c, int localX, int localY, int localZ) {
        this.chunk = c;
        this.localX = (byte) localX;
        this.y = (byte) localY;
        this.localZ = (byte) localZ;
    }

    public Block(Chunk c, Position pos, BlockID type) {
        this(c, pos.getX() / c.getPos().getX(), pos.getY(), pos.getZ() / c.getPos().getY());
    }

    public Chunk getChunk() {
        return chunk;
    }

    public BlockID getType() {
        return chunk.getBlockTypeAt(localX, y, localZ);
    }
    
    public byte getBlockLight() {
        return (byte)-1; //TODO
    }
    
    public byte getSkyLight() {
        return (byte)-1; //TODO
    }

    public byte getLocalX() {
        return localX;
    }

    public int getX() {
        return localX * chunk.getPos().getX();
    }

    public int getZ() {
        return localZ * chunk.getPos().getY();
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
        return new Position(localX * chunk.getPos().getX(), y, localZ * chunk.getPos().getY());
    }

    public short getPacketID() {
        return (short) ((getType().getID() << 4) | getType().getMetaBlock());
    }

    @Override
    public int compareTo(BlockID o) {
    	return o.compareTo(getType());
    }
}

