package org.marinemc.util;

public class Rand {
	public static boolean randomBoolean() {
		if((int)Math.random() == 1)
			return true;
		else
			return false;
	}
	
	public static int randomInteger(int maxValue) {
		return (int)(Math.random()*maxValue);
	}
	
	public static int randomInteger() {
		return (int)(Math.random()*Integer.MAX_VALUE);
	}
	
	public static float randomFloat() {
		return (float)(Math.random());
	}
	
	public static double randomDouble() {
		return Math.random();
	}
}
