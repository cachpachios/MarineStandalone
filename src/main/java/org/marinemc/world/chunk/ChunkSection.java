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

import java.util.Arrays;

import org.marinemc.io.binary.ByteList;
import org.marinemc.util.Position;
import org.marinemc.util.annotations.Hacky;
import org.marinemc.util.annotations.Unsafe;
import org.marinemc.world.BlockID;
import org.marinemc.world.Identifiers;

/**
 * Final storage of blocks and lightning.
 *
 * @author Fozie
 */
@Unsafe
public final class ChunkSection {
	
	/**
	 * Amount of bytes per save chunk,
	 * Total byte size per chunk for blocks and lightning is DATA_SIZE * 3
	 */
    final static int DATA_SIZE = 16 * 16 * 16;
    private final int sectionID;
    private final ChunkPos chunkPos;
    private short[] blockMap; // Each block is 2 bytes some for the block id some for the metadata. 
    private byte[] lightMap; // TODO Replace with nibblearray

    /**
     * Create an empty chunksection with parent chunk and id.
     * @param c The chunk the chunk section is beloning to
     * @param y The ID or Y value of the chunk, to get global y multiply it with 16
     */
    public ChunkSection(final Chunk c, int y) {
        this.chunkPos = c.getPos();
        this.sectionID = y;
        this.blockMap = new short[DATA_SIZE];
        this.lightMap = new byte[DATA_SIZE];
    }
    
    public static short EncodeType(final BlockID type) {
    	if(type.isMetaBlock())
    		return (short) (((type.getIntID() << 4) & 0xfff0) | type.getMetaBlock());
    	else
    		return (short) (type.getIntID() << 4);
    }

    public static int getIndex(int x, int y, int z) {
        return ((y & 0xf) << 8) | (z << 4) | x;
    }

    public static final <T extends Comparable<T>> T clamp(T v, T min, T max) {
        if (v.compareTo(max) == 1)
            return max;
        else if (v.compareTo(min) == -1)
            return min;
        else
            return v;
    }

    public byte[] getBlockData() { //TODO: Optimize
        byte[] raw = new byte[DATA_SIZE * 2];
        int i = -1;
        for (short id : blockMap) {
            raw[++i] = ((byte) (id & 0xff));
            raw[++i] = ((byte) (id >> 8));
        }
        return raw;
    }

    //TODO: NibbleArray based lightning
    public final ByteList getLightData() {
    	ByteList d = new ByteList();
    	for(int i = 0; i < DATA_SIZE; i++)
    		d.writeByte((byte)-1);
    	return d;
    }

    public void setType(int x, int y, int z, BlockID id) {
        blockMap[getIndex(x, y, z)] = EncodeType(id);
    }

    public void setLight(int x, int y, int z, byte light) {
        lightMap[getIndex(x, y, z)] = light;
    }

    public short getType(int x, int y, int z) {
        return blockMap[getIndex(x, y, z)];
    }

    @Hacky
    public BlockID getBlock(int x, int y, int z) {
    	//TODO Include MetaData in the search
        return Identifiers.getBlockID((byte) (getType(x, y, z) & 0xff));
    }

    public int getID() {
        return sectionID;
    }

    public void setPrivateCube(int x, int y, int z, int w, int d, int h, BlockID type) {
        for (int xx = x; xx < x+w; xx++)
            for (int zz = z; zz < z+d; zz++)
            	for (int yy = y; yy < y+h; yy++)
            		setType(xx, yy, zz, type);
    }

    public GlobalBlock[] setCube(int x, int y, int z, int w, int d, int h, BlockID type) {
        final GlobalBlock[] r = new GlobalBlock[w * d * h];
        int i = 0;
        for (int xx = clamp(-(w / 2), 0, 15); xx < (w / 2); xx++)
            for (int yy = clamp(-(h / 2), 0, 15); yy < (h / 2); yy++)
                for (int zz = clamp(-(d / 2), 0, 15); yy < (d / 2); zz++) {
                    setType(x + xx, y + yy, z + zz, type);
                    r[++i] = new GlobalBlock(getGlobalBlockPos(x + xx, y + yy, z + zz), type);
                }
        return r;
    }

    public Position getGlobalBlockPos(int x, int y, int z) {
        int cX = chunkPos.getX() + 1;
        int cY = chunkPos.getX() + 1;

        if (cX == 0) cX = -1;
        if (cY == 0) cY = -1;

        return new Position(x * cX, y * (sectionID + 1), z * cY);
    }

    public void fillSection(BlockID type) {
        Arrays.fill(blockMap, (short) (type.getID() << 4));
    }
}