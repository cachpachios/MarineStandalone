package com.marine.util;

import com.marine.player.Player;

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
    	if(e == null || e.size() == 0)
    		return "";
        StringBuilder builder = new StringBuilder();
        for (Object o : e) {
            builder.append(o).append(s);
        }
        String r = builder.toString();
        return r.substring(0, r.length() - s.length());
    }

    public static String format(String s, Object ... os) {
        String r;
        for(Object o : os) {
            try {
                if(o instanceof String)
                    r = "s";
                else if(o instanceof Integer || o instanceof Long)
                    r = "d";
                else if(o instanceof Float || o instanceof Double)
                    r = "f";
                else if(o instanceof Boolean || o instanceof Byte)
                    r = "b";
                else if(o instanceof Player)
                    r = "plr";
                else if(o instanceof Location)
                    r = "loc";
                else if(o instanceof Position)
                    r = "pos";
                else
                    continue;
                s = s.replaceFirst("%" + r, o.toString());
            } catch(Throwable ignored) {}
        }
        return s;
    }
}
