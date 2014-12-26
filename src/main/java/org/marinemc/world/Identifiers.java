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

import org.marinemc.util.annotations.Cautious;
import org.marinemc.util.annotations.Hacky;
import org.marinemc.util.annotations.Unsafe;
import org.marinemc.world.chunk.ChunkSection;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * Static class to index map data values
 * Hacky and not recommended way, preformance is at the cost.
 *
 * @author Fozie
 */

@Hacky
public final class Identifiers {

	private static int blockSize;
	
    private static Map<Byte, BlockID> block_id;
    private static Map<Character, BlockID> block_encode;

    public static BlockID getBlockID(byte id) {
        if (block_id == null) init();

        if (block_id.containsKey(id))
            return block_id.get(id);
        else
            return BlockID.AIR;
    }

    public static BlockID decodeBlock(char id) {
        if (block_encode == null) init();

        if (block_encode.containsKey(id))
            return block_encode.get(id);
        else
            return BlockID.AIR;
    }
    
    
    public static BlockID randomBlock() {
        if (block_id == null) init();
        byte r = (byte) (Math.random() * blockSize);
        
        if(r < 0)
        	return BlockID.AIR;
        else
        	return block_id.get(r);
    }
    
    @Unsafe
    @Cautious
    public static void init() {
        block_id = new HashMap<>();
        block_encode = new HashMap<>();
        EnumSet<BlockID> set = EnumSet.allOf(BlockID.class);
        for (BlockID b : set) {
            block_id.put(b.getID(), b);
            block_encode.put(ChunkSection.EncodeType(b), b);
        }
        
        blockSize = block_id.size();
        
    }
}
