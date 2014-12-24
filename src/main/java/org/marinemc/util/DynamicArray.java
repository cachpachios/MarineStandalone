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

package org.marinemc.util;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.RandomAccess;

/**
 * 
 * A resizeable array,
 * Can include dublicates but not null pointers.
 * 
 * @author Fozie
 * @see ArrayList
 */
public class DynamicArray<T extends Object> implements RandomAccess,Serializable, Iterable<T> {
	private static final long serialVersionUID = 8961846873731105803L;
	private Object[] objects;
	/**
	 * The real size of the dynamic array (Non null objects inside)
	 */
	private int size;
	
	/**
	 * The buffer where the data is stored
	 */

	public DynamicArray(int presize) {
		objects = new Object[presize];
		size = 0;
	}
	
	@SuppressWarnings("unchecked")
	public T[] toArray() {
		return (T[]) Arrays.copyOf(objects, size);
	}
	
	public Object[] toObjArray() {
        return Arrays.copyOf(objects, size);
	}
	
	public void add(T object) {
		allocateSize(size + 1);
		objects[++size] = object;
	}
	
	/**
	 * Return the object at the stated position
	 * @param pos The position of the object
	 * @return The object at the stated position
	 */
	@SuppressWarnings("unchecked")
	public T get(final int pos) {
		if(pos > size)
			return null;
		else
			return (T) objects[pos];
	}
	
	/**
	 * Trim the length of the allocated buffer to size+1
	 */
	public void trim() {
		trim(1);
	}
	/**
	 * Trim the length of the allocated buffer to size+offset
	 * @param offset The amount of space that should be allocated extra.
	 */
	public void trim(final int offset) {
		objects = Arrays.copyOf(objects, size+offset);
	}
	
	/**
	 * Make sure that 'size' slots is avalible
	 * @param size The target size of the buffer
	 */
	public void allocateSize(int size) {
		if(size > objects.length)
			objects = Arrays.copyOf(objects, size);
	}
	
	/**
	 * Get the total size of objects inside this array
	 * @return The size of this array
	 */
	public int size() {
		return size;
	}
	
	/**
	 * Get the allocated space of the buffer
	 * @return The buffers length
	 */
	public int getAllocatedSpace() {
		return objects.length;
	}
	
	/**
	 * Check if a object exists inside this array
	 * 
	 * @param object The object to check if equal to
	 * @return If any equal object is inside the array
	 */
	public boolean contains(T object) {
		for(int i = 0; i < size; i++)
			if(objects[i].equals(object))
				return true;
		return false;
	}
	
	/**
	 * Check if a compearable object exists in this array
	 * @param object The object to compear to
	 * @return If any compearable object is inside the array
	 */
	@SuppressWarnings("unchecked")
	public <E extends Comparable<T>> boolean contains(E object) {
		for(int i = 0; i < size; i++)
			if(object.compareTo((T)objects[i]) == 0)
				return true;
		return false;
	}

	@Override
	public Iterator<T> iterator() {
		return new DynamicIterator();
	}

	public boolean isEmpty() {
		return size == 0;
	}

	public void clear() {
        for (int i = 0; i < size; i++)
            objects[i] = null;

        size = 0;
        trim(10);
	}

    public int indexOf(Object obj) {
            for (int i = 0; i < size; i++)
                if (obj.equals(objects[i]))
                    return i;
        return -1;
    }

    public void remove(int index) {
        int numMoved = size - index - 1;
        if (numMoved > 0)
            System.arraycopy(objects, index+1, objects, index,
                             numMoved);
        objects[--size] = null;

    }

	public void remove(Object obj) {
		int i = indexOf(obj);
		if(i != -1)
			remove(i);
	}

	private final class DynamicIterator implements Iterator<T> {

		private int pos;

		@Override
		public boolean hasNext() {
			return pos != size;
		}

		@Override
		public T next() {
			return get(++pos);
		}

		@Override
		public void remove() {
			// TODO Implement
		}

	}
}
