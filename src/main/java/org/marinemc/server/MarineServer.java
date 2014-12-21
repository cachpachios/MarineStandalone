///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// MarineStandalone is a minecraft server software and API.
// Copyright (C) MarineMC (marinemc.org)
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

package org.marinemc.server;

import org.marinemc.events.Event;
import org.marinemc.game.PlayerManager;
import org.marinemc.game.command.CommandSender;
import org.marinemc.player.Gamemode;
import org.marinemc.player.Player;
import org.marinemc.plugins.PluginLoader;
import org.marinemc.util.Base64Image;
import org.marinemc.world.Difficulty;
import org.marinemc.world.World;

import java.io.File;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

/**
 * MarineServer interface
 *
 * @author Citymonstret
 * @author Fozie
 */
public interface MarineServer {

    /**
     * Get the default gamemode
     *
     * @return default gamemode
     */
    public Gamemode getDefaultGamemode();

    public void setDefaultGamemode(Gamemode gamemode);

    public Difficulty getDefaultDifficulty();

    public void setDefaultDifficulty(Difficulty difficulty);

    public String getMotd();

    public void setMotd(String motd);

    /**
     * Get the player manager
     *
     * @return player manager
     */
    public PlayerManager getPlayerManager();

    /**
     * Get the console command sender
     *
     * @return internal command sender (console)
     */
    public CommandSender getConsoleSender();

    /**
     * Get the plugin folder
     *
     * @return Plugin Folder
     */
    public File getPluginFolder();

    /**
     * Load and enable all plugins in the plugins folder
     */
    public void loadPlugins();

    /**
     * Get the plugin loader (handler)
     *
     * @return Plugin Loader
     */
    public PluginLoader getPluginLoader();

    /**
     * Get the server favicon
     *
     * @return Server Favicon
     */
    public Base64Image getFavicon();

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
     * Call an event
     *
     * @param event Event to call
     */
    public void callEvent(final Event event);

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
