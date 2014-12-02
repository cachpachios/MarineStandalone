package com.marine;

import com.marine.game.PlayerManager;
import com.marine.game.WorldManager;
import com.marine.net.NetworkManager;
import com.marine.server.Server;

public class StandaloneServer {
	
	private final int port;
	
	// Game managers
	private final PlayerManager players;
	private final WorldManager worlds;
	private final Server server;

	NetworkManager network;
	
	public final int skipTime;
	private final int targetTickRate; // For use in the loop should be same as (skipTime * 1000000000)

	
	public int ticks;
	public int refreshesPerSecound;
	
	private boolean shouldRun;
	
	public StandaloneServer(final int port, final int targetTickRate) {
		this.port = port;
		this.skipTime = 1000000000 / targetTickRate; // nanotim
		this.targetTickRate = targetTickRate;
        this.players = new PlayerManager(this);
        this.worlds = new WorldManager();
        this.server = new Server(this);
	}
	
	public void start() {
		shouldRun = true;
		run();
	}
	
	public PlayerManager getPlayerManager() {
		return players;
	}
	
	private void run() {
        Logging.getLogger().log(String.format("Marine Standalone Server starting - Protocol Version §c§o%d§0 (Minecraft §c§o%s§0)", ServerProperties.PROTOCOL_VERSION, ServerProperties.MINECRAFT_NAME));

		network = new NetworkManager(this, port);
		network.openConnection();

		try { // Check OS Arch and warn if lower than 64bit
			if(Integer.parseInt(System.getProperty("sun.arch.data.model")) < 64)  {
				Logging.getLogger().warn("Warning Server is running on 32bit this is highly not recommended and can mean fatal errors or lag!");
				Logging.getLogger().warn("Consider update java or your hardware.");
			}
		} catch(SecurityException e)  { // If blocked print an error
			Logging.getLogger().error("Unable to retrieve computer arch! Perhaps blocked by the OS.");
		}



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
	}

	public NetworkManager getNetwork() {
		return network;
	}
	
	public WorldManager getWorldManager() {
		return worlds;
	}
	
}
