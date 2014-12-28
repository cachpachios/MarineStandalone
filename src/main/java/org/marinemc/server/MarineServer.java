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
import org.marinemc.game.WorldManager;
import org.marinemc.game.command.CommandSender;
import org.marinemc.game.player.Player;
import org.marinemc.game.scheduler.Scheduler;
import org.marinemc.net.NetworkManager;
import org.marinemc.plugins.PluginLoader;
import org.marinemc.settings.JSONFileHandler;
import org.marinemc.util.Base64Image;
import org.marinemc.util.Location;
import org.marinemc.world.Difficulty;
import org.marinemc.world.Gamemode;
import org.marinemc.world.World;

import java.io.File;
import java.net.InetAddress;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

/**
 * MarineServer interface
 *
 * @author Citymonstret
 * @author Fozie
 */
@SuppressWarnings("unused")
public interface MarineServer {

    /**
     * See if the server is offline
     *
     * @return true if offline
     */
    public boolean isOfflineMode();

    /**
     * Set the offline moed
     *
     * @param n new Mode
     */
    public void setOfflineMode(boolean n);

    /**
     * Get the json file handler
     *
     * @return file handler
     */
    public JSONFileHandler getJsonFileHandler();

    /**
     * Get the settings folder
     *
     * @return settings folder
     */
    public File getSettingsFolder();

    /**
     * Get the storage folder
     *
     * @return storage folder
     */
    public File getStorageFolder();

    /**
     * Get the default gamemode
     *
     * @return default gamemode
     */
    public Gamemode getDefaultGamemode();

    /**
     * Set the default gamemode
     *
     * @param gamemode Default Gamemode
     */
    public void setDefaultGamemode(Gamemode gamemode);


    /**
     * Get the default difficulty
     *
     * @return default difficulty
     */
    public Difficulty getDefaultDifficulty();

    /**
     * Set the default difficulty
     *
     * @param difficulty default difficulty
     */
    public void setDefaultDifficulty(Difficulty difficulty);

    /**
     * Get the current MOTD
     *
     * @return current motd
     */
    public String getMotd();

    /**
     * Set the motd
     *
     * @param motd new motd
     */
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

    public Location getSpawnLocation();

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
     * Set the server favicon
     *
     * @param image New Favicon (Cannot be null)
     */
    public void setFavicon(final Base64Image image);

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

    /**
     * Get the network manager
     *
     * @return internal network manager
     */
    public NetworkManager getNetworkManager();

    /**
     * Get the scheduler
     *
     * @return internal scheduler
     */
    public Scheduler getScheduler();

    /**
     * Get the world manager
     *
     * @return internal world manager
     */
    public WorldManager getWorldManager();

    /**
     * Get the port the server is running on
     *
     * @return port
     */
    public int getPort();

    /**
     * Get the tickrate of the server
     * (server ticks every 1000 / tickrate ms)
     *
     * @return tickrate
     */
    public int getTickRate();

    /**
     * Stop the server
     */
    public void stop();

    /**
     * @deprecated
     */
    @Deprecated
    public boolean isStopping();

    /**
     * Check if a player is banned
     *
     * @param u Player to check for
     * @return true if the player is banned
     */
    public boolean isBanned(UUID u);

    /**
     * Check if an address is banned
     *
     * @param address Address to check for
     * @return true of the address is banned
     */
    public boolean isBanned(InetAddress address);

    /**
     * Get the binding address
     *
     * @return binding address, will default to localhost
     */
    public InetAddress getAddress();

    /**
     * Set the ban status of a player
     *
     * @param uuid Affected UUID
     * @param b    true to add, false to remove
     */
    public void setBanned(UUID uuid, boolean b);

    /**
     * Set the ban status of a network address
     *
     * @param address Affected Address
     * @param b true to add, false to remove
     */
    public void setBanned(InetAddress address, boolean b);

    /**
     * Check if the server is using a whitelist
     *
     * @return true if the server is using a whitelist
     */
    public boolean usingWhitelist();

    /**
     * Check if a player is whitelisted
     *
     * @param player Player
     * @return true if the player is whitelisted
     */
    public boolean isWhitelisted(Player player);

    /**
     * Set the whitelist mode of a player
     *
     * @param uuid UUID
     * @param b True to add, false to remove
     */
    public void setWhitelisted(UUID uuid, boolean b);
}
