package com.marineapi.net.data;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public final class ByteEncoder {
	public static byte[] writeByte(byte b) {
		return new byte[] {
			b	
		};
	}

	public static byte[] writeShort(short v) {
		return new byte[] {
				 (byte)(0xff & (v >> 8)),
				 (byte)(0xff & v)
		};
	}
	
	public static byte[] writeBoolean(boolean b) {
		
		byte a;
		if(b)
			a = 1;
		else
			a = 0;
		
		return new byte[] {
			a
		};
	}
	
	public static byte[] writeInt(int v) {
		return new byte[] {
				(byte)(0xff & (v >> 24)),
				 (byte)(0xff & (v >> 16)),
				 (byte)(0xff & (v >>    8)),
				 (byte)(0xff & v)
		};
	}

	public static byte[] writeLong(long v) {
		return new byte[] {
				 (byte)(0xff & (v >> 56)),
				 (byte)(0xff & (v >> 48)),
				 (byte)(0xff & (v >> 40)),
				 (byte)(0xff & (v >> 32)),
				 (byte)(0xff & (v >> 24)),
				 (byte)(0xff & (v >> 16)),
				 (byte)(0xff & (v >>  8)),
				 (byte)(0xff & v)
		};
	}
	
	

	public static byte[] writeFloat(float v) {
		return writeInt(Float.floatToIntBits(v));
	}
	public static byte[] writeDouble(double v) {
		return writeLong(Double.doubleToLongBits(v));
	}
	
	public static byte[] writeBytes(byte[][] da) {
		
		int size = 0;
		
		for(byte[] a : da)
			size += a.length;
		
		byte[] r = new byte[size];
		
		int i = 0;
		for(byte[] a : da)
			for(byte b : a) {
				r[i] = b;
				i++;
			}
		return r;
	}
	
	public static byte[] writeList(List<Byte> v) {
		byte[] r = new byte[v.size()];
		for(byte b : v)
			r[v.indexOf(b)] = b;
		return r;
	}
	
	// Minecraft Specifics: (Thanks to netty.io)
	
	
	public static byte[] writeUTFPrefixedString(String s) {
		return writeBytes(new byte[][] {
				writeShort((short) s.length()),
				s.getBytes(StandardCharsets.UTF_8)
		}
		);
	}
	
	public static byte[] writeVarInt(int v) {
		ByteData r = new ByteData();
		
		byte part;
        while (true) {
            part = (byte) (v & 0x7F);
            v >>>= 7;
            if (v != 0) {
                part |= 0x80;
            }
            r.writeByte(part);
            if (v == 0) {
                break;
            }
        }
        
        return r.getBytes();
	}
	
	public static byte[] writeUnsignedVarInt(int v) {
		
		ArrayList<Byte> out = new ArrayList<Byte>();	
		
        while ((v & 0xFFFFFF80) != 0L) {
            out.add((byte) ((v & 0x7F) | 0x80));
            v >>>= 7;
        }
        out.add((byte) (v & 0x7F));
        
        return writeList(out);
	}
	
	public static byte[] writeSignedVarInt(int v) {
        return writeUnsignedVarInt((v << 1) ^ (v >> 31));
	}
}

