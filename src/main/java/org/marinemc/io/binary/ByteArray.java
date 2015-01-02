package org.marinemc.io.binary;

import java.util.Arrays;
import java.util.RandomAccess;

public class ByteArray extends AbstractInput implements StoredReader, ByteDataInput, RandomAccess {

	private byte[] data;

	private int position;
	
	public ByteArray(final byte[] data) {
		position = 0;
		this.data = data;
	}
	
	@Override
	public byte readByte() {
		return data[position++];
	}
	
	@Override
    public byte[] readBytes(int size) {
    	return Arrays.copyOfRange(data, position, position + size);
    }

	@Override
	public int getReaderPosition() {
		return position;
	}

	@Override
	public int getRemainingBytes() {
		return data.length - position;
	}

	@Override
	public int getByteLength() {
		return data.length;
	}

	@Override
	public void skipBytes(int amount) {
		position += amount;
	}

	@Override
	public void backReader(int amount) {
		position -= amount;
	}

	@Override
	public void skipNextByte() {
		++position;
	}

	@Override
	public byte[] toBytes() {
		return data;
	}

	public int length() {
		return data.length;
	}
	
}
