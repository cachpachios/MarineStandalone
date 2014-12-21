package org.marinemc.io.binary;

public interface ByteData extends ByteInput, ByteOutput, StoredReader, ByteFlusher {
	public int getWriterPosition();
}	
