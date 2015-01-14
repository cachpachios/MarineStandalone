package com.intellectualsites.marine.object.groups;

import com.intellectualsites.marine.utils.PermissionManager;
import org.marinemc.game.permission.Group;

/**
 * Created 2014-12-26 for MarineStandalone
 *
 * @author Citymonstret
 */
public class VIP extends Group {

    public VIP() {
        super("vip", "VIP", PermissionManager.getVIPSuite());
    }
}
