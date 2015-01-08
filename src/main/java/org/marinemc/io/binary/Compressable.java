package org.marinemc.io.binary;

import org.marinemc.io.ByteCompressor.EncodingUseless;

public interface Compressable extends Byteable {
	public void compress() throws EncodingUseless;
}
