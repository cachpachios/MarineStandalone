package com.intellectualsites.marine.utils;

import org.marinemc.game.permission.Permission;

/**
 * Created 2014-12-26 for MarineStandalone
 *
 * @author Citymonstret
 */
public class PermissionManager {

    private static Permission JOIN_FULL = new Permission("network.join_full");

    public static Permission[] getVIPSuite() {
        return new Permission[] { JOIN_FULL };
    }

    public static Permission[] getPerms() {
        return new Permission[] { JOIN_FULL };
    }
}
