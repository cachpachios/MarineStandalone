package org.marinemc.io.binary;

import java.nio.charset.Charset;
import java.util.Deque;
import java.util.LinkedList;

public class ByteWrapper implements ByteDataOutput {
	final Deque<Byte> queue;

	public ByteWrapper() {
		queue = new LinkedList<Byte>();
	}

	@Override
	public void writeBoolean(final boolean v) {
		if (v)
			writeByte((byte) 0x01);
		else
			writeByte((byte) 0x00);
	}

	@Override
	public void writeByte(final byte v) {
		queue.add(v);
	}

	@Override
	public void writeShort(final short v) {
		writeByte((byte) (0xff & v >> 8));
		writeByte((byte) (0xff & v));
	}

	@Override
	public void writeInt(final int v) {
		writeByte((byte) (0xff & v >> 24));
		writeByte((byte) (0xff & v >> 16));
		writeByte((byte) (0xff & v >> 8));
		writeByte((byte) (0xff & v));
	}

	@Override
	public void writeLong(final long v) {
		writeByte((byte) (0xff & v >> 56));
		writeByte((byte) (0xff & v >> 48));
		writeByte((byte) (0xff & v >> 40));
		writeByte((byte) (0xff & v >> 32));
		writeByte((byte) (0xff & v >> 24));
		writeByte((byte) (0xff & v >> 16));
		writeByte((byte) (0xff & v >> 8));
		writeByte((byte) (0xff & v));
	}

	@Override
	public void writeFloat(final float v) {
		writeInt(Float.floatToIntBits(v));
	}

	@Override
	public void writeDouble(final double v) {
		writeDouble(Double.doubleToLongBits(v));
	}

	@Override
	public void writeVarInt(int v) {
		byte part;
		while (true) {
			part = (byte) (v & 0x7F);
			v >>>= 7;
			if (v != 0)
				part |= 0x80;
			writeByte(part);
			if (v == 0)
				break;
		}
	}

	@Override
	public void writeVarLong(long v) {
		byte part;
		while (true) {
			part = (byte) (v & 0x7F);
			v >>>= 7;
			if (v != 0)
				part |= 0x80;
			writeByte(part);
			if (v == 0)
				break;
		}
	}

	@Override
	public void writeString(final String s, final Charset charset) {
		write(s.getBytes(charset));
	}

	@Override
	public void write(final byte... input) {
		for (final byte b : input)
			queue.add(b);
	}

	@Override
	public byte[] toBytes() {
		final int size = queue.size();
		final byte[] result = new byte[size];
		for (int i = 0; i < size; i++)
			result[i] = queue.poll();
		return result;
	}

	@Override
	public int size() {
		return queue.size();
	}
}