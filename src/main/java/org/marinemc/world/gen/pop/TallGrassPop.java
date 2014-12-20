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

package org.marinemc.world.gen.pop;

import org.marinemc.world.BlockID;
import org.marinemc.world.chunk.Chunk;
import org.marinemc.world.gen.ChunkPopulator;

public class TallGrassPop implements ChunkPopulator {

	@Override
	public void populate(Chunk c) {
		for(int x = 0; x < 16; x++)
			for(int y = 0; y < 16; y++)
				if (c.getRandom().nextInt(4) == 0) {
					if (c.getRandom().nextInt(4) != 0)
						if (c.getRandom().nextInt(5) != 0)
								c.setBlock(x, (int) c.getMaxHeightAt(x, y)+1, y, BlockID.TALL_GRASS);
							else
								c.setBlock(x, (int) c.getMaxHeightAt(x, y)+1, y, BlockID.FERN);
						else if (c.getRandom().nextBoolean()) {
								c.setBlock(x, (int) c.getMaxHeightAt(x, y)+1, y, BlockID.SUGERCANE);
								c.setBlock(x, (int) c.getMaxHeightAt(x, y)+1, y, BlockID.SUGERCANE);
								c.setBlock(x, (int) c.getMaxHeightAt(x, y)+1, y, BlockID.SUGERCANE);
						if (c.getRandom().nextBoolean())
									c.setBlock(x, (int) c.getMaxHeightAt(x, y)+1, y, BlockID.SUGERCANE);
							}
							else
								c.setBlock(x, (int) c.getMaxHeightAt(x, y)+1, y, BlockID.YELLOW_FLOWER);
								
				}
	}
}
