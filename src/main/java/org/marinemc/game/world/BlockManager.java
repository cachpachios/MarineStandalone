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

package org.marinemc.game.world;

import org.marinemc.events.standardevents.BlockChangeEvent;
import org.marinemc.game.PlayerManager;
import org.marinemc.game.WorldManager;
import org.marinemc.server.Marine;
import org.marinemc.world.Block;
import org.marinemc.world.BlockID;

public class BlockManager {

    private final PlayerManager players;
    private final WorldManager worlds;

    public BlockManager(PlayerManager p, WorldManager w) {
        this.players = p;
        this.worlds = w;
    }

    public void changeBlock(Block b, BlockID target) {
        BlockChangeEvent event = new BlockChangeEvent(b.getGlobalPos(), b.getType(), target);
        Marine.getServer().callEvent(event);
        if (event.isCancelled())
            return;
        b.getChunk().getWorld().setTypeAt(b.getGlobalPos(), target, true);
    }


}
