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
import java.util.Iterator;

/**
 * String Utility Class
 * (Replacement to apache, and sun)
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
     * Join a collection as a string
     *
     * @param e Collection to join
     * @param s Separator (when more than one item left)
     * @param l Separator (before last item)
     * @return Joined String
     */
    public static String join(final Collection e, final String s, final String l) {
        if (e == null || e.size() == 0)
            return "";
        final StringBuilder sb = new StringBuilder();
        final Iterator it = e.iterator();
        Object o;
        boolean first = true;
        for (; ; ) {
            o = it.next();
            if (!it.hasNext()) {
                if (first)
                    return o.toString();
                else
                    return sb.append(l).append(o).toString();
            }
            if (first)
                first = false;
            else
                sb.append(s);
            sb.append(o);
        }
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
     * <p>
     * Used to replace (%plr, %b) etc. Uses
     * {0}, {1} and so on instead...
     * </p>
     * @param s  String containing placeholders
     * @param os Objects to replace with
     * @return replaced string
     */
    public static String format(String s, final Object... os) {
        for (int x = 0; x < os.length; x++) {
            s = s.replace("{" + x + "}", os[x].toString());
        }
        return s;
    }

}
