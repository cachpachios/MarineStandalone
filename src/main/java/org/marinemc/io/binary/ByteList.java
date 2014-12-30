package org.marinemc.io.binary;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.RandomAccess;
import java.util.UUID;

import org.marinemc.util.Position;


public class ByteList extends AbstractInput implements ByteDataOutput, SortedByteOutput, ByteDataInput, StoredReader, Iterable<Byte>, RandomAccess {
	final List<Byte> data;
	
	int position;
	
	public ByteList() {
		data = new ArrayList<Byte>();
		position = 0;
	}
	
	public ByteList(final int preSize) {
		data = new ArrayList<Byte>(preSize);
		position = 0;
	}

	public ByteList(byte[] data) {
		this.data = new ArrayList<Byte>(Arrays.asList(ByteUtils.wrap(data)));
		position = 0;
	}
	
	public ByteList(Byteable input) {
		this(input.toBytes());
	}
	
	@Override
	public void writeBoolean(int pos, boolean v) {
		if(v)
			writeByte(pos, (byte) 0x01);
		else
			writeByte(pos, (byte) 0x00);
	}

	@Override
	public void writeByte(int pos, byte v) {
		data.add(pos, v);
	}

	@Override
	public void writeShort(int pos, short v) {
		writeByte(pos, (byte) (0xff & (v >> 8)));
        writeByte(pos, (byte) (0xff & v));
	}

	@Override
	public void writeInt(int pos, int v) {
		writeByte(pos, (byte) (0xff & (v >> 24)));
		writeByte(pos, (byte) (0xff & (v >> 16)));
		writeByte(pos, (byte) (0xff & (v >> 8)));
		writeByte(pos, (byte) (0xff & v));
	}

	@Override
	public void writeLong(int pos, long v) {
		writeByte(pos, (byte) (0xff & (v >> 56)));
		writeByte(pos, (byte) (0xff & (v >> 48)));
		writeByte(pos, (byte) (0xff & (v >> 40)));
		writeByte(pos, (byte) (0xff & (v >> 32)));
		writeByte(pos, (byte) (0xff & (v >> 24)));
		writeByte(pos, (byte) (0xff & (v >> 16)));
		writeByte(pos, (byte) (0xff & (v >> 8)));
		writeByte(pos, (byte) (0xff & v));
	}

	@Override
	public void writeFloat(int pos, float v) {
		writeInt(pos, Float.floatToIntBits(v));
	}

	@Override
	public void writeDouble(int pos, double v) {
		writeLong(pos, Double.doubleToLongBits(v));
	}
	@Override
	public void writeVarInt(int pos, int v) {
	        byte part;
	        while (true) {
	            part = (byte) (v & 0x7F);
	            v >>>= 7;
	            if (v != 0) {
	                part |= 0x80;
	            }
	            writeByte(pos, part);
	            if (v == 0) {
	                break;
	            }
	        }
	}

	@Override
	public void writeVarLong(int pos, long v) {
        byte part;
        while (true) {
            part = (byte) (v & 0x7F);
            v >>>= 7;
            if (v != 0) {
                part |= 0x80;
            }
            writeByte(pos, part);
            if (v == 0) {
                break;
            }
        }
	}

	@Override
	public void writeString(int pos, final String s, final Charset charset) {
		write(pos, s.getBytes(charset));
	}

	@Override
	public void write(int pos, byte... input) {
		for(byte b : input)
			data.add(pos, b);
	}
	
	// At end writers
	
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

	@Override
	public byte[] toBytes() {
		return ByteUtils.unwrap(data.toArray(new Byte[data.size()]));
	}

	public void writeUTF8Short(String name) {
		writeShort((short) name.length());
		writeString(name, StandardCharsets.UTF_8);
	}
	public void writeUTF8(String name) {
		writeVarInt(name.length());
		writeString(name, StandardCharsets.UTF_8);
	}

	@Override
	public int size() {
		return data.size();
	}

	public void writeUUID(UUID uuid) {
		writeLong(uuid.getLeastSignificantBits());
		writeLong(uuid.getMostSignificantBits());
	}

	public void writePosition(Position pos) {
		writeLong(pos.encode());	
	}
	
	public Byte[] getPrimArray() {
		return (Byte[]) data.toArray();
	}
	
	public Collection<Byte> getCollection() {
		return data;
	}
	
	public void writeData(ByteList list) {
		data.addAll(list.getCollection());
	}
}
