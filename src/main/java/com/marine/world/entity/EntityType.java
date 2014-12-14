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

package com.marine.world.entity;

import com.marine.util.vectors.Vector3d;

/**
 * Created 2014-12-11 for MarineStandalone
 *
 * @author Citymonstret
 */
public enum EntityType {
    ////////////////////////////////////////////////////////////////////////////
    // For a list of all entities, and the data that should be added for each
    // see: http://wiki.vg/Entities
    ////////////////////////////////////////////////////////////////////////////
    PLAYER(Type.ENTITY, -1, -1d, -1d, -1d), /* Normal rules doesn't apply to players */
    CHICKEN(Type.ENTITY, 93, 0.3, 0.7, 0.3);

    private final Type type; // (Entity) / (Vehicle / Object)
    private final int id; // entity type ID
    private final Vector3d size; // advanced hit boxes and such

    private EntityType(final Type type, final int id, final double xSize,
                       final double ySize, final double zSize) {
        this.type = type;
        this.id = id;
        this.size = new Vector3d(xSize, ySize, zSize);
    }

    public Vector3d getSize() {
        return this.size;
    }

    public int getID() {
        return this.id;
    }

    public Type getType() {
        return this.type;
    }

    public static enum Type {
        ENTITY, OBJECT
    }
}
