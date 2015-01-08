package org.marinemc.io.binary;

import java.nio.charset.Charset;

public interface ByteOutput {

	public void writeBoolean(boolean v);

	public void writeByte(byte v);

	public void writeShort(short v);

	public void writeInt(int v);

	public void writeLong(long v);

	public void writeFloat(float v);

	public void writeDouble(double v);

	public void writeVarInt(int v);

	public void writeVarLong(long v);

	public void writeString(String s, Charset set);

	public void write(byte... input);

	public int size();
}
