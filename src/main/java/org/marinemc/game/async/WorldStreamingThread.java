package org.marinemc.game.async;

import org.marinemc.game.PlayerManager;
import org.marinemc.game.player.Player;
import org.marinemc.logging.Logging;
import org.marinemc.server.Marine;

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

	public WorldStreamingThread(final PlayerManager playerManager) {
		super("WorldStreamer");
		manager = playerManager;
	}

	private long time;

	@Override
	public void run() {

		// Wait until the system has been setup:
		while (Marine.getServer() == null)
			try {
				WorldStreamingThread.sleep(100);
			} catch (final InterruptedException e1) {
			}

		while (true) {
			if (Marine.getServer().getPlayerManager().isEmpty())
				try {
					WorldStreamingThread.sleep(500);
					continue;
				} catch (final InterruptedException e1) {
					Logging.getLogger()
							.error("The WorldStreamer got interupted :S, Recommending restart!");
				}

			time = System.nanoTime();

			for (final Player p : manager.getPlayers()) {
				if(!p.isStreamingNeeded()) continue;
				p.getWorld().generateAsyncRegion((int) p.getX(),
						(int) p.getY(),
						Marine.getServer().getViewDistance() * 2,
						Marine.getServer().getViewDistance() * 2);
				p.localChunkRegion(Marine.getServer().getViewDistance()); // Send the chunks
			}

			try {
				WorldStreamingThread.sleep(nonNeg(sleepTime
						- (System.nanoTime() - time) / 1000 / 1000));
			} catch (final InterruptedException e) {
				Logging.getLogger()
						.error("The WorldStreamer got interupted :S, Recommending restart!");
			}
		}
	}

	private long nonNeg(final long i) {
		if (i > 0)
			return i;
		else
			return 0;
	}
}
