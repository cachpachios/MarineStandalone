package com.marine.io.data;

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
public class MemoryEfficientNibbleArray implements NibbleArray{ 
	
	private List<Byte> values;
	
	public MemoryEfficientNibbleArray() {
		this.values = new ArrayList<Byte>();
	}
	
	@Override
	public boolean add(Byte e) {
		return false;
	}

	@Override
	public void add(int index, Byte element) {
	}

	@Override
	public boolean addAll(Collection<? extends Byte> c) {
		return false;
	}

	@Override
	public boolean addAll(int index, Collection<? extends Byte> c) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void clear() {
		values.clear();
	}
	
	@Override
	public boolean contains(Object o) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean containsAll(Collection<?> c) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Byte get(int index) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int indexOf(Object o) {
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
	public int lastIndexOf(Object o) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public ListIterator<Byte> listIterator() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ListIterator<Byte> listIterator(int index) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean remove(Object o) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Byte remove(int index) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean removeAll(Collection<?> c) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean retainAll(Collection<?> c) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Byte set(int index, Byte element) {
		return null;
	}

	@Override
	public int size() {
		return values.size() * 2;
	}

	@Override
	public List<Byte> subList(int fromIndex, int toIndex) {
		return null;
	}

	@Override
	public Object[] toArray() {
		return values.toArray();
	}

	@Override
	public <T> T[] toArray(T[] a) {
		return values.toArray(a);
	}

	@Override
	public byte[] toBytes() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean contains(byte nibble) {
		// TODO Auto-generated method stub
		return false;
	}
}
