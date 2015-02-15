package org.marinemc.util;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * A single iterator that iterates other iterators
 * 
 * Created 2015-2-15 for MarineStandalone
 *
 * @author Fozie
 */
public class JoinedIterator<T> implements Iterator<T>{

	final Iterator<T>[] its;
	
	public JoinedIterator(final Iterator<T>[] its) {
		this.its = its;
	}
	
	@Override
	public boolean hasNext() {
		boolean x = false;
		
		for(Iterator<T> i : its) {
			if(i.hasNext())
				x = true;
		}
		
		return x;
	}

	@Override
	public T next() {
		for(Iterator<T> i : its) {
			if(i.hasNext())
				return i.next();
		}
		
		throw new NoSuchElementException();
	}

}
