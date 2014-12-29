package org.marinemc.io.binary;

import java.nio.charset.Charset;
import java.util.Deque;
import java.util.LinkedList;

public class ByteQueue extends AbstractInput implements ByteDataOutput, ByteDataInput {
	final Deque<Byte> queue;
	
	public ByteQueue(byte[] data) {
		queue = new LinkedList<Byte>();
		for(byte b : data)
			queue.add(b);
	}

	public ByteQueue() {
		queue = new LinkedList<Byte>();
	}

	@Override
	public byte readByte() {
		return queue.poll();
	}
	
	public byte peakByte() {
		return queue.peek();
	}
	
	@Override
	public void writeBoolean(boolean v) {
		if(v)
			writeByte((byte) 0x01);
		else
			writeByte((byte) 0x00);
	}

	@Override
	public void writeByte(byte v) {
		queue.add(v);
	}

	@Override
	public void writeShort(short v) {
		writeByte((byte) (0xff & (v >> 8)));
        writeByte((byte) (0xff & v));
	}

	@Override
	public void writeInt(int v) {
		writeByte((byte) (0xff & (v >> 24)));
		writeByte((byte) (0xff & (v >> 16)));
		writeByte((byte) (0xff & (v >> 8)));
		writeByte((byte) (0xff & v));
	}

	@Override
	public void writeLong(long v) {
		writeByte((byte) (0xff & (v >> 56)));
		writeByte((byte) (0xff & (v >> 48)));
		writeByte((byte) (0xff & (v >> 40)));
		writeByte((byte) (0xff & (v >> 32)));
		writeByte((byte) (0xff & (v >> 24)));
		writeByte((byte) (0xff & (v >> 16)));
		writeByte((byte) (0xff & (v >> 8)));
		writeByte((byte) (0xff & v));
	}

	@Override
	public void writeFloat(float v) {
		writeInt(Float.floatToIntBits(v));
	}

	@Override
	public void writeDouble(double v) {
		writeDouble(Double.doubleToLongBits(v));
	}
	@Override
	public void writeVarInt(int v) {
	        byte part;
	        while (true) {
	            part = (byte) (v & 0x7F);
	            v >>>= 7;
	            if (v != 0) {
	                part |= 0x80;
	            }
	            writeByte(part);
	            if (v == 0) {
	                break;
	            }
	        }
	}

	@Override
	public void writeVarLong(long v) {
        byte part;
        while (true) {
            part = (byte) (v & 0x7F);
            v >>>= 7;
            if (v != 0) {
                part |= 0x80;
            }
            writeByte(part);
            if (v == 0) {
                break;
            }
        }
	}

	@Override
	public void writeString(final String s, final Charset charset) {
		write(s.getBytes(charset));
	}

	@Override
	public void write(byte... input) {
		for(byte b : input)
			queue.add(b);
	}

	@Override
	public byte[] toBytes() {
		final int size = queue.size();
		final byte[] result = new byte[size];
		for (int i = 0; i < size; i++) {
			result[i] = queue.poll();
		}
		return result;
	}

	@Override
	public int size() {
		return queue.size();
	}
}
