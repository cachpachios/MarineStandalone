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

package com.marine.game;

import com.marine.StandaloneServer;
import com.marine.game.async.ChatManager;
import com.marine.game.async.TimeoutManager;
import com.marine.net.Client;
import com.marine.net.Packet;
import com.marine.net.States;
import com.marine.net.play.clientbound.JoinGamePacket;
import com.marine.net.play.clientbound.KickPacket;
import com.marine.player.AbstractPlayer;
import com.marine.player.IPlayer;
import com.marine.player.Player;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class PlayerManager {

    private final StandaloneServer server;
    // private Set<Player> allPlayers;
    // private Map<UUID, Player> playerIDs;
    private final Map<Short, Player> uids;
    private final Map<String, Player> playerNames;
    private short uid = -1;
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

        timeout.start();
    }

    public ChatManager getChat() {
        return chat;
    }

    public void broadcastPacket(Packet packet) {
        for (Player p : uids.values())
            p.getClient().sendPacket(packet);
    }

    public void updateThemAll() {
        for (Player p : uids.values()) {
            p.update();
            p.sendTime();
        }
    }

    public short getNextUID() {
        return ++uid;
    }

    public StandaloneServer getServer() {
        return server;
    }

    public boolean isPlayerOnline(String name) {
        return playerNames.containsKey(name);
    }

    public boolean isPlayerOnline(UUID uid) {
        for (Player player : uids.values())
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
        // al.add(p);
        //playerIDs.put(p.getUUID(), p);
        uids.put(p.getUID(), p);
        playerNames.put(p.getName(), p);
    }

    public Player getPlayer(UUID uuid) {
        // if (!playerIDs.containsKey(uuid))
        //    return null;
        // return playerIDs.get(uuid);
        for (Player player : uids.values())
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
        //TODO: send player remove packet to every other client
        server.getNetwork().cleanUp(p.getClient());
        //forceGC(p); TODO: Fix this :(
    }

    public void disconnect(Player p, String msg) {
        if (p == null)
            return;
        p.disconnect();
        p.getClient().sendPacket(new KickPacket(msg));
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

        p.sendMapData(p.getWorld().getChunks(0, 0, 7, 7));

        p.sendAbilites();

        p.sendPosition();
        p.sendTime();
        //p.loginPopulation();
    }

    public void keepAlive(short uid, int ID) {
        if (uid == -1)
            return;
        timeout.keepAlive(getPlayer(uid), ID);
    }

    // TODO: Make this work, its just freezes the server now

//    public void forceGC(Player player) {
//        WeakReference ref = new WeakReference<>(player);
//        player = null;
//        while (ref.get() != null) {
//            System.gc();
//        }
//    }

    public MovementManager getMovementManager() {
        return movement;
    }

    public boolean hasAnyPlayers() {
        return uids.size() > 0;
    }
}
