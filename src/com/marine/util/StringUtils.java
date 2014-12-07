package com.marine.util;

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
    public static String join(Collection e, String s) {
        StringBuilder builder = new StringBuilder();
        for (Object o : e) {
            builder.append(o).append(s);
        }
        String r = builder.toString();
        return r.substring(0, r.length() - s.length());
    }

}
