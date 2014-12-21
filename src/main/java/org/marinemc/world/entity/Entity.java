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

package org.marinemc.world.entity;

import org.marinemc.logging.Logging;
import org.marinemc.util.Location;
import org.marinemc.util.Position;
import org.marinemc.util.TrackedLocation;
import org.marinemc.util.vectors.Vector3d;
import org.marinemc.util.vectors.Vector3i;
import org.marinemc.world.World;
/**
 * @author Fozie
 */
public abstract class Entity {

    private static int nextEntityID = Integer.MIN_VALUE;
    private final int entityID;
    private final EntityType type;
    private World world;
    
    private TrackedLocation position;
    
    
    private int ticksLived;

    public Entity(final EntityType type, final int ID, final TrackedLocation pos) {
        this(type, ID, pos.getWorld(), pos);
    }
    
    public Entity(final EntityType type, final int ID, final Location pos) {
        this(type, ID, pos.getWorld(), new TrackedLocation(pos));
    }

    public Entity(final EntityType type, final int ID, final World world, final TrackedLocation pos) {
        this.entityID = ID;
        this.world = pos.getWorld();
        this.position = pos;
        this.ticksLived = 0;
        this.type = type;
    }

    public static int generateEntityID() {
        return ++nextEntityID;
    }

    public EntityType getType() {
        return this.type;
    }

    public abstract int getSendDistance();

    public abstract void update(); // Called each tick for ai/other updating

    public final void tick() {
        if (ticksLived >= Integer.MAX_VALUE - 2) {
            Logging.getLogger().error("Entity lived more than 3.6 years impressive! but now we have to remove some age because of memory:/");
            ticksLived = -1;
        }
        ++ticksLived;
    }

    @Override
    public String toString() {
        return "MarineEntity{\"id\":" + getEntityID() + "}";
    }

    public int getEntityID() {
        return entityID;
    }

    public World getWorld() {
        return world;
    }

    public double getX() {
        return position.getX();
    }

    public double getY() {
        return position.getY();
    }

    public double getZ() {
        return position.getZ();
    }

    public Vector3i getRelativeLocation() {
        return position.getRelativePosition();
    }

    public Position getRelativePosition() {
        return position.getRelativePosition();
    }

    public Vector3d getAbsoluteLocation() {
        return position;
    }

    public Location getPosition() {
        return position;
    }

    public TrackedLocation getTrackedPosition() {
        return position;
    }

    public int getSecondsLived() {
        return ticksLived / 20;
    }

    public int getTicksLived() {
        return ticksLived;
    }

}
