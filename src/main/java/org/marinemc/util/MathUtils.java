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
}
