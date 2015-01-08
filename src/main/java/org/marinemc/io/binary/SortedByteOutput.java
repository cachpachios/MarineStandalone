package org.marinemc.io.binary;

import java.nio.charset.Charset;

public interface SortedByteOutput extends ByteOutput {
	public void writeBoolean(int pos, boolean v);

	public void writeByte(int pos, byte v);

	public void writeShort(int pos, short v);

	public void writeInt(int pos, int v);

	public void writeLong(int pos, long v);

	public void writeFloat(int pos, float v);

	public void writeDouble(int pos, double v);

	public void writeVarInt(int pos, int v);

	public void writeVarLong(int pos, long v);

	public void writeString(int pos, String s, Charset set);

	public void write(int pos, byte... input);
}
