package com.marine.game;

import com.marine.net.play.clientbound.PlayerListHeaderPacket;
import com.marine.net.play.clientbound.PlayerListItemPacket;
import com.marine.player.Player;

/**
 * Created 2014-12-05 for MarineStandalone
 *
 * @author Citymonstret
 */
public class TablistManager {

    private static TablistManager instance;

    public static TablistManager getInstance() {
        if(instance == null) instance = new TablistManager();
        return instance;
    }

    public TablistManager() {}

    public void setHeaderAndFooter(final String header, final String footer, final Player player) {
        player.getClient().sendPacket(new PlayerListHeaderPacket(header, footer));
    }

    public void addItem(final Player toAdd, final Player affected) {
        affected.getClient().sendPacket(new PlayerListItemPacket(PlayerListItemPacket.Action.ADD_PLAYER, toAdd));
    }

    public void removeItem(final Player toRemove, final Player affected) {
        affected.getClient().sendPacket(new PlayerListItemPacket(PlayerListItemPacket.Action.REMOVE_PLAYER, toRemove));
    }
}
