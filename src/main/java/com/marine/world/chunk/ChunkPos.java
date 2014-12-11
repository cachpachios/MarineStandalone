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

package com.marine.world.chunk;

public class ChunkPos implements Comparable<ChunkPos> {
    private final int x, y;

    public ChunkPos(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public ChunkPos(long encoded) {
        this((int) (encoded >> 32), (int) encoded);
    }

    public static long Encode(int x, int y) {
        return (long) x << 32 | y & 0xFFFFFFFFL;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public long encode() {
        return (long) x << 32 | y & 0xFFFFFFFFL;
    }

    @Override
    public int compareTo(ChunkPos v) {
        if (x == v.x && y == v.y)
            return 0;
        if (x < v.x && y < v.y)
            return -1;
        else
            return 1;
    }
}
