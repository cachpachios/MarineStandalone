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

package org.marinemc.io.data;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

/**
 * Memory efficient nibble array allways recommended to use this in any case
 *
 * @author Fozie
 */
public class MemoryEfficientNibbleArray implements NibbleArray { // TODO WIP

	private final List<Byte> values;

	public MemoryEfficientNibbleArray() {
		values = new ArrayList<Byte>();
	}

	@Override
	public boolean add(final Byte e) {
		return false;
	}

	@Override
	public void add(final int index, final Byte element) {
	}

	@Override
	public boolean addAll(final Collection<? extends Byte> c) {
		return false;
	}

	@Override
	public boolean addAll(final int index, final Collection<? extends Byte> c) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void clear() {
		values.clear();
	}

	@Override
	public boolean contains(final Object o) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean containsAll(final Collection<?> c) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Byte get(final int index) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int indexOf(final Object o) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean isEmpty() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Iterator<Byte> iterator() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int lastIndexOf(final Object o) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public ListIterator<Byte> listIterator() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ListIterator<Byte> listIterator(final int index) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean remove(final Object o) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Byte remove(final int index) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean removeAll(final Collection<?> c) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean retainAll(final Collection<?> c) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Byte set(final int index, final Byte element) {
		return null;
	}

	@Override
	public int size() {
		return values.size() * 2;
	}

	@Override
	public List<Byte> subList(final int fromIndex, final int toIndex) {
		return null;
	}

	@Override
	public Object[] toArray() {
		return values.toArray();
	}

	@Override
	public <T> T[] toArray(final T[] a) {
		return values.toArray(a);
	}

	@Override
	public byte[] toBytes() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean contains(final byte nibble) {
		// TODO Auto-generated method stub
		return false;
	}
}
