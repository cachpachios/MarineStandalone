package com.marine;

import com.marine.events.Listener;
import com.marine.game.CommandManager;
import com.marine.game.PlayerManager;
import com.marine.game.WorldManager;
import com.marine.game.chat.ChatColor;
import com.marine.game.commands.*;
import com.marine.game.scheduler.Scheduler;
import com.marine.net.NetworkManager;
import com.marine.player.Gamemode;
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

public class StandaloneServer implements Listener {

    public final int skipTime;
    private final int port;
    // Managers
    private final PlayerManager players;
    private final WorldManager worlds;
    private final Server server;
    private final JSONFileHandler jsonHandler;
    private final Scheduler scheduler;
    private final PluginLoader pluginLoader;
    private final int targetTickRate; // For use in the loop should be same as (skipTime * 1000000000)
    public int ticks;
    public int refreshesPerSecound;
    NetworkManager network;
    // Settings:
    private String standard_motd = "MarineStandalone | Development";
    private int standard_maxplayers = 99;
    private Difficulty standard_difficulty = Difficulty.PEACEFUL;
    private Gamemode standard_gamemode = Gamemode.SURVIVAL;
    private boolean shouldRun;
    private String newMOTD = null;

    public StandaloneServer(MainComponent.StartSettings settings) {
        this.port = settings.port;
        this.skipTime = 1000000000 / settings.tickrate; // nanotime
        this.targetTickRate = settings.tickrate;
        this.worlds = new WorldManager();
        this.players = new PlayerManager(this);
        this.server = new Server(this);
        this.jsonHandler = new JSONFileHandler(this, new File("./settings"), new File("./storage"));
        this.jsonHandler.loadAll();
        /*try {
            this.jsonHandler.defaultValues();
        } catch (Throwable e) {
            e.printStackTrace();
        }*/
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
        // TPS and stuff
        // scheduler.createSyncTask(new LagTester());
        // Create a new plugin loader
        this.pluginLoader = new PluginLoader(new PluginManager());
    }
    
    public Scheduler getScheduler() {
        return this.scheduler;
    }

    private void loadPlugins() {
        File pluginFolder = new File("./plugins");
        Logging.getLogger().log("Plugin Folder: " + pluginFolder.getPath());
        if(!pluginFolder.exists()) {
            if(!pluginFolder.mkdir()) {
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
        shouldRun = true;
        run();
    }

    public MarineServer getServer() {
        return this.server;
    }

    public PlayerManager getPlayerManager() {
        return players;
    }

    private void run() {


        Logging.getLogger().log(String.format("Marine Standalone Server starting - Protocol Version §c§o%d§0 (Minecraft §c§o%s§0)", ServerProperties.PROTOCOL_VERSION, ServerProperties.MINECRAFT_NAME));

        network = new NetworkManager(this, port, ServerSettings.getInstance().useHasing);

        //TODO World loading

        network.openConnection();

        // Load in and enable plugins
        this.loadPlugins();

        long startTime = System.nanoTime();
        long lastTime = startTime;

        long lastRunTime = startTime;

        int ups = 0;

        while (shouldRun) {
            startTime = System.nanoTime(); // Microtime

            if ((startTime - lastRunTime < skipTime)) {
                int sleepTime = (int) (skipTime - (startTime - lastRunTime));

                if (sleepTime > 0)
                    try {
                        Thread.sleep(sleepTime / 1000000, sleepTime % 1000);
                    } catch (InterruptedException e) {
                    }
                continue;
            }

            lastRunTime = startTime;

            if (startTime - lastTime >= 1000000000) {
                ticks = ups;
                players.updateThemAll();
                ups = 0;
                lastTime = startTime;
            }

            if (ups >= targetTickRate) {
                continue;
            }
            //Check networkConnections
            network.tryConnections();
            
            // Stuff:

            if (Logging.getLogger().isDisplayed())
                if (Logging.getLogger().hasBeenTerminated())
                    System.exit(0);

            // Advance the tick clock.
            players.tickAllPlayers();
            worlds.tick();
            ServerProperties.tick();
            scheduler.tickSync();
            ups++;
        }// Loop End
    }

    public void stop() {
        shouldRun = false;
        jsonHandler.saveAll();

        // When finished
        System.exit(0);
    }

    public NetworkManager getNetwork() {
        return network;
    }

    public WorldManager getWorldManager() {
        return worlds;
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

    public void setMOTD(String motd) {
        this.standard_motd = motd;
    }

    public int getMaxPlayers() {
        return standard_maxplayers;
    }

    public void setMaxPlayers(int maxplayers) {
        this.standard_maxplayers = maxplayers;
    }

    public Difficulty getDifficulty() {
        return standard_difficulty;
    }

    public void setDifficulty(Difficulty difficulty) {
        this.standard_difficulty = difficulty;
    }

    public Gamemode getGamemode() {
        return standard_gamemode;
    }

    public void setGamemode(Gamemode gm) {
        this.standard_gamemode = gm;
    }

    public PluginLoader getPluginLoader() {
        return this.pluginLoader;
    }
}
