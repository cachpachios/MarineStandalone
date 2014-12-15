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

/**
 * Random, literally Random, utility class
 */
public class Rand {

	public static boolean randomBoolean() {
		return Math.random() < .5;
	}

	public static int randomInteger(final int maxValue) {
		return (int) (Math.random() * maxValue);
	}

	public static int randomInteger() {
		return (int)(Math.random()*Integer.MAX_VALUE);
	}
	
	public static float randomFloat() {
		return (float) randomDouble();
	}
	
	public static double randomDouble() {
		return Math.random();
	}

	public static Object randomObject(final Object[] o) {
		return o[(int) (Math.random() * o.length)];
	}
}
