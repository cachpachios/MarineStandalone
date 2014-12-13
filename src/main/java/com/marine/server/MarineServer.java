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

import com.marine.StandaloneServer;
import com.marine.events.Listener;
import com.marine.events.MarineEvent;
import com.marine.player.Player;
import com.marine.world.World;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

/**
 * Created 2014-12-02 for MarineStandalone
 *
 * MarineServer interface
 *
 * @author Citymonstret
 * @author Fozie
 */
public interface MarineServer {

    /**
     * Get the StandaloneServer instance
     *
     * @return instance
     */
    public StandaloneServer getServer();

    /**
     * Get all online players
     *
     * @return A list containing all players
     */
    public Collection<Player> getPlayers();

    /**
     * Get the current player count
     *
     * @return The current Player Count
     */
    public int getPlayerCount();

    /**
     * Get a player based on its uid
     *
     * @param uid uid
     * @return Null if not found, else the player
     */
    public Player getPlayer(final short uid);

    /**
     * Get a world based on its name
     *
     * @param name World Name
     * @return World if found, else null
     */
    public World getWorld(final String name);

    /**
     * Get all worlds
     *
     * @return A list containing all worlds
     */
    public List<World> getWorlds();

    /**
     * Get a player from its uuid
     *
     * @param uuid UUID
     * @return Player
     */
    public Player getPlayer(final UUID uuid);

    /**
     * Get a player based on its username
     *
     * @param username Username
     * @return Player
     */
    public Player getPlayer(final String username);

    /**
     * Register an event listener
     *
     * @param listener event listener
     */
    public void registerListener(final Listener listener);

    /**
     * UnRegister an event listener
     *
     * @param listener event listener
     */
    public void unregisterListener(final Listener listener);

    /**
     * Call an event
     *
     * @param event to call
     */
    public void callEvent(final MarineEvent event);

    /**
     * Get the current MOTD
     *
     * @return current MOTD
     */
    public String getMOTD();

    /**
     * Get the max player count
     *
     * @return max player count
     */
    public int getMaxPlayers();

    /**
     * Set the amount of max players
     *
     * @param n max players
     */
    public void setMaxPlayers(final int n);

}
