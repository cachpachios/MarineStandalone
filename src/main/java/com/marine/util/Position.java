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

package com.marine.util;

import com.marine.util.vectors.Vector3i;
import com.marine.world.chunk.ChunkPos;
import org.json.JSONObject;

/**
 * Position Class
 *
 * Can be used to check for exact positions (x, y, z)
 *
 * @author Citymonstret
 * @author Fozie
 */
public class Position extends Vector3i {

    /**
     * Constructor
     *
     * @param x X Coordinate
     * @param y Y Coordinate
     * @param z Z Coordinate
     */
    public Position(int x, int y, int z) { // Used for relative posisitions (Blocks etc)
        super(x, y, z);
    }

    /**
     * Constructor
     * <p/>
     * Will default to (x=0, y=0, z=0)
     */
    public Position() {
        this(0, 0, 0);
    }

    /**
     * Decode an encoded position
     *
     * @param l Encoded long
     * @return Decoded position
     */
    public static Position Decode(long l) {
        int x = ((int) (l >> 38));
        int y = ((int) ((l >> 26) & 0xFFF));
        int z = ((int) (l << 38 >> 38));
        return new Position(x, y, z);
    }

    /**
     * Decode an encoded position
     * and this the current instance
     * to that position
     *
     * @param l Decoded position
     */
    public void decode(long l) {
        setX((int) (l >> 38));
        setY((int) ((l >> 26) & 0xFFF));
        setZ((int) (l << 38 >> 38));
    }

    /**
     * Encode the current position and
     * get the encoded long
     *
     * @return Encoded long
     */
    public long encode() {
        return ((getX() & 0x3FFFFFF) << 38) | ((getY() & 0xFFF) << 26) | (getZ() & 0x3FFFFFF);
    }

    /**
     * Create a location based on the current
     * instance, will set pitch and yaw to 0, 0
     *
     * @return new location (null, x, y, z, 0, 0)
     */
    public Location toLocation() {
        return new Location(null, getX(), getY(), getZ(), 0, 0);
    }

    /**
     * Create a new JSON Object based on the
     * current instance
     *
     * @return object containing (x, y, z)
     */
    public JSONObject toJSONObject() {
        JSONObject o = new JSONObject();
        try {
            o.put("x", getX());
            o.put("y", getY());
            o.put("z", getZ());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return o;
    }

    /**
     * Get the chunk position
     *
     * @return chunk pos(x >> 4, y >> 4)
     */
    public ChunkPos getChunkPos() {
        return new ChunkPos(getX() >> 4, getZ() >> 4);
    }

    /**
     * Get a new position
     *
     * @return (x divided by 16, z divided by 16)
     */
    public Position getChunkBlockPos() {
        return new Position(getX() / 16, getY(), getZ() / 16);
    }

    @Override
    public String toString() {
        return String.format("\"position\": {\"x\": %f, \"y\": %f, \"z\": %f}", getX(), getY(), getZ());
    }

}
