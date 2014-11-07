package com.marineapi.net;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class ByteData {
	
	protected List<Byte> bytes;
	
	protected int writerPos;
	
	public ByteData(byte[] bytes) {
		this.bytes = new ArrayList<Byte>();
		
		for(byte b : bytes)
			this.bytes.add(b);
	}
	
	public ByteData() {
		this.bytes = new ArrayList<Byte>();
	}

	public void writeByte(byte... b) {
		for(byte by : b)
			bytes.add(by);
	}
	
	public boolean readBoolean() {
		if(bytes.size() < 1)
			return false;
		
		byte b = bytes.get(writerPos);
		writerPos++;
		
		if(b == 0)
			return false;
		else
			return true;
	}
	
	public byte readByte() {
		
		if(bytes.size() < 1)
			return 0;
		
		byte b = bytes.get(writerPos);
		writerPos++;
		return b;
	}
	
	public short readShort() {
		if(bytes.size() < 2)
			return 0;

		byte a = bytes.get(writerPos);
		writerPos++;
		byte b = bytes.get(writerPos);
		writerPos++;
		
		return (short)((a << 8) | (b & 0xff));
	}
	
	public int readUnsignedShort() {
		if(bytes.size() < 2)
			return 0;

		byte a = bytes.get(writerPos);
		writerPos++;
		byte b = bytes.get(writerPos);
		writerPos++;
		
		return (((a & 0xff) << 8) | (b & 0xff));
	}
	
	public int readInt() {
		
		if(bytes.size() < 4)
			return 0;		
		
		byte a = bytes.get(writerPos);
		writerPos++;
		byte b = bytes.get(writerPos);
		writerPos++;
		byte c = bytes.get(writerPos);
		writerPos++;
		byte d = bytes.get(writerPos);
		writerPos++;
		
		return  (((a & 0xff) << 24) | ((b & 0xff) << 16) |
				  ((c & 0xff) << 8) | (d & 0xff));
		
	}
	
	public long readLong() {
		
		if(bytes.size() < 8)
			return 0;		
		
		byte a = bytes.get(writerPos);
		writerPos++;
		byte b = bytes.get(writerPos);
		writerPos++;
		byte c = bytes.get(writerPos);
		writerPos++;
		byte d = bytes.get(writerPos);
		writerPos++;
		byte e = bytes.get(writerPos);
		writerPos++;
		byte f = bytes.get(writerPos);
		writerPos++;
		byte g = bytes.get(writerPos);
		writerPos++;
		byte h = bytes.get(writerPos);
		writerPos++;
		
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
		
		byte a = bytes.get(writerPos);
		writerPos++;
		byte b = bytes.get(writerPos);
		writerPos++;
		
		return (char)((a << 8) | (b & 0xff));
	};
	
	public byte[] readAllBytes() {
		byte[] a = new byte[bytes.size()];
		int i = 0;
		for(byte b : bytes) {
			a[i] = b;
			writerPos++;
			
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
                break;
            }
        }
        return out;
    }
	
	public byte[] readBytes(int amt) {
		byte[] r = new byte[amt];
		int i = 0;
		while(amt < i) {
			r[i] = bytes.get(writerPos);
			writerPos++;
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
	
	public int getWriterPos() {
		return writerPos;
	}
	
	public int getRemainingBytes() {
		return bytes.size() - writerPos;
	}
}