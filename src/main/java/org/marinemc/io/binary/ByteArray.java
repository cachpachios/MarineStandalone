package org.marinemc.io.binary;

import java.nio.charset.Charset;

public class ByteArray extends AbstractInput implements ByteOutput, StoredReader {

	byte[] data;
	
	int position = -1;
	
	public ByteArray(byte[] data) {
		this.data = data;
	}
	
	@Override
	public byte readByte() {
		return 0;
	}

	@Override
	public void writeByte(byte v) {
		write(new byte[] {v});
	}

	
	public void write(byte... v) {
		data = ByteUtils.writeEnd(data, v);
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
	public void writeBoolean(boolean v) {
		if(v)
			writeByte((byte) 0x01);
		else
			writeByte((byte) 0x00);
	}

	@Override
	public int getReaderPosition() {
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


	
}
