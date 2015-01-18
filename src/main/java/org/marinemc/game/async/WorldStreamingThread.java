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

import java.util.ArrayList;

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
	private long time;

	private ArrayList<Short> streamingRequested;
	
	public WorldStreamingThread(final PlayerManager playerManager) {
		super("WorldStreamer");
		manager = playerManager;
		streamingRequested = new ArrayList<Short>();
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

			//The update

			
			//To minimize lock time the list is moved to a new Short array.
			Short[] streamers = new Short[streamingRequested.size()];
			
			synchronized(streamingRequested) {
				streamers = streamingRequested.toArray(streamers);
				streamingRequested.clear();
			}
			
			for(Short s : streamers)
				Marine.getServer().getPlayer(s).localChunkRegion(Marine.getServer().getViewDistance());
			
			time = System.nanoTime();

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

	public void asyncStreaming(short uid) {
		synchronized(streamingRequested) {
			if(!streamingRequested.contains(uid));
				streamingRequested.add(uid);
		}
	}
}
