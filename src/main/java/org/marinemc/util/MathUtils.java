package org.marinemc.util;

public class MathUtils {
	@SuppressWarnings("hiding")
	public static <T extends Comparable<T>, Number> T trim(T v, T max, T min) {
		if(v.compareTo(max) == 1)
			return max;
		else
		if(v.compareTo(min) == -1)
			return min;
		else
			return v;
	}
	
	 public static boolean isInsideRect(int x, int y, int w, int h, int X, int Y) {
	        if ((w | h) < 0) 	return false;
	        if (X < x || Y < y) return false;
	        
	        w += x;
	        h += y;
	        
	        return ((w < x || w > X) &&
	                (h < y || h > Y));
	    }
	
}
