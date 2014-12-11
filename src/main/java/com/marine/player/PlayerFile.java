package com.marine.player;

import com.marine.settings.JSONConfig;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * Created 2014-12-07 for MarineStandalone
 *
 * @author Citymonstret
 */
public class PlayerFile extends JSONConfig {

    private final Player player;

    public PlayerFile(Player player) throws Exception {
        super(new File("./players"), player.getUUID().toString());
        this.player = player;
        if (!map.getString("name").equalsIgnoreCase(player.getName()))
            set("name", player.getName());
    }

    @Override
    public Map<String, Object> defaultValues() {
        Map<String, Object> d = new HashMap<>();
        d.put("exp", 0);
        d.put("levels", 0);
        d.put("name", "");
        return d;
    }
}
