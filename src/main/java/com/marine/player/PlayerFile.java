package com.marine.player;

import com.marine.settings.JSONConfig;

import java.io.File;

/**
 * Created 2014-12-07 for MarineStandalone
 *
 * @author Citymonstret
 */
public class PlayerFile extends JSONConfig {

    public PlayerFile(Player player) throws Exception {
        super(new File("./players"), player.getUUID().toString());
        super.read();
        // TODO: Major remake
    }

    public void set(String s, Object o) {
        if (!map.isNull(s))
            map.remove(s);
        try {
            map.put(s, o);
        } catch (Exception e) {
            e.printStackTrace();
        }
        read();
    }
}
