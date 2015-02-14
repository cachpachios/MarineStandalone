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

import org.json.JSONObject;

import java.util.Arrays;
import java.util.Collection;
import java.util.Map;

/**
 * Created 2014-12-28 for MarineStandalone
 *
 * @author Citymonstret
 */
public class Assert {

	public static <T> void notNull(final T t) {
		if (t == null || t instanceof String && t.equals(""))
			throw new NullPointerException("Unsupported null");
	}

	public static <T> void compare(final T t, final T w) {
		if (!t.equals(w))
			throw new AssertionError("t != w");
	}

	public static void notNull(final Object... objects) {
		for (final Object o : objects)
			notNull(o);
	}

	public static <T> boolean notEmpty(final T t) {
		notNull(t);

		boolean b;
		if (t instanceof Collection)
			b = ((Collection) t).isEmpty();
		else if (t instanceof Map)
			b = ((Map) t).isEmpty();
		else
			throw new IllegalArgumentException("T is not a collection or a map");
		return true;
	}

	public static void contains(final Object[] t, final Object o) {
		contains(Arrays.asList(t), o);
	}

	public static <T> void equals(final T t, final Object o) {
		if (o == null || !o.getClass().equals(t.getClass()) || !t.equals(o)) {
			throw new AssertionError("o doesn't equal t");
		}
	}

    public static void AssertJSONChild(final JSONObject object, final String key) {
        requireFalse(object.isNull(key));
    }

	public static void requireTrue(boolean b) {
		equals(b, true);
	}

	public static void requireFalse(boolean b) {
		equals(b, false);
	}

	public static <T> void contains(final T t, final Object o) {
		boolean c = false;
		if (t instanceof Map)
			c = ((Map) t).containsKey(o);
		else if (t instanceof Collection)
			c = ((Collection) t).contains(o);
		else
			throw new IllegalArgumentException("T is not a collection or a map");
		if (!c)
			throw new AssertionError("t doesn't contain o");
	}
}
