package org.marinemc.game.async;

import org.marinemc.game.PlayerManager;
import org.marinemc.game.player.Player;
import org.marinemc.logging.Logging;
import org.marinemc.server.Marine;
import org.marinemc.util.operations.PlayerOperation;

/**
 * Sends and unloads chunks localy for each player with an update rate of 8hz
 * Does also so the World will async load chunks around the player
 * 
 * @author Fozie
 *
 */
public class WorldStreamingThread extends Thread {
	public static final long sleepTime = 1000 / 8;
	
	final PlayerManager manager;
	
	public WorldStreamingThread(PlayerManager playerManager) {
		super("WorldStreamer");
		this.manager = playerManager;
	}
	
	private long time;
	public void run() {
		
		// Wait until the system has been setup:
		while(Marine.getServer() == null)
			try { WorldStreamingThread.sleep(100); } catch (InterruptedException e1) {}
		
		while(true) {
			if(Marine.getServer().getPlayerManager().isEmpty())
				try {
					System.out.println("No players online :(");
					WorldStreamingThread.sleep(500);
					continue;
				} catch (InterruptedException e1) {
					Logging.getLogger().error("The WorldStreamer got interupted :S, Recommending restart!");
				}
			
			time = System.nanoTime();
			
			for(Player p :	manager.getPlayers()){
				p.getWorld().generateAsyncRegion((int)p.getX(), (int)p.getY(), (int)(Marine.getServer().getViewDistance()*2), (int)(Marine.getServer().getViewDistance()*2));
				p.localChunkRegion(Marine.getServer().getViewDistance(), Marine.getServer().getViewDistance());
			}
			
			try {
				System.out.println("Sleep in " + nonNeg(sleepTime - ((System.nanoTime() - time)/1000/1000)));
				WorldStreamingThread.sleep(nonNeg(sleepTime - ((System.nanoTime() - time)/1000/1000)));
				} catch (InterruptedException e) {
					Logging.getLogger().error("The WorldStreamer got interupted :S, Recommending restart!");
				}
		}
	}
	
	private long nonNeg(long i) {
		if(i > 0)
			return i;
		else
			return 0;
	}
}
