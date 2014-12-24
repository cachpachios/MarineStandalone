///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// MarineStandalone is a minecraft server software and API.
// Copyright (C) MarineMC (marinemc.org)
//
// This program is free software; you can redistribute it and/or modify
// it under the terms of the GNU General Public License as published by
// the Free Software Foundation; either version 2 of the License, or
// (at your option) any later version.
//
// This program is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
// GNU General Public License for more details.
//
// You should have received a copy of the GNU General Public License along
// with this program; if not, write to the Free Software Foundation, Inc.,
// 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

package org.marinemc.io.binary;

import java.nio.charset.Charset;
import java.util.Iterator;

/**
 * ByteArray is a Stored reader with output and input functionality
 * 
 * Is able to expand automaticly!
 * But needs to be trimed to minimize.
 * 
 * @author Fozie
 */
public class ByteArray extends AbstractInput implements ByteOutput, StoredReader, Iterable<Byte> {

	private int size;
	
	private byte[] data;
	
	private int position = -1;
	
	public ByteArray(byte[] data) {
		this.data = data;
		size = data.length;
	}
	
	public ByteArray() {
		this.data = new byte[10];
		size = 0;
	}
	
	public ByteArray(int size) {
		this.data = new byte[size];
		size = 0;
	}
	
	@Override
	public byte readByte() {
		if(hasAnotherByte())
			return data[++position];
		else
			return 0;
	}

	public boolean hasAnotherByte() {
		return (position + 1) < size;
	}
	
	public int getPosition() {
		if(position != 0)
			return position;
		return 0;
	}
	
	public void ensureSpace(final int amount) {
		if(extraAllocation() < amount)
			data = ByteUtils.extendArray(data, amount - extraAllocation());
	}
	
	public void allocateAnotherByte() {
		data = ByteUtils.extendArray(data, 1);
	}
	
	public void trim() {
		trim(1);
	}
	
	public void trim(final int offset) {
		data = ByteUtils.resize(data, size+offset);
	}
	
	public int extraAllocation() {
		return data.length - size;
	}
	
	@Override
	public void writeByte(byte v) {
		ensureSpace(size+1);
		data[++size] = v;
	}

	public void write(byte... v) {
		data = ByteUtils.insert(data, v);
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
	
	@Override
	public Iterator<Byte> iterator() {
		return new ByteIterator();
	}
	
	private final class ByteIterator implements Iterator<Byte> {

		private int pos;
		
		@Override
		public boolean hasNext() {
			return pos != size;
		}

		@Override
		public Byte next() {
			return data[++pos];
		}

		@Override
		public void remove() {
			// TODO Implement
		}

	}
}
