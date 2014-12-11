package com.marine.game.world;

import com.marine.events.standardevents.BlockChangeEvent;
import com.marine.game.PlayerManager;
import com.marine.game.WorldManager;
import com.marine.server.Marine;
import com.marine.world.Block;
import com.marine.world.BlockID;

public class BlockManager {
    private final PlayerManager players;
    private final WorldManager worlds;

    public BlockManager(PlayerManager p, WorldManager w) {
        this.players = p;
        this.worlds = w;
    }


    public void changeBlock(Block b, BlockID target) { // TODO EVENT
        BlockChangeEvent event = new BlockChangeEvent(b.getBlockPos(), b.getType(), target);
        Marine.getServer().callEvent(event);
        if (event.isCancelled())
            return;

        b.getChunk().getWorld().setTypeAt(b.getBlockPos(), target, true);
    }


}
