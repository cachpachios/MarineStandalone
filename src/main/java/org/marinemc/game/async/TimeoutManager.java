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

import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.marinemc.game.PlayerManager;
import org.marinemc.game.player.Player;
import org.marinemc.net.play.KeepAlivePacket;
import org.marinemc.server.Marine;

public class TimeoutManager {
    private final Map<Short, Integer> lastReceived; // Contains last recived in seconds
    private final Map<Short, Integer> lastSent; // Contains last sent KeepAlivePacketID

    public TimeoutManager() {
        this.lastReceived = Collections.synchronizedMap(new ConcurrentHashMap<Short, Integer>());
        this.lastSent = Collections.synchronizedMap(new ConcurrentHashMap<Short, Integer>());
    }

    private int randomInt(final int max) {
        return (int) (Math.random() * max);
    }

    private long getMiliTime() {
        return (int) (System.nanoTime() / 1000 / 1000);
    }

    public void addPlayerToManager(final Player p) {
        lastReceived.put(p.getUID(), 0);
    }

    private void update(final Player p) {
        final int id = randomInt(32);
        p.getClient().sendPacket(new KeepAlivePacket(id));
        lastSent.put(p.getUID(), id);
    }


    public void cleanUp(final Player p) {
        final short uid = p.getUID();
        lastReceived.remove(uid);
        lastSent.remove(uid);
    }
    
    private void clean(Short uid) {
        lastReceived.remove(uid);
        lastSent.remove(uid);
    }

    private void disconnect(final Player p) {
        Marine.getServer().getPlayerManager().disconnect(p);
    }
    
    public void run() { // Will update each second :D
        int time = (int) getMiliTime();
        for (final Short uid : lastReceived.keySet()) 
        	if(!Marine.getServer().getPlayerManager().isPlayerOnline(uid))
        		clean(uid);
        	else
            if (lastReceived.get(uid) - time >= 10) {
                Player plr = Marine.getServer().getPlayerManager().getPlayer(uid);
                disconnect(plr);
            }
    }

    public void keepAlive(final Player p) {
        final short uid = p.getUID();
        lastSent.remove(uid);
        // It is automatically removed by default :)
        lastReceived.put(uid, (int) getMiliTime());
        update(p);
    }


}
