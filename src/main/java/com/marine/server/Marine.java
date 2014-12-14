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
import com.marine.game.chat.builder.Chat;
import com.marine.game.scheduler.Scheduler;
import com.marine.game.system.MarineSecurityManager;
import com.marine.player.Player;
import com.marine.util.Protected;
import com.marine.world.World;

import java.net.InetAddress;
import java.util.Collection;
import java.util.UUID;

/**
 * Static API Class - Fun stuff in here
 *
 * Static implementation of commonly used
 * methods. It wraps around the global instances
 * of:
 * StandaloneServer
 * MarineServer
 *
 * @author Citymonstret
 * @author Fozie
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
    public static Player getPlayer(final String username) {
        return null;
    }

    /**
     * Get a player based on its uid
     *
     * @param uuid player uuid
     * @return player or null
     */
    public static Player getPlayer(final UUID uuid) {
        for (final Player player : getPlayers())
            if (player.getUUID().equals(uuid))
                return player;
        return null;
    }

    /**
     * Get a world based on its name
     *
     * @param name World Name
     * @return World or null
     */
    public static World getWorld(final String name) {
        return server.getWorld(name);
    }

    /**
     * Get the current MOTD
     * @return Current MOTD
     */
    public static String getMOTD() {
        return server.getMOTD();
    }

    /**
     * Get the MarineServer
     * @return Marine Server
     */
    public static MarineServer getServer() {
        return server;
    }

    // IGNORE
    @Protected
    public static void setServer(final MarineServer marine) {
        // Security Check Start
        System.getSecurityManager().checkPermission(MarineSecurityManager.MARINE_PERMISSION);
        // Security Check end
        if (server != null) {
            throw new RuntimeException("Cannot replace the MarineServer");
        }
        server = marine;
    }

    /**
     * Stop the server
     */
    public static void stop() {
        standaloneServer.stop();
    }

    // IGNORE
    @Protected
    public static void setStandalone(final StandaloneServer s) {
        // Security Check Start
        System.getSecurityManager().checkPermission(MarineSecurityManager.MARINE_PERMISSION);
        // Security Check end
        if (standaloneServer != null) {
            throw new RuntimeException("Cannot replace the StandaloneServer");
        }
        standaloneServer = s;
    }

    /**
     * Get the max player number
     *
     * @return max players
     */
    public static int getMaxPlayers() {
        return server.getMaxPlayers();
    }

    /**
     * Get the scheduler class
     *
     * @return scheduler class
     */
    public static Scheduler getScheduler() {
        return getServer().getServer().getScheduler();
    }

    /**
     * Broadcast a message to all players
     *
     * @param string message
     */
    public static void broadcastMessage(final String string) {
        for (final Player player : getPlayers()) {
            player.sendMessage(string);
        }
        Logging.getLogger().log(string);
    }

    /**
     * Broadcast a message to all players
     *
     * @param chat message
     */
    public static void broadcastMessage(final Chat chat) {
        for (final Player player : getPlayers()) {
            player.sendMessage(chat);
        }
        Logging.getLogger().log("Raw Chat Sent: " + chat.toString());
    }

    /**
     * Get a player based on its uid
     *
     * @param uid Unique ID
     * @return Player or null
     */
    public static Player getPlayer(final short uid) {
        return getServer().getPlayer(uid);
    }

    /**
     * Check if a player is banned
     *
     * @param uuid Player UUID
     * @return boolean (banned)
     */
    public static boolean isBanned(final UUID uuid) {
        return false;
    }

    /**
     * Check if an IP is banned
     *
     * @param address IP
     * @return boolean (banned)
     */
    public static boolean isBanned(final InetAddress address) {
        return false;
    }
}
