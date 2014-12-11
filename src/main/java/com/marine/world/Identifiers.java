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

import java.util.EnumSet;
import java.util.concurrent.ConcurrentHashMap;

public final class Identifiers {
    private static ConcurrentHashMap<Byte, BlockID> block_id;

    public static BlockID getBlockID(byte id) {
        if (block_id == null) {
            block_id = new ConcurrentHashMap<Byte, BlockID>();
            EnumSet<BlockID> set = EnumSet.allOf(BlockID.class);
            for (BlockID b : set)
                block_id.put(b.getID(), b);
        }


        if (block_id.containsKey(id))
            return block_id.get(id);
        else
            return BlockID.AIR;
    }

}
