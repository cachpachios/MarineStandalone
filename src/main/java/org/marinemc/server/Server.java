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

import java.io.File;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.util.Collection;
import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;

import org.marinemc.events.Event;
import org.marinemc.events.EventManager;
import org.marinemc.events.standardevents.ServerReadyEvent;
import org.marinemc.game.CommandManager;
import org.marinemc.game.PlayerManager;
import org.marinemc.game.WorldManager;
import org.marinemc.game.chat.ChatColor;
import org.marinemc.game.command.Command;
import org.marinemc.game.command.CommandSender;
import org.marinemc.game.command.ConsoleSender;
import org.marinemc.game.command.ServiceProvider;
import org.marinemc.game.commands.Help;
import org.marinemc.game.commands.Info;
import org.marinemc.game.commands.List;
import org.marinemc.game.commands.Me;
import org.marinemc.game.commands.Plugins;
import org.marinemc.game.commands.Say;
import org.marinemc.game.commands.SendAboveActionBarMessage;
import org.marinemc.game.commands.Stop;
import org.marinemc.game.commands.Teleport;
import org.marinemc.game.commands.Tellraw;
import org.marinemc.game.commands.Test;
import org.marinemc.game.permission.PermissionManager;
import org.marinemc.game.player.Player;
import org.marinemc.game.scheduler.MarineRunnable;
import org.marinemc.game.scheduler.Scheduler;
import org.marinemc.game.system.MarineSecurityManager;
import org.marinemc.logging.Logging;
import org.marinemc.net.NetworkManager;
import org.marinemc.net.play.clientbound.KickPacket;
import org.marinemc.plugins.PluginLoader;
import org.marinemc.plugins.PluginManager;
import org.marinemc.settings.JSONFileHandler;
import org.marinemc.settings.ServerSettings;
import org.marinemc.util.Assert;
import org.marinemc.util.Base64Image;
import org.marinemc.util.Location;
import org.marinemc.util.StartSettings;
import org.marinemc.util.mojang.UUIDHandler;
import org.marinemc.world.Difficulty;
import org.marinemc.world.Gamemode;
import org.marinemc.world.Identifiers;
import org.marinemc.world.World;

/**
 * Server implementation
 *
 * @author Citymonstret
 * @author Fozie
 */
public class Server extends TimerTask implements MarineServer, ServiceProvider {

    private final PluginLoader pluginLoader;
    private final File pluginFolder, settingsFolder, storageFolder;
    private final int port;
    private final CommandSender console;
    private final NetworkManager networkManager;
    private final PlayerManager playerManager;
    private final WorldManager worldManager;
    private final Scheduler scheduler;
    private final Timer timer;
    private final int tickRate;
    private InetAddress address;
    private JSONFileHandler jsonFileHandler;
    private Base64Image image;
    private Gamemode gamemode;
    private Difficulty difficulty;
    private String motd;
    private int maxPlayers;
    private boolean offlineMode;
    private boolean stopping;

    public Server(final StartSettings settings) {
        // Security Check Start
        System.getSecurityManager().checkPermission(MarineSecurityManager.MARINE_PERMISSION);
        // Security Check end
        this.address = getAddress();
        this.stopping = false;
        this.timer = new Timer("mainTimer", true);
        this.port = settings.port;
        this.tickRate = settings.tickrate;
        this.worldManager = new WorldManager(this);
        this.playerManager = new PlayerManager();
        this.pluginLoader = new PluginLoader(new PluginManager());
        this.networkManager = new NetworkManager(port, address);
        this.pluginFolder = new File("./plugins");
        this.storageFolder = new File("./storage");
        this.settingsFolder = new File("./settings");
		this.jsonFileHandler = new JSONFileHandler(settingsFolder, storageFolder);
        this.scheduler = new Scheduler();
        this.console = new ConsoleSender();
        this.gamemode = Gamemode.valueOf(ServerSettings.getInstance().gamemode.toUpperCase());
        this.difficulty = Difficulty.valueOf(ServerSettings.getInstance().difficulty.toUpperCase());
        this.motd = ChatColor.transform('&', ServerSettings.getInstance().motd);
        this.maxPlayers = ServerSettings.getInstance().maxPlayers;
        this.offlineMode = ServerSettings.getInstance().offlineMode;
        // INIT :D
        init();
    }

    @Override
    final public InetAddress getAddress() {
        if (this.address == null) {
            String addr = null;
            try {
                addr = ServerSettings.getInstance().host;
            } catch (Throwable e) {
                e.printStackTrace();
            }
            if (addr == null || addr.length() == 0) {
                try {
                    return Inet4Address.getLocalHost();
                } catch (Exception e) {
                    e.printStackTrace();
                    return null;
                }
            }
            try {
                return InetAddress.getByName(addr);
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }
        return this.address;
    }

    private void init() {
        Marine.setServer(this);
        this.scheduler.start(1000 / tickRate);
        registerDefaultCommands();
        Identifiers.init();
        this.networkManager.openConnection();
        loadPlugins();
        Logging.getLogger().log("Generating the World");
        this.worldManager.getMainWorld().getChunks(0, 0, 8, 8);
        EventManager.getInstance().bake();
        callEvent(new ServerReadyEvent());
        this.timer.scheduleAtFixedRate(this, 0l, (1000 / tickRate));
        PermissionManager.instance().load();
        UUIDHandler.instance(); // Make sure to init.
    }

    @Override
    final public boolean isStopping() {
        return this.stopping;
    }

    @Override
    final public void run() {
        try {
            networkManager.tryConnections();
            playerManager.tickAllPlayers();
            scheduler.tickSync();
        } catch (final Throwable e) {
            Logging.getLogger().error("Something went wrong in the main thread...", e);
        }
    }

    private void registerDefaultCommands() {
        final Command[] defaults = new Command[]{
                new Info(), new Help(),
                new Test(), new Say(),
                new Stop(), new Plugins(),
                new SendAboveActionBarMessage(),
                new Teleport(), new Me(),
                new Tellraw(), new List(),
        };
        final CommandManager commandManager = CommandManager.getInstance();
        for (final Command command : defaults) {
            commandManager.registerCommand(this, command);
        }
    }

    @Override
    final public JSONFileHandler getJsonFileHandler() {
        return this.jsonFileHandler;
    }

    @Override
    final public Gamemode getDefaultGamemode() {
        return gamemode;
    }

    @Override
    final public void setDefaultGamemode(final Gamemode gamemode) {
        Assert.notNull(gamemode);
        this.gamemode = gamemode;
    }

    @Override
    final public String getMotd() {
        return this.motd;
    }

    @Override
    final public void setMotd(String motd) {
        Assert.notNull(motd);
        this.motd = motd;
    }

    @Override
    final public Difficulty getDefaultDifficulty() {
        return difficulty;
    }

    @Override
    final public void setDefaultDifficulty(final Difficulty difficulty) {
        Assert.notNull(difficulty);
        this.difficulty = difficulty;
    }

    @Override
    final public PlayerManager getPlayerManager() {
        return playerManager;
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
    final public void setFavicon(final Base64Image image) {
        Assert.notNull(image);
        this.image = image;
    }

    @Override
    final public Player getPlayer(final short uid) {
        return this.getPlayerManager().getPlayer(uid);
    }

    @Override
    final public Collection<Player> getPlayers() {
        return this.getPlayerManager().getPlayers();
    }

    @Override
    final public int getPlayerCount() {
        return this.getPlayerManager().getPlayersConnected();
    }

    @Override
    final public World getWorld(final String name) {
        return null;
    }

    @Override
    final public java.util.List<World> getWorlds() {
        return worldManager.getWorlds();
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
        Assert.notNull(event);
        EventManager.getInstance().handle(event);
    }

    @Override
    final public int getMaxPlayers() {
        return this.maxPlayers;
    }

    @Override
    final public void setMaxPlayers(final int n) {
        Assert.notNull(n);
        this.maxPlayers = n;
    }

    @Override
    final public NetworkManager getNetworkManager() {
        return this.networkManager;
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
    final public Location getSpawnLocation() {
        return worldManager.getMainWorld().getSpawnPoint().toLocation();
    }

    @Override
    final public byte getProviderPriority() {
        return 0x00;
    }

    @Override
    final public void stop() {
        stopping = true;
        scheduler.createSyncTask(new MarineRunnable(this, 5, 1) {
            @Override
            public void run() {
                Logging.getLogger().info("Shutting down...");
                for (final Player player : getPlayers()) {
                    player.getClient().sendPacket(new KickPacket(ChatColor.red + ChatColor.bold + "Server stopped"));
                }
                Logging.getLogger().info("Plugin Handler Shutting Down");
                // Cache UUIDs
                Logging.getLogger().info("Caching UUIDs");
                UUIDHandler.instance().save();
                // Disable all plugins
                pluginLoader.disableAllPlugins();
                // Should not run, smart stuff
                timer.cancel();
                // Save all json configs
                Logging.getLogger().info("Saving JSON Files");
                jsonFileHandler.saveAll();
                // Logging stop
                Logging.getLogger().saveLog();
                // When finished
                System.exit(0);
            }
        });
    }

    @Override
    final public boolean usingWhitelist() {
        return ServerSettings.getInstance().whitelist;
    }

    @Override
    final public boolean isWhitelisted(final Player player) {
        return jsonFileHandler.whitelist.isWhitelisted(player.getUUID());
    }

    @Override
    final public boolean isBanned(final UUID uuid) {
        return jsonFileHandler.banned.isBanned(uuid);
    }

    @Override
    final public boolean isBanned(final InetAddress address) {
        return jsonFileHandler.banned.isBanned(address);
    }

    @Override
    final public void setWhitelisted(final UUID uuid, final boolean b) {
        jsonFileHandler.whitelist.setWhitelisted(uuid, b);
    }

    @Override
    final public void setBanned(final InetAddress address, final boolean b) {
        jsonFileHandler.banned.setBanned(address, b);
    }

    @Override
    final public void setBanned(final UUID uuid, final boolean b) {
        jsonFileHandler.banned.setBanned(uuid, b);
    }

    @Override
    final public Scheduler getScheduler() {
        return this.scheduler;
    }

    @Override
    final public WorldManager getWorldManager() {
        return this.worldManager;
    }

    @Override
    final public int getPort() {
        return this.port;
    }

    @Override
    final public int getTickRate() {
        return this.tickRate;
    }

    @Override
    final public File getStorageFolder() {
        return this.storageFolder;
    }

    @Override
    final public File getSettingsFolder() {
        return settingsFolder;
    }

    @Override
    final public boolean isOfflineMode() {
        return this.offlineMode;
    }

    @Override
    final public void setOfflineMode(final boolean offlineMode) {
        Assert.notNull(offlineMode);
        this.offlineMode = offlineMode;
    }
}
