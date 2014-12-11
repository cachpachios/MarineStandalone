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

import java.lang.ref.Reference;
import java.lang.ref.WeakReference;
import java.util.Collections;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

public class TimeoutManager extends Thread {
    private final PlayerManager players;
    private final Random rand;
    private Map<Short, Integer> lastRecive; // Contains last recived in seconds
    private Map<Short, Integer> lastSent; // Contains last sent KeepAlivePacketID

    public TimeoutManager(PlayerManager manager) {
        rand = new Random();
        this.players = manager;
        this.lastRecive = Collections.synchronizedMap(new ConcurrentHashMap<Short, Integer>());
        this.lastSent = Collections.synchronizedMap(new ConcurrentHashMap<Short, Integer>());
    }

    private long getMiliTime() {
        return (int) (System.nanoTime() / 1000 / 1000);
    }

    public void addPlayerToManager(final Player p) {
         lastRecive.put(p.getUID(), 0);
    }

    private void update(final Player p) {
            if (lastSent.containsKey(p))
                lastSent.remove(p);

            int id = rand.nextInt();
            p.getClient().sendPacket(new KeepAlivePacket(id));

            lastSent.put(p.getUID(), id);
    }


    public void cleanUp(Player p) {
    		lastRecive.remove(p.getUID());
            lastSent.remove(p.getUID());
    }

    private void disconnect(Player p) {
        players.disconnect(p, "Connection Timed Out");
        cleanUp(p);
    }

    @Override
    public void run() { // Will update each second :D
        while (true) {
        	int time = (int) getMiliTime();
            for (Short p : lastRecive.keySet())
                if (lastRecive.get(p) - time >= 10) {
                    Player plr = players.getPlayer(p);
                    disconnect(plr);
                    Reference<Player> r = new WeakReference<Player>(plr);
                    plr = null;
                    while (r.get() != null)
                        System.gc();
                }
            try {
                TimeoutManager.sleep(1000);
            } catch (InterruptedException e) {}
        }
    }

    public void keepAlive(Player p, int ID) {
                lastSent.remove(p.getUID());
                lastRecive.remove(p.getUID());
                lastRecive.put(p.getUID(), (int) getMiliTime());
                update(p);
    }

}
