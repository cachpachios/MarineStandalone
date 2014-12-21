package org.marinemc.io.binary;

public class StaticArrayByteInput extends AbstractInput implements StoredReader, ByteFlusher {

	final byte[] data;

	int position;
	
	public StaticArrayByteInput(final byte[] data) {
		position = -1;
		this.data = data;
	}
	
	@Override
	public byte readByte() {
		return data[++position];
	}

	@Override
	public int getReaderPosition() {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public int getRemainingBytes() {
		// TODO Auto-generated method stub
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
	public byte[] flushBytes() {
		return data;
	}

}
