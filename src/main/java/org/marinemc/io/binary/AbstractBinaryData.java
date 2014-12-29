package org.marinemc.io.binary;

import java.nio.charset.Charset;

public abstract class AbstractBinaryData extends AbstractInput implements SortedByteOutput {
	@Override
	public void writeBoolean(int pos, boolean v) {
		if(v)
			writeByte(pos, (byte) 0x01);
		else
			writeByte(pos, (byte) 0x00);
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
	
	// At end writers
	
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
}
