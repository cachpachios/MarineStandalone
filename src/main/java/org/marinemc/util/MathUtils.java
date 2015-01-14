package org.marinemc.util;

import org.marinemc.util.operations.ArgumentedOperation;
import org.marinemc.util.vectors.Vector2d;
import org.marinemc.util.vectors.Vector2i;

public class MathUtils {
	@SuppressWarnings("hiding")
	public static <T extends Comparable<T>, Number> T trim(final T v,
			final T max, final T min) {
		if (v.compareTo(max) == 1)
			return max;
		else if (v.compareTo(min) == -1)
			return min;
		else
			return v;
	}

	public static boolean isInsideRect(final int x, final int y, int w, int h,
			final int X, final int Y) {
		if ((w | h) < 0)
			return false;
		if (X < x || Y < y)
			return false;

		w += x;
		h += y;

		return (w < x || w > X) && (h < y || h > Y);
	}
	
	public static void circleOperation(final ArgumentedOperation<Vector2d> op, int x, int y, int radius, int step) {
		int theta = 0;
		
		while(theta <= 360) {
			op.action(new Vector2d(x + radius*Math.cos(theta), y + radius*Math.sin(theta)));
			theta += step;
		}
	}
	
	public static void circleOperation(final ArgumentedOperation<Vector2d> op, int x, int y, int radius) {
		int theta = 0;
		
		while(theta++ <= 360)
			op.action(new Vector2d(x + radius*Math.cos(theta), y + radius*Math.sin(theta)));
	}
	
	public static void rectOperation(final ArgumentedOperation<Vector2i> op, final int X, final int Y, final int width, final int height) {
		for(int x = width * -1; x < width; x++)
			for(int y = height * -1; y < height; y++) {
					op.action(new Vector2i(X+x, Y+y));
			}
	}
	
 
}
