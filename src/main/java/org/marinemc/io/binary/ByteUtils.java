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

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * Diffrent utils for array manipulation of byte arrays
 * 
 * @author Fozie
 *
 */
public class ByteUtils {

    public static List<Byte> asWrappedList(final byte[] array) {
        final List<Byte> result = new ArrayList<>(array.length);
        for (byte anArray : array) result.add(anArray);
        return result;
	}

    public static Byte[] wrap(final byte[] array) {
        if (array == null)
            return null;
        else if (array.length == 0)
            return new Byte[]{};

        final Byte[] result = new Byte[array.length];
        for (int i = 0; i < array.length; i++) {
            result[i] = array[i];
        }
        return result;
    }
	
	public static byte[] merge(byte[]... data) {
		int size = 0;
		for(byte[] a : data) 
			size += a.length;
		
		byte[] r = new byte[size];

		for(byte[] a : data)
			combine(r,a);
	
		return r;
	}
	
    public static byte[] combine(byte[] array1, byte[] array2) {
        if (array1 == null) {
            return copy(array2);
        } else if (array2 == null) {
            return copy(array1);
        }
        byte[] joinedArray = new byte[array1.length + array2.length];
        System.arraycopy(array1, 0, joinedArray, 0, array1.length);
        System.arraycopy(array2, 0, joinedArray, array1.length, array2.length);
        return joinedArray;
    }
   
    public static byte[] copy(byte[] array) {
        if (array == null) {
            return null;
        }
        return array.clone();
    }
    
    public static byte[] unwrap(final Byte[] array) {
        if (array == null)
            return null;
        else if (array.length == 0)
            return new byte[]{};

        final byte[] result = new byte[array.length];
        for (int i = 0; i < array.length; i++) {
            Byte b = array[i];
            result[i] = (b == null ? 0 : b);
        }
        return result;
    }
    
    public static byte[] putLast(final byte[] input, final byte[] data) {
    	byte[] r = new byte[data.length + input.length];
        System.arraycopy(data, 0, r, 0, data.length);
        System.arraycopy(input, 0, r, data.length, input.length);
    	return r;
    }
    
    public static byte[] putFirst(final byte[] input, final byte[] data) {
    	byte[] r = new byte[data.length + input.length];
        System.arraycopy(input, 0, r, 0, input.length);
        System.arraycopy(data, 0, r, input.length, data.length);
    	return r;
    }
    
    public static byte[] extendArray(final byte[] input, final int offset) {
    	return resize(input, input.length + offset);
    }
    
    public static byte[] resize(final byte[] input, final int length) {
    	byte[] r = new byte[length];
    	System.arraycopy(input, 0, r, 0, Math.min(input.length, length));
    	return r;
    }
    
    public static byte[] shift(final byte[] input, final int amount) {
    	byte[] r = new byte[input.length + amount];
    	System.arraycopy(input, 0, r, amount, Math.min(input.length, r.length));
    	return r;
    }
    
    public static String readUTF8Short(final ByteInput v) {
    	return new String(v.readBytes(v.readShort()));
    }
    
    public static String readUTF8VarInt(final ByteInput v) {
    	return new String(v.readBytes(v.readVarInt()));
    }
    
    public static byte[] singleton(byte b) {
    	return new byte[] {b};
    }
    
    public static byte[] VarInt(int v) {
        ArrayList<Byte> r = new ArrayList<Byte>();

        byte part;
        while (true) {
            part = (byte) (v & 0x7F);
            v >>>= 7;
            if (v != 0) {
                part |= 0x80;
            }
            r.add(part);
            if (v == 0) {
                break;
            }
        }

        return unwrap(r.toArray(new Byte[r.size()]));
    }
}
