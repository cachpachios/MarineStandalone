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

package com.marine;

import com.marine.events.Listener;
import com.marine.game.CommandManager;
import com.marine.game.PlayerManager;
import com.marine.game.WorldManager;
import com.marine.game.chat.ChatColor;
import com.marine.game.commands.*;
import com.marine.game.scheduler.Scheduler;
import com.marine.net.NetworkManager;
import com.marine.net.play.clientbound.KickPacket;
import com.marine.player.Gamemode;
import com.marine.player.Player;
import com.marine.plugins.PluginLoader;
import com.marine.plugins.PluginManager;
import com.marine.server.Marine;
import com.marine.server.MarineServer;
import com.marine.server.Server;
import com.marine.settings.JSONFileHandler;
import com.marine.settings.ServerSettings;
import com.marine.world.Difficulty;

import java.io.File;
import java.util.Timer;
import java.util.TimerTask;

import org.json.JSONException;

@SuppressWarnings("unused")
public class StandaloneServer implements Listener {

    // Final values
    public final int skipTime;
    private final int port;
    private final int targetTickRate;

    // Managers and handlers
    private final PlayerManager players;
    private final WorldManager worlds;
    private final Server server;
    private final Scheduler scheduler;
    private final PluginLoader pluginLoader;
    // Dynamic values
    public int ticks;
    NetworkManager network;
    private JSONFileHandler jsonHandler;
    // Settings:
    private String standard_motd = "MarineStandalone | Development";
    private int standard_maxplayers = 99;
    private Difficulty standard_difficulty = Difficulty.PEACEFUL;
    private Gamemode standard_gamemode = Gamemode.SURVIVAL;
    private boolean shouldRun;
    private String newMOTD = null;

    public StandaloneServer(final MainComponent.StartSettings settings) throws Throwable {
        this.port = settings.port;
        this.skipTime = 1000000000 / settings.tickrate; // nanotime
        this.targetTickRate = settings.tickrate;
        this.worlds = new WorldManager();
        this.players = new PlayerManager(this);
        this.server = new Server(this);
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
        new Timer("scheduler").scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                scheduler.run();
            }
        }, 0l, (1000 / 20));
        // Make the plugin loader
        this.pluginLoader = new PluginLoader(new PluginManager());
    }

    public Scheduler getScheduler() {
        return this.scheduler;
    }

    private void loadPlugins() {
        final File pluginFolder = new File("./plugins");
        Logging.getLogger().log("Plugin Folder: " + pluginFolder.getPath());
        if (!pluginFolder.exists()) {
            if (!pluginFolder.mkdir()) {
                Logging.getLogger().error("Could not create plugin folder");
                return;
            }
        }
        Logging.getLogger().log("Loading Plugins...");
        this.pluginLoader.loadAllPlugins(pluginFolder);
        Logging.getLogger().log("Enabling Plugins...");
        this.pluginLoader.enableAllPlugins();
    }

    private void registerDefaultCommands() {
        CommandManager.getInstance().registerCommand(new Info());
        CommandManager.getInstance().registerCommand(new Help());
        CommandManager.getInstance().registerCommand(new Test());
        CommandManager.getInstance().registerCommand(new Say());
        CommandManager.getInstance().registerCommand(new Stop());
        CommandManager.getInstance().registerCommand(new Plugins());
        CommandManager.getInstance().registerCommand(new SendAboveActionBarMessage());
        CommandManager.getInstance().registerCommand(new Teleport());
    }

    public void start() {
        this.shouldRun = true;
        run();
    }

    public MarineServer getServer() {
        return this.server;
    }

    public PlayerManager getPlayerManager() {
        return players;
    }

    private void init() {
        Logging.getLogger().logf("Starting MarineStandalone Server - Protocol Version §c§o%d§0 (Minecraft §c§o%s§0)",
                ServerProperties.PROTOCOL_VERSION, ServerProperties.MINECRAFT_NAME);
        // Start the networking stuffz
        if (this.network == null) {
            this.network = new NetworkManager(this, port, ServerSettings.getInstance().useHasing);
            this.network.openConnection();
        }
        //TODO World loading
        // Open connection
        // Load in and enable plugins
        this.loadPlugins();
        initialized = true;
    }

    public void run() {
        if (!this.initialized)
            init();
        players.updateThemAll();
        network.tryConnections();
        try {
            this.players.tickAllPlayers();
            this.worlds.tick();
            this.scheduler.tickSync();
            // Should really not be static
            // TODO Fix this
            ServerProperties.tick();
        } catch (Throwable e) {
            // Oh noes :(
            e.printStackTrace();
        }
    }

    public void stop() {
        for (final Player player : players.getPlayers()) {
            player.getClient().sendPacket(new KickPacket(ChatColor.red + ChatColor.bold + "Server stopped"));
        }
        // Disable all plugins
        pluginLoader.disableAllPlugins();
        // Should not run, smart stuff
        MainComponent.mainTimer.cancel();
        // Save all json configs
        jsonHandler.saveAll();
        // When finished
        System.exit(0);
    }

    public void restart() {
    	Logging.getLogger().log("Server is restaring...");
        for (final Player player : players.getPlayers()) {
            player.kick("Server Restarting");
        }
        pluginLoader.disableAllPlugins();
        shouldRun = false;
        jsonHandler.saveAll();
        Logging.getLogger().clearLogger();
        Logging.getLogger().logf("Starting MarineStandalone Server - Protocol Version §c§o%d§0 (Minecraft §c§o%s§0)",
                ServerProperties.PROTOCOL_VERSION, ServerProperties.MINECRAFT_NAME);
        final StandaloneServer server = this;
        new Timer("restarter").schedule(new TimerTask() {
            @Override
            public void run() {
                try {jsonHandler = new JSONFileHandler(server, new File("./settings"), new File("./storage"));} catch (JSONException e) {}
                server.shouldRun = true;
                server.run();
            }
        }, 0l, 1500l);
    }

    public NetworkManager getNetwork() {
        return this.network;
    }

    public WorldManager getWorldManager() {
        return this.worlds;
    }

    public String getMOTD() {
        if (newMOTD != null)
            return newMOTD;
        try {
            newMOTD = ChatColor.transform('&', ServerSettings.getInstance().motd);
        } catch (Throwable e) {
            return standard_motd;
        }
        return newMOTD;
    }

    public void setMOTD(final String motd) {
        this.standard_motd = motd;
    }

    public int getMaxPlayers() {
        return this.standard_maxplayers;
    }

    public void setMaxPlayers(final int maxplayers) {
        this.standard_maxplayers = maxplayers;
    }

    public Difficulty getDifficulty() {
        return this.standard_difficulty;
    }

    public void setDifficulty(final Difficulty difficulty) {
        this.standard_difficulty = difficulty;
    }

    public Gamemode getGamemode() {
        return this.standard_gamemode;
    }

    public void setGameMode(final Gamemode gm) {
        this.standard_gamemode = gm;
    }

    public PluginLoader getPluginLoader() {
        return this.pluginLoader;
    }
}
