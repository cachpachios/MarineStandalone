package com.intellectualsites.marine.listener;

import com.intellectualsites.marine.object.groups.VIP;
import com.intellectualsites.marine.utils.VIPUtil;
import org.marinemc.events.EventListener;
import org.marinemc.game.permission.Group;
import org.marinemc.game.permission.Groups;
import org.marinemc.game.player.Player;
import org.marinemc.util.StringUtils;

/**
 * Created 2014-12-26 for MarineStandalone
 *
 * @author Citymonstret
 */
public class JoinEvent extends EventListener<org.marinemc.events.standardevents.JoinEvent> {

    private boolean validGroup(final Group group) {
        return group instanceof VIP || group.equals(Groups.ADMIN);
    }

    public void listen(final org.marinemc.events.standardevents.JoinEvent event) {
        final Player player = event.getPlayer();
        if (VIPUtil.instance().isVIP(player)) {
            player.setGroup(VIPUtil.getVIPGroup());
        }
        if (validGroup(player.getGroup())) {
            event.setJoinMessage(StringUtils.format("[{0}]{1} joined the game", player.getGroup(), player));
        }
    }

}
