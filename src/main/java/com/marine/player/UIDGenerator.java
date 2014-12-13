package com.marine.player;

import java.util.HashMap;
import java.util.Map;

public class UIDGenerator {
	
	private Map<Integer, Short> UIDMap;
	private short nextUnassigned = Short.MIN_VALUE;
	
	public UIDGenerator() {
		UIDMap = new HashMap<Integer, Short>();
		nextUnassigned = Short.MIN_VALUE;
	}
	
	public short getUID(String username) { // Will return a UID for the player, if no one can be found will return Short.MIN_VALUE
		if(UIDMap.containsKey(username.hashCode()))
			return UIDMap.get(username.hashCode());
		
		short uid = ++nextUnassigned;
		try {
			while(UIDMap.containsKey(uid)) // Too make sure u get a unused uid
				uid = ++nextUnassigned;
		} catch(Exception e) {
			return Short.MIN_VALUE; // Error code
		}
		
		return uid;
		
	}
	
	private static UIDGenerator instance;
	public static UIDGenerator instance() {
		if(instance == null)
			instance = new UIDGenerator();
		return instance;
	}
}
