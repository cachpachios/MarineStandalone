package org.marinemc.io.binary;

public interface StoredReader {

	public int getReaderPosition();

	public int getRemainingBytes();

	public int getByteLength();

	public void skipBytes(int amount);

	public void backReader(int amount);

	public void skipNextByte();
}
