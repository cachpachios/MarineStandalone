package com.marine.io.data;

import java.util.UUID;

import com.marine.util.Hacky;
import com.marine.util.Unsafe;

/**
* Can be used in some cases to save a bitmap or UUID as a single number
* Size is 128bits(16bytes) stored as 4 integers.
*
* @author Fozie
*/ 
@Hacky @Deprecated @Unsafe
public class Extream extends Number implements Comparable<Number>, Byteable {
	
	final static long LONG_MASK = 0xffffffffL;
	
	private static final long serialVersionUID = 7108749761663713482L;
	
	private int a,b,c,d;

	public Extream(int a, int b, int c, int d) {
		this.a = a;
		this.b = b;
		this.c = c;
		this.d = d;
	}
	
	public Extream(UUID uuid) {
		this(uuid.getLeastSignificantBits(), uuid.getMostSignificantBits());
	}
	
	//TODO: Multiplication, Divitation etc;
	
	public Extream(long a, long b) {
		this((int)(a >> 32),(int) a, (int)(b >> 32),(int) b); 
	}
	
	@Override
	public double doubleValue() {
		return (((long)getFirstLong()) << 64) | (getSecoundLong());
	}

	@Override
	public float floatValue() { 
		return (float) doubleValue();
	}

	@Override
	public int intValue() {
		return a*b*c*d;
	}

	@Override
	public long longValue() {
		return (((long)getFirstLong()) << 64) | (getSecoundLong());
	}
	
	public long getFirstLong() {
		return (((long)a) << 32) | (b & 0xffffffffL);
	}	
	
	public long getSecoundLong() {
		return (((long)c) << 32) | (d & 0xffffffffL);
	}
	
	public int compareTo(Extream e) {
		if(e.a == a && e.b == b && e.c == c && e.d == d)
			return 0;

		int[] value = asInts();
		int[] bval = e.asInts();
		for (int i = 0, j = 0; i < 4; i++, j++) {
			int b1 = value[i] + 0x80000000;
		    int b2 = bval[j]  + 0x80000000;
		    if (b1 < b2)
		    	return -1;
		    if (b1 > b2)
		        return 1;
		}
		return 0;
	}
	
	void add(Extream addend) {
		int x = 4;
		int y = 4;
		int[] result = new int[4];
		
		int[] value = asInts();
		
		int rstart = result.length-1;
		long sum;
		long carry = 0;
		
		// Add common parts of both numbers
		while(x>0 && y>0) {
		    x--; y--;
		    sum = (value[x] & LONG_MASK) +
		        (addend.asInts()[y] & LONG_MASK) + carry;
		    result[rstart--] = (int)sum;
		    carry = sum >>> 32;
		}
		
		while(x>0) {
		    x--;
		    if (carry == 0 && result == value && rstart == (x))
		        return;
		    sum = (value[x] & LONG_MASK) + carry;
		    result[rstart--] = (int)sum;
		    carry = sum >>> 32;
		}
		while(y>0) {
		    y--;
		    sum = (addend.asInts()[y] & LONG_MASK) + carry;
		    result[rstart--] = (int)sum;
		    carry = sum >>> 32;
		}
		
		if (carry > 0) { // Result must grow in length
		        int temp[] = new int[4];
		        // Result one word longer from carry-out; copy low-order
		        // bits into new result.
		        System.arraycopy(result, 0, temp, 1, result.length);
		        temp[0] = 1;
		        result = temp;
		    } else {
		        result[rstart--] = 1;
		    }
		a = result[0];
		b = result[0];
		c = result[0];
		d = result[0];   
	}
	
	public int[] asInts() {
		return new int[] {a,b,c,d};
	}
	
	@Override
	public int compareTo(Number n) {
		// TODO:
		return -1;
	}
	
	public int hashCode() {
		return new int[] {a,b,c,d}.hashCode();
	}

	@Override
	public byte[] toBytes() {
		return new byte[] {
				ByteEncoder.writeInt(a)[0],
				ByteEncoder.writeInt(a)[1],
				ByteEncoder.writeInt(a)[2],
				ByteEncoder.writeInt(a)[3],
				
				ByteEncoder.writeInt(b)[0],
				ByteEncoder.writeInt(b)[1],
				ByteEncoder.writeInt(b)[2],
				ByteEncoder.writeInt(b)[3],
				
				ByteEncoder.writeInt(c)[0],
				ByteEncoder.writeInt(c)[1],
				ByteEncoder.writeInt(c)[2],
				ByteEncoder.writeInt(c)[3],
				
				ByteEncoder.writeInt(d)[0],
				ByteEncoder.writeInt(d)[1],
				ByteEncoder.writeInt(d)[2],
				ByteEncoder.writeInt(d)[3],
				
		};
	}

	
	
}
