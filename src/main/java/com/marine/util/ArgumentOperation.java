package com.marine.util;

/**
 *	Alike Java 8's Consumer class but rewritten for java 7/6 users by Fozie
 *
 *  @author Fozie
 */
public interface ArgumentOperation<T> {
	
    /**
     * Perform this action for on object
     *
     * @param Type object to perform the action on
     */
	 void accept(T t);
}
