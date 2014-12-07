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
        if(map.isNull("username") || !map.getString("username").equals(player.getName())) {
            if(!map.isNull("username")) map.remove("username");
            map.put("username", player.getName());
        }
        super.saveFile();
    }
}
