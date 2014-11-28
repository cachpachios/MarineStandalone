package com.marineapi;

import com.marineapi.net.NetworkManager;

public class StandaloneServer {
	
	private int port;
	
	NetworkManager network;
	
	public final int skipTime;
	private final int targetTickRate; // For use in the loop should be same as (skipTime * 1000000000)

	
	public int ticks;
	
	private boolean shouldRun;
	
	public StandaloneServer(int port, int tickRate) {
		this.port = port;
		this.skipTime = 1000000000 / tickRate; // nanotim
		targetTickRate = tickRate;
	}
	
	public void start() {
		shouldRun = true;
		run();
	}
	
	private void run() {

		Logging.getLogger().log("Marine Standalone Server Starting protocol version "+ ServerProperties.PROTOCOL_VERSION +" (Minecraft "+ ServerProperties.Minecraft_Name +")");
		network = new NetworkManager(port);
		network.openConnection();
		
		long startTime = System.nanoTime();
		long lastTime = startTime;
		
		long lastRunTime = startTime;
		
		int ups = 0;
		
		while(shouldRun) {
			startTime = System.nanoTime(); // Microtime
			
			if((startTime - lastRunTime <= skipTime))
				continue;
			
			lastRunTime = startTime;
			
			if(startTime - lastTime >= 1000000000) {	
				ticks = ups;
				ups = 0;
				lastTime = startTime;
			}	
			
			if(ups >= targetTickRate) continue;
			
			// Stuff:
			network.processAll();
			
			ups++;
		}// Loop End
	}
	
	public void stop() {
		shouldRun = false;
	}
	
}
