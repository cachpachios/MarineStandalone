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

import org.marinemc.util.operations.ArgumentedOperation;
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

    public static int floor(double num) {
        final int numInt = (int) num;
        return numInt == num ? numInt : numInt - (int) (Double.doubleToRawLongBits(num) >>> 63);
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

	public static void circleOperation(final ArgumentedOperation<Vector2i> op, int x, int y, int radius, int step) {
		int theta = 0;
		
		while(theta <= 360) {
			op.action(new Vector2i((int) (x + radius * Math.cos(theta)), (int) (y + radius * Math.sin(theta))));
			theta += step;
		}
	}

	public static void circleOperation(final ArgumentedOperation<Vector2i> op, int x, int y, int radius) {
		int theta = 0;
		
		while(theta++ <= 360)
			op.action(new Vector2i((int) (x + radius * Math.cos(theta)), (int) (y + radius * Math.sin(theta))));
	}
	
	public static void rectOperation(final ArgumentedOperation<Vector2i> op, final int X, final int Y, final int width, final int height) {
		for(int x = width * -1; x < width; x++)
			for(int y = height * -1; y < height; y++) {
					op.action(new Vector2i(X+x, Y+y));
			}
	}
	
 
}
