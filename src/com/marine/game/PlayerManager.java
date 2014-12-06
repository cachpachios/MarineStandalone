package com.marine.game;

import com.marine.StandaloneServer;
import com.marine.game.async.TimeoutManager;
import com.marine.net.Client;
import com.marine.net.States;
import com.marine.net.play.clientbound.JoinGamePacket;
import com.marine.net.play.clientbound.KickPacket;
import com.marine.net.play.clientbound.MapChunkPacket;
import com.marine.player.AbstractPlayer;
import com.marine.player.IPlayer;
import com.marine.player.Player;
import com.marine.world.chunk.Chunk;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class PlayerManager {
	
	private List<Player> allPlayers;
	
	private Map<UUID, Player> playerIDs;
	private Map<String, Player> playerNames;
	
	
	
	private LoginHandler loginManager;
	
	private final StandaloneServer server;
	
	private TimeoutManager timeout;
	
	public void updateThemAll() {
		for(Player p : allPlayers)
			p.update();
	}
	
	public PlayerManager(StandaloneServer server) {
		this.server = server;
		loginManager = new LoginHandler(this, this.server.getWorldManager().getMainWorld(), this.server.getWorldManager().getMainWorld().getSpawnPoint());
		allPlayers = Collections.synchronizedList(new ArrayList<Player>());
		playerIDs = Collections.synchronizedMap(new ConcurrentHashMap<UUID, Player>());
		playerNames = Collections.synchronizedMap(new ConcurrentHashMap<String, Player>());
		timeout = new TimeoutManager(this);
		timeout.start();
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
		if(allPlayers.contains(p))
			return;
		
		allPlayers.add(p);
		playerIDs.put(p.getUUID(), p);
		playerNames.put(p.getName(), p);
	}
	
	public Player getPlayer(UUID uuid) {
		if(!playerIDs.containsKey(uuid))
			return null;
		return playerIDs.get(uuid);
	}
	
	public Player getPlayer(String displayName) {
		if(!playerNames.containsKey(displayName))
			return null;
		return playerNames.get(displayName);
		
	}
	
	protected void removePlayer(Player p) {
		synchronized(allPlayers){ synchronized(playerIDs){synchronized(playerNames){
			if(allPlayers.contains(p)) {
				allPlayers.remove(p);
				playerIDs.remove(p.getUUID());
				playerNames.remove(p.getName());
			}
		}}} // Sync end
	}
	
	public void tickAllPlayers() {
		synchronized(allPlayers) {
			for(IPlayer p : allPlayers)
				if(p instanceof Player) {
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
		if(player instanceof Player) {
			putPlayer((Player) player);
			return (Player) player;
		}
		else
			if(player instanceof AbstractPlayer) {
				Player p = new Player((AbstractPlayer) player, server.getGamemode());
				putPlayer(p);
				return p;
			}
		return null; // This shoulnt happening if id does its wierd :S
	}

    public Player getPlayerByClient(Client c) {
        for(Player player : getPlayers()) {
            if(player.getClient() == c)
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
		p.getClient().sendPacket(new KickPacket(msg));
		cleanUp(p);
	}
	
	public void joinGame(Player p) {
		if(p.getClient().getState() != States.INGAME) {
			cleanUp(p); return;
		}
		
		timeout.addPlayerToManager(p);
	 	
		p.getClient().sendPacket(new JoinGamePacket(p));
	
		p.sendAbilites();
		
		p.sendPostion();
	}

	public void keepAlive(String name, int ID) {
		if(name == null)
			return;
		timeout.keepAlive(getPlayer(name),ID);
		
	}
}
