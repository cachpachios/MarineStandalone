package org.marinemc.io.binary;

import java.nio.charset.Charset;

public abstract class AbstractInput implements ByteInput{

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

}
