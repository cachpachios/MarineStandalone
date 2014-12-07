package com.marine.player;

import com.marine.settings.JSONConfig;
import com.marine.util.Location;
import com.marine.world.World;

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
        if (map.isNull("username") || !map.getString("username").equals(player.getName())) {
            if (!map.isNull("username")) map.remove("username");
            map.put("username", player.getName());
        }
        if (map.isNull("exp") && map.isNull("levels")) {
            map.put("exp", 0d);
            map.put("levels", 0);
        }
        if (map.isNull("location")) {
            map.put("location", new Location(new World("world"), 0d, 0d, 0d).toJSONObject());
        }
    }

    public void set(String s, Object o) {
        if (!map.isNull(s))
            map.remove(s);
        try {
            map.put(s, o);
        } catch (Exception e) {
            e.printStackTrace();
        }
        saveFile();
        read();
    }
}
