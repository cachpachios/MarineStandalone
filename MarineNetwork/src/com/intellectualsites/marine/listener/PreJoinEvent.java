package com.intellectualsites.marine.listener;

import com.intellectualsites.marine.utils.VIPUtil;
import org.marinemc.events.EventListener;
import org.marinemc.events.EventPriority;
import org.marinemc.events.standardevents.PreLoginEvent;
import org.marinemc.game.permission.Groups;
import org.marinemc.game.player.Player;
import org.marinemc.server.Marine;

/**
 * Created 2014-12-26 for MarineStandalone
 *
 * @author Citymonstret
 */
public class PreJoinEvent extends EventListener<PreLoginEvent> {

    public PreJoinEvent() {
        super(EventPriority.HIGH);
    }

    public void listen(final PreLoginEvent event) {
        final Player p = event.getPlayer();
        if (Marine.getPlayers().size() >= Marine.getMaxPlayers() && (!VIPUtil.instance().isVIP(p) && !p.getGroup().equals(Groups.ADMIN))) {
            event.setAllowed(false);
            event.setMessage("The server is full");
        }
    }
}
