package com.marine.game;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

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

public class PlayerManager {

    private final StandaloneServer server;
    private List<Player> allPlayers;
    private Map<UUID, Player> playerIDs;
    private Map<String, Player> playerNames;
    private LoginHandler loginManager;
    private TimeoutManager timeout;
    
    private MovmentManager movment;
    private ChatManager chat;

    public PlayerManager(StandaloneServer server) {
        this.server = server;
        loginManager = new LoginHandler(this, this.server.getWorldManager().getMainWorld(), this.server.getWorldManager().getMainWorld().getSpawnPoint());
        allPlayers = Collections.synchronizedList(new ArrayList<Player>());
        playerIDs = Collections.synchronizedMap(new ConcurrentHashMap<UUID, Player>());
        playerNames = Collections.synchronizedMap(new ConcurrentHashMap<String, Player>());
        
        timeout = new TimeoutManager(this);
        chat = new ChatManager(this);
        movment = new MovmentManager(this);
        
        timeout.start();
    }
    
    public ChatManager getChat() {
    	return chat;
    }

    public void brodcastPacket(Packet packet) {
    	for(Player p : allPlayers)
    		p.getClient().sendPacket(packet);
    }
    
    public void updateThemAll() {
        for (Player p : allPlayers) {
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
        return playerIDs.containsKey(uid);
    }

    protected void putPlayer(Player p) {
        if (allPlayers.contains(p))
            return;

        allPlayers.add(p);
        playerIDs.put(p.getUUID(), p);
        playerNames.put(p.getName(), p);
    }

    public Player getPlayer(UUID uuid) {
        if (!playerIDs.containsKey(uuid))
            return null;
        return playerIDs.get(uuid);
    }

    public Player getPlayer(String displayName) {
        if (!playerNames.containsKey(displayName))
            return null;
        return playerNames.get(displayName);

    }

    protected void removePlayer(Player p) {
        synchronized (allPlayers) {
            synchronized (playerIDs) {
                synchronized (playerNames) {
                    if (allPlayers.contains(p)) {
                        allPlayers.remove(p);
                        playerIDs.remove(p.getUUID());
                        playerNames.remove(p.getName());
                    }
                }
            }
        } // Sync end
    }

    public void tickAllPlayers() {
        synchronized (allPlayers) {
            for (IPlayer p : allPlayers)
                if (p instanceof Player) {
                    Player pl = (Player) p;
                    pl.tick();
                }
        }
    }

    public LoginHandler getLoginManager() {
        return loginManager;
    }

    public List<Player> getPlayers() {
        return allPlayers;
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
    }

    public void disconnect(Player p, String msg) {
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
        p.loginPopulation();
    }

    public void keepAlive(String name, int ID) {
        if (name == null)
            return;
        timeout.keepAlive(getPlayer(name), ID);

    }

	public MovmentManager getMovmentManager() {
		return movment;
	}

	public boolean hasAnyPlayers() {
		return !allPlayers.isEmpty();
	}
}
