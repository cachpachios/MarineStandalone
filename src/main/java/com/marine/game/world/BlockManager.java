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
