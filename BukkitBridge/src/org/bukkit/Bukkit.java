package org.bukkit;

import com.marine.server.Marine;
import org.bukkit.org.bukkit.entity.Player;

import java.util.Collection;
import java.util.UUID;

/**
 * Created 2014-12-11 for MarineStandalone
 *
 * @author Citymonstret
 */
public class Bukkit {

    public static Collection<? extends Player> getOnlinePlayers() {
        return Marine.getPlayers();
    }

    public static int getMaxPlayers() {
        return Marine.getMaxPlayers();
    }

    public int broadcastMessage(String message) {
        Marine.broadcastMessage(message);
        return getOnlinePlayers().size();
    }

    public static Player getPlayer(String name) {
        return Marine.getPlayer(name);
    }

    public static Player getPlayerExact(String name) {
        return getPlayer(name);
    }

    public static Player getPlayer(UUID uuid) {
        return Marine.getPlayer(uuid);
    }


}
