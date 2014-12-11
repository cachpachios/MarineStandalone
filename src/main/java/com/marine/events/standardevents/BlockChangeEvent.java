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

package com.marine.events.standardevents;

import com.marine.events.Cancellable;
import com.marine.events.MarineEvent;
import com.marine.util.Position;
import com.marine.world.BlockID;

public class BlockChangeEvent extends MarineEvent implements Cancellable {

    private final Position blockPos;
    private final BlockID current;
    boolean isCancelled;
    private BlockID target;

    public BlockChangeEvent(Position blockPos, BlockID current, BlockID target) {
        super("BlockChangeEvent");
        this.blockPos = blockPos;
        this.current = current;
        this.target = target;
        this.isCancelled = false;
    }

    public BlockID getPrevious() {
        return this.current;
    }

    public BlockID getNew() {
        return this.target;
    }

    public void setNew(BlockID target) {
        if (target == this.getPrevious())
            this.setCancelled(true);
        else
            this.target = target;
    }

    public Position getPosition() {
        return this.blockPos;
    }

    @Override
    public boolean isCancelled() {
        return isCancelled;
    }

    @Override
    public void setCancelled(boolean b) {
        isCancelled = true;
    }

}
