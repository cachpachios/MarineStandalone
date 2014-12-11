package com.marine.events;

import com.marine.events.MarineEvent;
import com.marine.player.Player;

/**
 * Created 2014-12-11 for MarineStandalone
 *
 * @author Citymonstret
 */
public class PlayerEvent extends MarineEvent {

    private final Player player;

    public PlayerEvent(final Player player, final String name) {
        super("player_event:" + name);
        this.player = player;
    }

    final public Player getPlayer() {
        return this.player;
    }
}
