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
 * System Utilities
 *
 * @author Citymonstret
 */
public class SystemUtils {

	/**
	 * Get the java version as a number
	 *
	 * @return Java Specification Version Times 10 Minus 10
	 */
	public static int getJavaVersion() {
		try {
			return (int) (Double.parseDouble(System
					.getProperty("java.specification.version")) * 10) - 10;
		} catch (final Throwable e) {
			return -1;
		}
	}

	/**
	 * Get the system arch
	 *
	 * @return System Arch
	 */
	public static int getArch() {
		try {
			return Integer.parseInt(System.getProperty("sun.arch.data.model"));
		} catch (final Exception e) {
			return -1;
		}
	}
	
	/**
	 * Pases to System.arraycopy
	 * To bypass SecurityManager
	 * @param src
	 * @param srcPos
	 * @param dest
	 * @param destPos
	 * @param length
	 */
	public static void arraycopy(Object src, int srcPos, Object dest, int destPos, int length) {
		System.arraycopy(src, srcPos, dest, destPos, length);
	}
}
