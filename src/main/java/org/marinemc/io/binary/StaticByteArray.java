package org.marinemc.io.binary;

public class StaticByteArray extends AbstractInput implements StoredReader, Byteable {

	final byte[] data;

	int position;
	
	public StaticByteArray(final byte[] data) {
		position = -1;
		this.data = data;
	}
	
	@Override
	public byte readByte() {
		return data[++position];
	}

	@Override
	public int getReaderPosition() {
		if(position == -1)
			return 0;
		else
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

}
