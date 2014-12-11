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
