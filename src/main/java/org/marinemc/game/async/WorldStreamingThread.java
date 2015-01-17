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

package org.marinemc.game.async;

import org.marinemc.game.PlayerManager;
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
	private long time;

	public WorldStreamingThread(final PlayerManager playerManager) {
		super("WorldStreamer");
		manager = playerManager;
	}

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

			// TODO FIX
			// for (final Player p : manager.getPlayers()) {
			//	if(!p.isStreamingNeeded()) continue;
			//	p.getWorld().generateAsyncRegion((int) p.getX(),
			//			(int) p.getY(),
			//			Marine.getServer().getViewDistance() * 2,
			//			Marine.getServer().getViewDistance() * 2);
			//	p.localChunkRegion(Marine.getServer().getViewDistance()); // Send the chunks
			// }

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
