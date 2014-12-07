package com.marine;

import com.marine.events.Listener;
import com.marine.events.TestListener;
import com.marine.game.CommandManager;
import com.marine.game.PlayerManager;
import com.marine.game.WorldManager;
import com.marine.game.chat.ChatColor;
import com.marine.game.commands.*;
import com.marine.net.NetworkManager;
import com.marine.player.Gamemode;
import com.marine.server.Marine;
import com.marine.server.MarineServer;
import com.marine.server.Server;
import com.marine.settings.JSONFileHandler;
import com.marine.settings.ServerSettings;
import com.marine.world.Difficulty;

import java.io.File;

public class StandaloneServer implements Listener {
	
	private final int port;
	
	// Managers
	private final PlayerManager players;
	private final WorldManager worlds;
	private final Server server;
    private final JSONFileHandler jsonHandler;

	// Settings:
	private String 			standard_motd = "MarineStandalone | Development";
	private int 			standard_maxplayers = 99;
	private Difficulty		standard_difficulty = Difficulty.PEACEFUL;
	private Gamemode		standard_gamemode = Gamemode.SURVIVAL;
	
	NetworkManager network;
	
	public final int skipTime;
	private final int targetTickRate; // For use in the loop should be same as (skipTime * 1000000000)
	
	public int ticks;
	public int refreshesPerSecound;
	
	private boolean shouldRun;
	
	public StandaloneServer(final int port, final int targetTickRate) {
		this.port = port;
		this.skipTime = 1000000000 / targetTickRate; // nanotime
		this.targetTickRate = targetTickRate;
        this.worlds = new WorldManager();
        this.players = new PlayerManager(this);
        this.server = new Server(this);
        this.jsonHandler = new JSONFileHandler(this, new File("./settings"), new File("./storage"));
        this.jsonHandler.loadAll();
        try {
            this.jsonHandler.defaultValues();
        } catch(Throwable e) {
            e.printStackTrace();
        }
        Marine.setStandalone(this);
        Marine.setServer(this.server);
        // Register commands
        registerDefaultCommands();

        getServer().registerListener(new TestListener());
    }

    private void registerDefaultCommands() {
        CommandManager.getInstance().registerCommand(new Info());
        CommandManager.getInstance().registerCommand(new Help());
        CommandManager.getInstance().registerCommand(new Test());
        CommandManager.getInstance().registerCommand(new Say());
        CommandManager.getInstance().registerCommand(new Stop());
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

		network = new NetworkManager(this, port);

		try { // Check OS Arch and warn if lower than 64bit
			if(Integer.parseInt(System.getProperty("sun.arch.data.model")) < 64)  {
				Logging.getLogger().warn("Warning Server is running on 32bit this is highly not recommended and can mean fatal errors or lag!");
				Logging.getLogger().warn("Consider update java or your hardware.");
			}
		} catch(SecurityException e)  { // If blocked print an error
			Logging.getLogger().error("Unable to retrieve computer arch! Perhaps blocked by the OS.");
		}

		//TODO World loading
		
		network.openConnection();

		long startTime = System.nanoTime();
		long lastTime = startTime;
		
		long lastRunTime = startTime;

		int ups = 0;
		
		while(shouldRun) {
			startTime = System.nanoTime(); // Microtime
			
			if((startTime - lastRunTime < skipTime) ) {
				int sleepTime = (int) (skipTime - (startTime - lastRunTime));

				if(sleepTime > 0)
				try { Thread.sleep(sleepTime/1000000, sleepTime % 1000); } catch (InterruptedException e) {}
				continue;
			}
				
			lastRunTime = startTime;
			
			if(startTime - lastTime >= 1000000000) {	
				ticks = ups;
				ups = 0;
				lastTime = startTime;
			}	
			
			if(ups >= targetTickRate) {	continue; }
			// Stuff:
			
			if(Logging.getLogger().isDisplayed())
				if(Logging.getLogger().hasBeenTerminated())
					System.exit(0);
			
			// Advance the tick clock.
			ServerProperties.tick();

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

    private String newMOTD = null;
	public String getMOTD() {
        if(newMOTD != null)
            return newMOTD;
		try {
            newMOTD = ChatColor.transform('&', ServerSettings.getInstance().motd);
        } catch(Throwable e) {
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
}
