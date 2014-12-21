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

import org.marinemc.Logging;
import org.marinemc.events.Event;
import org.marinemc.events.EventManager;
import org.marinemc.game.PlayerManager;
import org.marinemc.game.chat.ChatColor;
import org.marinemc.game.command.CommandProvider;
import org.marinemc.game.command.CommandSender;
import org.marinemc.game.command.ConsoleSender;
import org.marinemc.game.system.MarineSecurityManager;
import org.marinemc.player.Gamemode;
import org.marinemc.player.Player;
import org.marinemc.plugins.PluginLoader;
import org.marinemc.plugins.PluginManager;
import org.marinemc.settings.ServerSettings;
import org.marinemc.util.Base64Image;
import org.marinemc.world.Difficulty;
import org.marinemc.world.World;

import java.io.File;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

/**
 * Server implementation
 *
 * @author Citymonstret
 * @author Fozie
 */
public class Server implements MarineServer, CommandProvider {

    private final StandaloneServer server;
    private final PluginLoader pluginLoader;
    private final File pluginFolder;
    private final CommandSender console;
    private Base64Image image;
    private Gamemode gamemode;
    private Difficulty difficulty;
    private String motd;
    private int maxPlayers;

    public Server(final StandaloneServer server) {
        // Security Check Start
        System.getSecurityManager().checkPermission(MarineSecurityManager.MARINE_PERMISSION);
        // Security Check end
        this.server = server;
        this.pluginLoader = new PluginLoader(new PluginManager());
        this.pluginFolder = new File("./plugins");
        this.console = new ConsoleSender();
        this.gamemode = Gamemode.SURVIVAL;
        this.difficulty = Difficulty.PEACEFUL;
        this.motd = ChatColor.transform('&', ServerSettings.getInstance().motd);
        this.maxPlayers = 999;
    }

    @Override
    final public Gamemode getDefaultGamemode() {
        return gamemode;
    }

    @Override
    final public void setDefaultGamemode(Gamemode gamemode) {
        this.gamemode = gamemode;
    }

    @Override
    final public String getMotd() {
        return this.motd;
    }

    @Override
    final public void setMotd(String motd) {
        this.motd = motd;
    }

    @Override
    final public Difficulty getDefaultDifficulty() {
        return difficulty;
    }

    @Override
    final public void setDefaultDifficulty(Difficulty difficulty) {
        this.difficulty = difficulty;
    }

    @Override
    final public PlayerManager getPlayerManager() {
        return server.players;
    }

    @Override
    final public CommandSender getConsoleSender() {
        return this.console;
    }

    @Override
    final public File getPluginFolder() {
        return this.pluginFolder;
    }

    @Override
    final public void loadPlugins() {
        Logging.getLogger().log("Plugin Folder: " + pluginFolder.getPath());
        if (!pluginFolder.exists()) {
            if (!pluginFolder.mkdir()) {
                Logging.getLogger().error("Could not create the plugin folder");
                return;
            }
        }
        Logging.getLogger().log("Loading Plugins...");
        pluginLoader.loadAllPlugins(pluginFolder);
        Logging.getLogger().log("Enabling Plugins...");
        pluginLoader.enableAllPlugins();
    }

    @Override
    final public Base64Image getFavicon() {
        if (image == null) {
            try {
                File file = new File("./res/favicon.png");
                if (file.exists()) {
                    this.image = new Base64Image(file);
                } else {
                    this.image = new Base64Image(null);
                }
            } catch (Throwable e) {
                e.printStackTrace();
            }
        }
        return image;
    }

    @Override
    final public Player getPlayer(final short uid) {
        return this.getPlayerManager().getPlayer(uid);
    }

    @Override
    final public StandaloneServer getServer() {
        return this.server;
    }

    @Override
    final public Collection<Player> getPlayers() {
        return this.getPlayerManager().getPlayers();
    }

    @Override
    final public int getPlayerCount() {
        return this.getPlayers().size();
    }

    @Override
    final public World getWorld(final String name) {
        return null;
    }

    @Override
    final public List<World> getWorlds() {
        return this.server.getWorldManager().getWorlds();
    }

    @Override
    final public Player getPlayer(final UUID uuid) {
        return this.getPlayerManager().getPlayer(uuid);
    }

    @Override
    final public Player getPlayer(final String username) {
        return this.getPlayerManager().getPlayer(username);
    }

    @Override
    final public void callEvent(final Event event) {
        EventManager.getInstance().handle(event);
    }

    @Override
    final public int getMaxPlayers() {
        return this.maxPlayers;
    }

    @Override
    final public void setMaxPlayers(final int n) {
        this.maxPlayers = n;
    }

    @Override
    final public PluginLoader getPluginLoader() {
        return this.pluginLoader;
    }

    @Override
    final public String getProviderName() {
        return "marine";
    }

    @Override
    public byte getProviderPriority() {
        return 0x00;
    }
}
