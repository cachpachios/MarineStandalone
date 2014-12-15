package org.marinemc.world.gen;

import org.marinemc.world.chunk.Chunk;

/**
 * Use to poulate chunk (add flowers, trees etc)
 * 
 * @author Fozie
 *
 */
public interface ChunkPopulator {
	public void populate(Chunk c);
}
