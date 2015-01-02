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

import java.util.Arrays;
import java.util.Collection;
import java.util.Map;

/**
 * Created 2014-12-28 for MarineStandalone
 *
 * @author Citymonstret
 */
public class Assert {

    public static <T> void notNull(T t) {
        if (t == null || (t instanceof String && t.equals(""))) {
            throw new NullPointerException("Unsupported null");
        }
    }

    public static <T> void compare(T t, T w) {
        if (!t.equals(w)) {
            throw new AssertionError("t != w");
        }
    }

    public static void notNull(Object... objects) {
        for (Object o : objects) {
            notNull(o);
        }
    }

    public static <T> boolean notEmpty(T t) {
        notNull(t);

        boolean b;
        if (t instanceof Collection) {
            b = ((Collection) t).isEmpty();
        } else if (t instanceof Map) {
            b = ((Map) t).isEmpty();
        } else {
            throw new IllegalArgumentException("T is not a collection or a map");
        }
        return true;
    }

    public static void contains(Object[] t, Object o) {
        contains(Arrays.asList(t), o);
    }

    public static <T> void contains(T t, Object o) {
        boolean c = false;
        if (t instanceof Map) {
            c = ((Map) t).containsKey(o);
        } else if (t instanceof Collection) {
            c = ((Collection) t).contains(o);
        } else {
            throw new IllegalArgumentException("T is not a collection or a map");
        }
        if (!c) {
            throw new AssertionError("t doesn't contain o");
        }
    }
}
