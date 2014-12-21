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

import org.marinemc.Bootstrap;
import org.marinemc.events.EventManager;
import org.marinemc.events.standardevents.ServerReadyEvent;
import org.marinemc.game.CommandManager;
import org.marinemc.game.PlayerManager;
import org.marinemc.game.WorldManager;
import org.marinemc.game.chat.ChatColor;
import org.marinemc.game.commands.*;
import org.marinemc.game.scheduler.Scheduler;
import org.marinemc.logging.Logging;
import org.marinemc.net.NetworkManager;
import org.marinemc.net.play.clientbound.KickPacket;
import org.marinemc.player.Player;
import org.marinemc.settings.JSONFileHandler;
import org.marinemc.settings.ServerSettings;
import org.marinemc.world.Identifiers;

import java.io.File;

/**
 * StandaloneServer - Housing of the main loop
 *
 * @author Fozie
 * @author Citymonstret
 */
@SuppressWarnings("unused")
public class StandaloneServer {

    // Final values
    public final int skipTime;
    public final int targetTickRate;
    // Managers and handlers
    protected final PlayerManager players;
    protected final WorldManager worlds;
    protected final Scheduler scheduler;
    private final int port;
    private final Server server;
    // Dynamic values
    public int ticks;
    NetworkManager network;
    private JSONFileHandler jsonHandler;
    // Settings:
    private boolean shouldRun;
    private boolean initialized = false;

    /**
     * Constructor
     *
     * @param settings Startup settings
     * @throws Throwable If anything goes wrong
     */
    public StandaloneServer(final Bootstrap.StartSettings settings) throws Throwable {
        this.port = settings.port;
        this.skipTime = 1000000000 / settings.tickrate; // nanotime
        this.targetTickRate = settings.tickrate;
        this.worlds = new WorldManager(this);
        this.server = new Server(this);
        this.players = new PlayerManager(this);
        this.jsonHandler = new JSONFileHandler(this, new File("./settings"), new File("./storage"));
        // Set the static standalone server
        Marine.setStandalone(this);
        // Server the server
        Marine.setServer(this.server);
        // Register commands
        registerDefaultCommands();
        // Create a new scheduler instance
        this.scheduler = new Scheduler();
        // Set it to run 20 times per second
        this.scheduler.start(1000 / targetTickRate);
        // Make the plugin loader
        // Activate the identifier
        Identifiers.init();
    }

    /**
     * Get the internal scheduler
     *
     * @return scheduler
     */
    public Scheduler getScheduler() {
        return this.scheduler;
    }


    /**
     * Register default (internal) commands
     */
    private void registerDefaultCommands() {
        CommandManager.getInstance().registerCommand(server, new Info());
        CommandManager.getInstance().registerCommand(server, new Help());
        CommandManager.getInstance().registerCommand(server, new Test());
        CommandManager.getInstance().registerCommand(server, new Say());
        CommandManager.getInstance().registerCommand(server, new Stop());
        CommandManager.getInstance().registerCommand(server, new Plugins());
        CommandManager.getInstance().registerCommand(server, new SendAboveActionBarMessage());
        CommandManager.getInstance().registerCommand(server, new Teleport());
        CommandManager.getInstance().registerCommand(server, new Tellraw());
        CommandManager.getInstance().registerCommand(server, new List());
        CommandManager.getInstance().registerCommand(server, new Me());
    }

    /**
     * Get the MarineServer implementation
     *
     * @return server API
     */
    public MarineServer getServer() {
        return this.server;
    }

    /**
     * Init. the server
     */
    private void init() {
        // Start the networking stuffz
        if (this.network == null) {
            this.network = new NetworkManager(this, port, ServerSettings.getInstance().useHasing);
            this.network.openConnection();
        }
        //TODO World loading
        // Open connection
        // Load in and enable plugins
        getServer().loadPlugins();
        // Bake all event handlers
        EventManager.getInstance().bake();
        initialized = true;
        getServer().callEvent(new ServerReadyEvent());
    }

    /**
     * Run!
     */
    public void run() throws Exception {
        if (!this.initialized)
            init();
        this.players.updateThemAll();
        network.tryConnections();
        this.players.tickAllPlayers();
        this.worlds.tick();
        this.scheduler.tickSync();
        // Should really not be static
        // TODO Fix this
        ServerProperties.tick();
    }

    /**
     * Stop the server
     */
    public void stop() {
        Logging.getLogger().info("Shutting down...");
        for (final Player player : players.getPlayers()) {
            player.getClient().sendPacket(new KickPacket(ChatColor.red + ChatColor.bold + "Server stopped"));
        }
        Logging.getLogger().info("Plugin Handler Shutting Down");
        // Disable all plugins
        getServer().getPluginLoader().disableAllPlugins();
        // Should not run, smart stuff
        Bootstrap.instance().mainTimer.cancel();
        // Save all json configs
        Logging.getLogger().info("Saving JSON Files");
        jsonHandler.saveAll();
        // Logging stop
        Logging.getLogger().saveLog();
        // When finished
        System.exit(0);
    }

    public PlayerManager getPlayerManager() {
        return this.players;
    }

    /**
     * Get the internal network manager
     *
     * @return internal network manager
     */
    public NetworkManager getNetwork() {
        return this.network;
    }

    public WorldManager getWorldManager() {
        return this.worlds;
    }

}