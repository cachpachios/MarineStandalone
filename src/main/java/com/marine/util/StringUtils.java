///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// MarineStandalone is a minecraft server software and API.
// Copyright (C) IntellectualSites (marine.intellectualsites.com)
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

package com.marine.util;

import com.marine.player.Player;

import java.util.Arrays;
import java.util.Collection;

/**
 * Created 2014-12-07 for MarineStandalone
 *
 * @author Citymonstret
 */
public class StringUtils {

    /**
     * Join a collection as a string
     *
     * @param e Collection to join
     * @param s Separator String
     * @return E.cont#toString separated by s
     */
    public static String join(final Collection e, final String s) {
        if (e == null || e.size() == 0)
            return "";
        final StringBuilder builder = new StringBuilder();
        for (final Object o : e) {
            builder.append(o).append(s);
        }
        final String r = builder.toString();
        return r.substring(0, r.length() - s.length());
    }

    /**
     * Join an object array
     *
     * @param e Object array
     * @param s String separator
     * @return string
     * @see #join(java.util.Collection, String)
     */
    public static String join(final Object[] e, final String s) {
        return join(Arrays.asList(e), s);
    }

    /**
     * Format a string
     * <br>
     *
     * Currently replaces:
     * <ul>
     *     <li>Player - %plr</li>
     *     <li>String - %s</li>
     *     <li>Integer, Long - %d</li>
     *     <li>Boolean - %b</li>
     *     <li>Location - %loc</li>
     *     <li>Position: %pos</li>
     * </ul>
     *
     * @param s String containing placeholders
     * @param os Objects to replace with
     *
     * @return replaced string
     */
    public static String format(String s, final Object... os) {
        String r;
        for (final Object o : os) {
            try {
                if (o instanceof String)
                    r = "s";
                else if (o instanceof Integer || o instanceof Long)
                    r = "d";
                else if (o instanceof Float || o instanceof Double)
                    r = "f";
                else if (o instanceof Boolean || o instanceof Byte)
                    r = "b";
                else if (o instanceof Player)
                    r = "plr";
                else if (o instanceof Location)
                    r = "loc";
                else if (o instanceof Position)
                    r = "pos";
                else
                    continue;
                s = s.replaceFirst("%" + r, o.toString());
            } catch (Throwable ignored) {
            }
        }
        return s;
    }
}
