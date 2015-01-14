package com.intellectualsites.marine.utils;

import com.intellectualsites.marine.object.groups.VIP;
import org.marinemc.game.permission.Group;
import org.marinemc.game.player.Player;

/**
 * Created 2014-12-26 for MarineStandalone
 *
 * @author Citymonstret
 */
public class VIPUtil {

    private final Group VIP;

    private static VIPUtil instance;
    public static VIPUtil instance() {
        if (instance == null) {
            instance = new VIPUtil();
        }
        return instance;
    }

    public VIPUtil() {
        this.VIP = new VIP();
    }

    public static Group getVIPGroup() {
        return instance().VIP;
    }

    public boolean isVIP(final Player p) {
        return true;
    }


}
