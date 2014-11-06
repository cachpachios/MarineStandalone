package com.marineapi.net;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class ByteData {
	
	protected List<Byte> bytes;
	
	public ByteData(byte[] bytes) {
		this.bytes = new ArrayList<Byte>();
		
		for(byte b : bytes)
			this.bytes.add(b);
	}
	
	public boolean readBoolean() {
		if(bytes.size() < 1)
			return false;
		
		byte b = bytes.get(0);
		bytes.remove(0);
		
		if(b == 0)
			return false;
		else
			return true;
	}
	
	public byte readByte() {
		
		if(bytes.size() < 1)
			return 0;
		
		byte b = bytes.get(0);
		bytes.remove(0);
		return b;
	}
	
	public short readShort() {
		if(bytes.size() < 2)
			return 0;

		byte a = bytes.get(0);
		byte b = bytes.get(0);
	
		return (short)((a << 8) | (b & 0xff));
	}
	
	public int readUnsignedShort() {
		if(bytes.size() < 2)
			return 0;

		byte a = bytes.get(0);
		byte b = bytes.get(0);
	
		return (((a & 0xff) << 8) | (b & 0xff));
	}
	
	public int readInt() {
		
		if(bytes.size() < 4)
			return 0;		
		
		byte a = bytes.get(0);
		byte b = bytes.get(1);
		byte c = bytes.get(2);
		byte d = bytes.get(3);
		
		bytes.remove(0); bytes.remove(1); bytes.remove(2); bytes.remove(3);
		
		return  (((a & 0xff) << 24) | ((b & 0xff) << 16) |
				  ((c & 0xff) << 8) | (d & 0xff));
		
	}
	
	public long readLong() {
		
		if(bytes.size() < 8)
			return 0;		
		
		byte a = bytes.get(0);
		byte b = bytes.get(1);
		byte c = bytes.get(2);
		byte d = bytes.get(3);
		byte e = bytes.get(4);
		byte f = bytes.get(5);
		byte g = bytes.get(6);
		byte h = bytes.get(7);
		
		bytes.remove(0); bytes.remove(1); bytes.remove(2); bytes.remove(3);
		bytes.remove(4); bytes.remove(5); bytes.remove(6); bytes.remove(7);
		
		return  (((long)(a & 0xff) << 56) |
				  ((long)(b & 0xff) << 48) |
				  ((long)(c & 0xff) << 40) |
				  ((long)(d & 0xff) << 32) |
				  ((long)(e & 0xff) << 24) |
				  ((long)(f & 0xff) << 16) |
				  ((long)(g & 0xff) <<  8) |
				  ((long)(h & 0xff)));
	}
	
	public float readFloat() {
		return Float.intBitsToFloat(readInt());
	}
	
	public double readDouble() {
		return Double.longBitsToDouble(readLong());
	}
	
	public char readChar() { // To give control of character readings in updates
		return readCharacter();
	}
	
	private char readCharacter() {
		if(bytes.size() < 2)
			return 0;
		
		byte a = bytes.get(0);
		byte b = bytes.get(0);
		return (char)((a << 8) | (b & 0xff));
	};
	
	public byte[] readAllBytes() {
		byte[] a = new byte[bytes.size()];
		int i = 0;
		for(byte b : bytes) {
			a[i] = b;
			bytes.remove(b);
			i++;
		}
		
		return a;
 	}
	
	public byte[] getBytes() {
		byte[] a = new byte[bytes.size()];
		int i = 0;
		for(byte b : bytes) {
			a[i] = b;
			i++;
		}
		
		return a;
	}
	

	public int readVarInt(){
        int out = 0;
        int bytes = 0;
        byte in;
        while (true) {
            in = readByte();
            out |= (in & 0x7F) << (bytes++ * 7);
            if ((in & 0x80) != 0x80) {
            	append(in);
                break;
            }
        }
        return out;
    }
	
	public byte[] readBytes(int amt) {
		byte[] r = new byte[amt];
		int i = 0;
		while(amt < i) {
			r[i] = bytes.get(i);
			bytes.remove(i);
			i++;
		}
		
		return r;
	}
	
	public String readString() {
		int l = readUnsignedShort();
		return new String(readBytes(l), StandardCharsets.UTF_8);
	}
	
	public void writeToStream(OutputStream stream) {
		try {
			stream.write(getBytes());
		} catch (IOException e) {
		}
	}
	
	public void append(byte b) {
		bytes.add(0, b);
	}
	
}