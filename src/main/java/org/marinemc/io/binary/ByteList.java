package org.marinemc.io.binary;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


public class ByteList extends AbstractInput implements ByteOutput, ByteFlusher, StoredReader, Iterable {
	final List<Byte> data;
	
	int position;
	
	public ByteList() {
		data = new ArrayList<Byte>();
		position = 0;
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
		data.add(v);
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
			data.add(b);
	}

	@Override
	public byte[] flushBytes() {
		final int size = data.size();
        final byte[] result = new byte[size];
        for (int i = 0; i < size; i++) {
            result[i] = data.get(i);
        }
        return result;
	}

	@Override
	public byte readByte() {
		final byte r = data.get(position);
		++position;
		return r;
	}

	@Override
	public int getReaderPosition() {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public int getRemainingBytes() {
		// TODO Auto-generated method stub
		return getByteLength() - position;
	}

	@Override
	public int getByteLength() {
		return data.size();
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
	
	public void flip() {
	    for(int i = 0, j = data.size() - 1; i < j; i++) {
	        data.add(i, data.remove(j));
	    }
	}
	
	public void writeLengthPrefix() {
		final ByteList d = new ByteList();
		d.writeVarInt(data.size());
		d.flip();
		for(int i = 0; i < d.getByteLength(); i++)
			data.add(0, d.readByte());
	}
	
	public List<Byte> getList() {
		return data;
	}

	@Override
	public Iterator<Byte> iterator() {
		return data.iterator();
	}
}
