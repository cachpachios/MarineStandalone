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

package org.marinemc.world.chunk;

import org.marinemc.util.vectors.Vector2i;

/**
 * @author Fozie
 */
public class ChunkPos extends Vector2i implements Comparable<ChunkPos> {

	public ChunkPos(final int x, final int y) {
		super(x, y);
	}

	public ChunkPos(final long encoded) {
		this((int) (encoded >> 32), (int) encoded);
	}

	public static long Encode(final int x, final int y) {
		return (long) x << 32 | y & 0xFFFFFFFFL;
	}

	public long encode() {
		return (long) getX() << 32 | getY() & 0xFFFFFFFFL;
	}

	@Override
	public int compareTo(final ChunkPos v) {
		final int x = getX(), y = getY(), x2 = v.getX(), y2 = v.getY();
		if (x == x2 && y == y2)
			return 0;
		if (x < x2 && y < y2)
			return -1;
		else
			return 1;
	}
	
	@Override
	public String toString() {
		return "X: " + x + " Y: " + y;
	}
}
