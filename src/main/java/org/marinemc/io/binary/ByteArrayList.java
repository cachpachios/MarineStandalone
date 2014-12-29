package org.marinemc.io.binary;

import java.nio.charset.Charset;
import java.util.ArrayList;

public class ByteArrayList extends ArrayList<Byte> implements ByteInput, ByteOutput, Byteable{
	private static final long serialVersionUID = -7950020843968716560L;

	@Override
	public boolean readBoolean() {
		return readByte() == 0x01;
	}

	@Override
	public short readShort() {
        return (short) ((readByte() << 8) | (readByte() & 0xff));
	}

	public int readUnsignedShort() {
        return (((readByte() & 0xff) << 8) | (readByte() & 0xff));
	}
	
	@Override
	public int readInt() {
		return (((readByte() & 0xff) << 24) | ((readByte() & 0xff) << 16) |
            ((readByte() & 0xff) << 8) | (readByte() & 0xff));
	}

	@Override
	public long readLong() {
		return (((long) (readByte() & 0xff) << 56) |
            ((long) (readByte() & 0xff) << 48) |
            ((long) (readByte() & 0xff) << 40) |
            ((long) (readByte() & 0xff) << 32) |
            ((long) (readByte() & 0xff) << 24) |
            ((long) (readByte() & 0xff) << 16) |
            ((long) (readByte() & 0xff) << 8) |
            ((long) (readByte() & 0xff)));
	}

    public float readFloat() {
        return Float.intBitsToFloat(readInt());
    }

    public double readDouble() {
        return Double.longBitsToDouble(readLong());
    }
	
    public int readVarInt() {
        int out = 0;
        int bytes = 0;
        byte in;
        while (true) {
            in = readByte();
            out |= (in & 0x7F) << (bytes++ * 7);
            if ((in & 0x80) != 0x80) {
                break;
            }
        }
        return out;
    }


	@Override
	public long readVarLong() {
        long out = 0;
        long bytes = 0;
        byte in;
        while (true) {
            in = readByte();
            out |= (in & 0x7F) << (bytes++ * 7);
            if ((in & 0x80) != 0x80) {
                break;
            }
        }
        return out;
	}
	
    public byte[] read(byte... input) {
        int i = 0;
        while (i < input.length) {
            input[i] = readByte();
            i++;
        }
        return input;
    }
    
    public byte[] readBytes(int size) {
        final byte[] r = new byte[size];
        for(int i = 0; i < size;i++)
            r[i] = readByte();

        return r;
    }

	@Override
	public String readString(final int size, final Charset charset) {
		return new String(readBytes(size), charset);
	}

	@Override
	public byte[] toBytes() {
		return ByteUtils.unwrap((Byte[]) this.toArray());
	}

	@Override
	public void writeBoolean(boolean v) {
		if(v)
			writeByte((byte) 0x01);
		else
			writeByte((byte) 0x00);
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

	private transient int position;
	
	@Override
	public byte readByte() {
		return get(position++);
	}

	@Override
	public void writeByte(byte v) {
		add(v);
	}

	@Override
	public void write(byte... input) {
		addAll(ByteUtils.asWrappedList(input));
	}
}
