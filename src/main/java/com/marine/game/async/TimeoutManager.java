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

package com.marine.game.async;

import com.marine.game.PlayerManager;
import com.marine.net.play.KeepAlivePacket;
import com.marine.player.Player;

import java.util.Collections;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

public class TimeoutManager extends Thread {
    private final PlayerManager players;
    private final Random rand;
    private Map<Player, Integer> lastRecive; // Contains last recived in seconds
    private Map<Player, Integer> lastSent; // Contains last sent KeepAlivePacketID

    public TimeoutManager(PlayerManager manager) {
        rand = new Random();
        this.players = manager;
        this.lastRecive = Collections.synchronizedMap(new ConcurrentHashMap<Player, Integer>());
        this.lastSent = Collections.synchronizedMap(new ConcurrentHashMap<Player, Integer>());
    }

    private long getMiliTime() {
        return (int) (System.nanoTime() / 1000 / 1000);
    }

    public void addPlayerToManager(Player p) {
        synchronized (lastRecive) {
            synchronized (lastSent) {
                lastRecive.put(p, 0);
            }
        }
    }

    private void update(Player p) {
            if (lastSent.containsKey(p))
                lastSent.remove(p);

            int id = rand.nextInt();
            p.getClient().sendPacket(new KeepAlivePacket(id));

            lastSent.put(p, id);
    }


    public void cleanUp(Player p) {
    		lastRecive.remove(p);
            lastSent.remove(p);
    }

    private void disconnect(Player p) {
        players.disconnect(p, "Connection Timed Out");
        cleanUp(p);
    }

    @Override
    public void run() { // Will update each second :D
        while (true) {
        	int time = (int) getMiliTime();
            for (Player p : lastRecive.keySet())
             		if (lastRecive.get(p)-time >= 10)
               			disconnect(p);
            try {
                TimeoutManager.sleep(1000);
            } catch (InterruptedException e) {}
        }
    }

    public void keepAlive(Player p, int ID) {
                lastSent.remove(p);
                lastRecive.remove(p);
                lastRecive.put(p, (int) getMiliTime());
                update(p);
    }

}
