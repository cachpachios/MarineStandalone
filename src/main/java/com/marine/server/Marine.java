///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// MarineStandalone is a minecraft server software and API.
// Copyright (C) IntellectualSites (marine.intellectualsites.com)
//
// This program is free software; you can redistribute it and/or modify
// it under the terms of the GNU General Public License as published by
// the Free Software Foundation; either version 2 of the License, or
// (at your option) any later version.
//
// This program is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
// GNU General Public License for more details.
//
// You should have received a copy of the GNU General Public License along
// with this program; if not, write to the Free Software Foundation, Inc.,
// 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

package com.marine.server;

import com.marine.Logging;
import com.marine.StandaloneServer;
import com.marine.game.scheduler.Scheduler;
import com.marine.player.Player;
import com.marine.world.World;

import java.util.Collection;
import java.util.UUID;

/**
 * Created 2014-12-02 for MarineStandalone
 *
 * @author Citymonstret
 */
public class Marine {

    protected static MarineServer server;
    protected static StandaloneServer standaloneServer;

    /**
     * Get all players
     *
     * @return List containing all players
     */
    public static Collection<Player> getPlayers() {
        return server.getPlayers();
    }

    /**
     * Get a player based on the username
     *
     * @param username Username
     * @return Player
     */
    public static Player getPlayer(String username) {
        return null;
    }

    /**
     * @param uuid
     * @return
     */
    public static Player getPlayer(UUID uuid) {
        for (Player player : getPlayers())
            if (player.getUUID().equals(uuid))
                return player;
        return null;
    }

    public static World getWorld(String name) {
        return server.getWorld(name);
    }

    public static String getMOTD() {
        return server.getMOTD();
    }

    public static MarineServer getServer() {
        return server;
    }

    public static void setServer(MarineServer marine) {
        if (server != null) {
            throw new RuntimeException("Cannot replace the MarineServer");
        }
        server = marine;
    }

    public static void stop() {
        standaloneServer.stop();
    }

    public static void setStandalone(StandaloneServer s) {
        if (standaloneServer != null) {
            throw new RuntimeException("Cannot replace the StandaloneServer");
        }
        standaloneServer = s;
    }

    public static int getMaxPlayers() {
        return server.getMaxPlayers();
    }

    public static Scheduler getScheduler() {
        return getServer().getServer().getScheduler();
    }

    public static void broadcastMessage(String string) {
        for (Player player : getPlayers()) {
            player.sendMessage(string);
        }
        Logging.getLogger().log(string);
    }

    public static Player getPlayer(short uid) {
        return getServer().getPlayer(uid);
    }

}
