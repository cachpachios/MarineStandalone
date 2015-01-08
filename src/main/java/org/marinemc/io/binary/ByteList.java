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

import org.marinemc.io.ByteCompressor;
import org.marinemc.io.ByteCompressor.EncodingUseless;
import org.marinemc.util.Position;

public class ByteList extends AbstractInput implements ByteDataOutput,
		CompressableStoredByteOutput, ByteDataInput, StoredReader,
		Iterable<Byte>, RandomAccess {
	List<Byte> data;

	int position;

	public ByteList() {
		data = new ArrayList<Byte>();
		position = 0;
	}

	public ByteList(final int preSize) {
		data = new ArrayList<Byte>(preSize);
		position = 0;
	}

	public ByteList(final byte[] data) {
		this.data = new ArrayList<Byte>(Arrays.asList(ByteUtils.wrap(data)));
		position = 0;
	}

	public ByteList(final Byteable input) {
		this(input.toBytes());
	}

	@Override
	public void writeBoolean(final int pos, final boolean v) {
		if (v)
			writeByte(pos, (byte) 0x01);
		else
			writeByte(pos, (byte) 0x00);
	}

	@Override
	public void writeByte(final int pos, final byte v) {
		data.add(pos, v);
	}

	@Override
	public void writeShort(final int pos, final short v) {
		writeByte(pos, (byte) (0xff & v >> 8));
		writeByte(pos, (byte) (0xff & v));
	}

	@Override
	public void writeInt(final int pos, final int v) {
		writeByte(pos, (byte) (0xff & v >> 24));
		writeByte(pos, (byte) (0xff & v >> 16));
		writeByte(pos, (byte) (0xff & v >> 8));
		writeByte(pos, (byte) (0xff & v));
	}

	@Override
	public void writeLong(final int pos, final long v) {
		writeByte(pos, (byte) (0xff & v >> 56));
		writeByte(pos, (byte) (0xff & v >> 48));
		writeByte(pos, (byte) (0xff & v >> 40));
		writeByte(pos, (byte) (0xff & v >> 32));
		writeByte(pos, (byte) (0xff & v >> 24));
		writeByte(pos, (byte) (0xff & v >> 16));
		writeByte(pos, (byte) (0xff & v >> 8));
		writeByte(pos, (byte) (0xff & v));
	}

	@Override
	public void writeFloat(final int pos, final float v) {
		writeInt(pos, Float.floatToIntBits(v));
	}

	@Override
	public void writeDouble(final int pos, final double v) {
		writeLong(pos, Double.doubleToLongBits(v));
	}

	@Override
	public void writeVarInt(final int pos, int v) {
		final List<Byte> varInt = new ArrayList<Byte>();
		byte part;
		while (true) {
			part = (byte) (v & 0x7F);
			v >>>= 7;
			if (v != 0)
				part |= 0x80;
			varInt.add(part);
			if (v == 0)
				break;
		}
		data.addAll(pos, varInt);
	}

	@Override
	public void writeVarLong(final int pos, long v) {
		byte part;
		while (true) {
			part = (byte) (v & 0x7F);
			v >>>= 7;
			if (v != 0)
				part |= 0x80;
			writeByte(pos, part);
			if (v == 0)
				break;
		}
	}

	@Override
	public void writeString(final int pos, final String s, final Charset charset) {
		write(pos, s.getBytes(charset));
	}

	@Override
	public void write(final int pos, final byte... input) {
		for (final byte b : input)
			data.add(pos, b);
	}

	// At end writers

	@Override
	public void writeBoolean(final boolean v) {
		if (v)
			writeByte((byte) 0x01);
		else
			writeByte((byte) 0x00);
	}

	@Override
	public void writeByte(final byte v) {
		data.add(v);
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
		writeLong(Double.doubleToLongBits(v));
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
	public void skipBytes(final int amount) {
		position += amount;
	}

	@Override
	public void backReader(final int amount) {
		position -= amount;
	}

	@Override
	public void skipNextByte() {
		++position;
	}

	public void flip() {
		for (int i = 0, j = data.size() - 1; i < j; i++)
			data.add(i, data.remove(j));
	}

	public void writeLengthPrefix() {
		final ByteList d = new ByteList();
		d.writeVarInt(data.size());
		d.flip();
		for (int i = 0; i < d.getByteLength(); i++)
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

	public void writeUTF8Short(final String name) {
		writeShort((short) name.length());
		writeString(name, StandardCharsets.UTF_8);
	}

	public void writeUTF8(final String name) {
		final byte[] stringData = name.getBytes(StandardCharsets.UTF_8);
		writeVarInt(stringData.length);
		write(stringData);
	}

	@Override
	public int size() {
		return data.size();
	}

	public void writeUUID(final UUID uuid) {
		// writeLong(uuid.getLeastSignificantBits());
		// writeLong(uuid.getMostSignificantBits());
		writeUTF8(uuid.toString());
	}

	public void writePosition(final Position pos) {
		writeLong(pos.encode());
	}

	public Byte[] getPrimArray() {
		return (Byte[]) data.toArray();
	}

	public Collection<Byte> getCollection() {
		return data;
	}

	public void writeData(final ByteList list) {
		data.addAll(list.getCollection());
	}

	@Override
	public void compress() throws EncodingUseless {
		data = new ArrayList<>(Arrays.asList(ByteUtils.wrap(ByteCompressor
				.instance().encode(toBytes()))));
	}
}
