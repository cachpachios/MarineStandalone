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
    private static final long sleepTime = 1500;
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
        synchronized (lastSent) {
            if (lastSent.containsKey(p))
                lastSent.remove(p);

            int id = rand.nextInt();
            if (id < 0) // IF id is negative
                id *= -1; // make it possetive
            if (id == 0) // if its zero
                id++; // Add one

            p.getClient().sendPacket(new KeepAlivePacket(id));

            lastSent.put(p, id);
        }
    }


    public void cleanUp(Player p) {
        synchronized (lastRecive) {
            synchronized (lastSent) {
                if (lastRecive.containsKey(p))
                    lastRecive.remove(p);
                if (lastSent.containsKey(p))
                    lastSent.remove(p);
            }
        }
    }

    private void disconnect(Player p) {
        players.disconnect(p, "Connection Timed Out");
        cleanUp(p);
    }

    @Override
    public void run() { // Will update each second :D
        long lastTime = getMiliTime();
        long time;
        while (true) {
            if (!players.hasAnyPlayers())
                try {
                    TimeoutManager.sleep(sleepTime);
                } catch (InterruptedException e1) {
                }
            time = getMiliTime();

            if (time - lastTime >= 1000) {
                synchronized (lastRecive) {
                    synchronized (lastSent) {
                        for (Player p : lastRecive.keySet()) {
                            int t = lastRecive.get(p);

                            lastRecive.remove(p);
                            lastRecive.put(p, t + 1);

                            if (t >= 10)
                                disconnect(p);
                        }

                        for (Player p : players.getPlayers())
                            if (!lastSent.containsKey(p)) update(p); // Send some packages
                    }
                }
                lastTime = time;
            }

            try {
                TimeoutManager.sleep(1000 - (time - lastTime));
            } catch (InterruptedException e) {
            }
        }
    }

    public void keepAlive(Player p, int ID) {
        synchronized (lastSent) {
            synchronized (lastRecive) {
                lastSent.remove(p);
                lastRecive.remove(p);
                lastRecive.put(p, 0);

            }
        }
    }


}
