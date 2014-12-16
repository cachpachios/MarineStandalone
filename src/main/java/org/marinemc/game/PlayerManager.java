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

package org.marinemc.game;

import org.marinemc.StandaloneServer;
import org.marinemc.game.async.ChatManager;
import org.marinemc.game.async.TimeoutManager;
import org.marinemc.net.Client;
import org.marinemc.net.Packet;
import org.marinemc.net.States;
import org.marinemc.net.play.clientbound.JoinGamePacket;
import org.marinemc.net.play.clientbound.KickPacket;
import org.marinemc.player.AbstractPlayer;
import org.marinemc.player.IPlayer;
import org.marinemc.player.Player;

import java.lang.ref.Reference;
import java.lang.ref.SoftReference;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Fozie
 */
public class PlayerManager {

    private final StandaloneServer server;
    // private Set<Player> allPlayers;
    // private Map<UUID, Player> playerIDs;
    private final Map<Short, Player> uids;
    private final Map<String, Player> playerNames;

    private LoginHandler loginManager;
    private TimeoutManager timeout;

    private MovementManager movement;
    private ChatManager chat;

    public PlayerManager(StandaloneServer server) {
        this.server = server;
        loginManager = new LoginHandler(this, this.server.getWorldManager().getMainWorld(), this.server.getWorldManager().getMainWorld().getSpawnPoint());
        // allPlayers = Collections.synchronizedSet(new HashSet<Player>());
        // playerIDs = Collections.synchronizedMap(new ConcurrentHashMap<UUID, Player>());
        uids = Collections.synchronizedMap(new ConcurrentHashMap<Short, Player>());
        playerNames = Collections.synchronizedMap(new ConcurrentHashMap<String, Player>());

        timeout = new TimeoutManager(this);
        chat = new ChatManager(this);
        movement = new MovementManager(this);

        // Will run it at a FIXED rater, rather than dynamic rate
        new Timer("timeoutManager").scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                timeout.run();
            }
        }, 0, 1000);
    }

    public ChatManager getChat() {
        return chat;
    }

    public void broadcastPacket(Packet packet) {
        for (final Player p : uids.values())
            p.getClient().sendPacket(packet);
    }

    public void updateThemAll() {
        for (final Player p : uids.values()) {
            p.update();
            p.sendTime();
        }
    }

    public StandaloneServer getServer() {
        return server;
    }

    public boolean isPlayerOnline(String name) {
        return playerNames.containsKey(name);
    }

    public boolean isPlayerOnline(UUID uid) {
        for (final Player player : uids.values())
            if (player.getUUID().equals(uid))
                return true;
        return false;
    }

    public boolean isPlayerOnline(short uid) {
        return uids.containsKey(uid);
    }

    protected void putPlayer(Player p) {
        if (uids.containsValue(p))
            return;
        uids.put(p.getUID(), p);
        playerNames.put(p.getName(), p);
    }

    public Player getPlayer(UUID uuid) {
        for (final Player player : uids.values())
            if (player.getUUID().equals(uuid))
                return player;
        return null;
    }

    public Player getPlayer(short uid) {
        if (uids.containsKey(uid))
            return uids.get(uid);
        return null;
    }

    public Player getPlayer(String displayName) {
        if (!playerNames.containsKey(displayName))
            return null;
        return playerNames.get(displayName);

    }

    protected void removePlayer(Player p) {
        {
            synchronized (uids) {
                synchronized (playerNames) {
                    if (uids.containsValue(p)) {
                        uids.remove(p.getUID());
                        playerNames.remove(p.getName());
                    }
                }
            }
        } // Sync end
    }

    public void tickAllPlayers() {
        synchronized (uids.values()) {
            for (IPlayer p : uids.values())
                if (p instanceof Player) {
                    Player pl = (Player) p;
                    pl.tick();
                }
        }
    }

    public LoginHandler getLoginManager() {
        return loginManager;
    }

    public Collection<Player> getPlayers() {
        return uids.values();
    }

    protected Player passFromLogin(IPlayer player) {
        if (player instanceof Player) {
            putPlayer((Player) player);
            return (Player) player;
        } else if (player instanceof AbstractPlayer) {
            Player p = new Player((AbstractPlayer) player, server.getGamemode());
            putPlayer(p);
            return p;
        }
        return null; // This shoulnt happening if id does its wierd :S
    }

    public Player getPlayerByClient(Client c) {
        for (Player player : getPlayers()) {
            if (player.getClient() == c)
                return player;
        }
        return null;
    }

    private void cleanUp(Player p) {
        removePlayer(p);
        timeout.cleanUp(p);
        server.getNetwork().cleanUp(p.getClient());
    }

    public void disconnect(Player p, String reason) {
        if (p == null)
            return;
        p.disconnect(reason);
        p.getClient().sendPacket(new KickPacket(
                (reason.length() > 0) ? reason : "Kicked"
        ));
        cleanUp(p);
    }

    public void disconnect_timeout(Player p, String reason) {
        if (p == null)
            return;
        p.timeoutDisconnect();
        p.getClient().sendPacket(new KickPacket(
                (reason.length() > 0) ? reason : "Timed out"
        ));
        cleanUp(p);
    }

    public void joinGame(Player p) {
        if (p.getClient().getState() != States.INGAME) {
            cleanUp(p);
            return;
        }

        timeout.addPlayerToManager(p);

        p.getClient().sendPacket(new JoinGamePacket(p));

        p.sendPosition();
        
        p.sendMapData(p.getWorld().getChunks(0, 0, 2, 2));

        p.sendAbilites();

        p.sendPosition();
        
        p.sendTime();
        
        p.loginPopulation();
    }

    public void keepAlive(short uid, int ID) {
        if (uid == -1)
            return;
        timeout.keepAlive(getPlayer(uid));
    }

    protected void forceGC(Player player) {
        Reference<Player> ref = new SoftReference<>(player);
        player = null;
        while (ref.get() != null) {
            System.gc();
        }
    }

    public MovementManager getMovementManager() {
        return movement;
    }

    public boolean hasAnyPlayers() {
        return uids.size() > 0;
    }
}
