package com.marine.util;

import java.util.Collection;

/**
 * Created 2014-12-07 for MarineStandalone
 *
 * @author Citymonstret
 */
public class StringUtils {

    public static String join(Collection e, String s) {
        StringBuilder builder = new StringBuilder();
        for(Object o : e) {
            builder.append(o).append(s);
        }
        return builder.toString();
    }

}
