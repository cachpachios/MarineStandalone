package org.marinemc.io.binary;

import java.nio.charset.Charset;

public interface ByteInput {
	public boolean readBoolean();

	public byte readByte();

	public short readShort();

	public int readUnsignedShort();

	public int readInt();

	public long readLong();

	public float readFloat();

	public double readDouble();

	public int readVarInt();

	public long readVarLong();

	public String readString(int size, Charset charset);

	public byte[] read(byte... input);

	public byte[] readBytes(int size);
}
