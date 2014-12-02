package com.marine.game;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import com.marine.StandaloneServer;
import com.marine.net.States;
import com.marine.player.AbstractPlayer;
import com.marine.player.IPlayer;
import com.marine.player.Player;

public class PlayerManager {
	
	private List<IPlayer> allPlayers;
	
	private Map<UUID, Player> playerIDs;
	private Map<String, Player> playerNames;
	
	
	private LoginHandler loginManager;
	
	private final StandaloneServer server;
	
	public void updateThemAll() {
		for(IPlayer p : allPlayers)
			p.update();
	}
	
	public PlayerManager(StandaloneServer server) {
		this.server = server;
		loginManager = new LoginHandler(this, this.server.getWorldManager().getMainWorld(), this.server.getWorldManager().getMainWorld().getSpawnPoint());
		allPlayers = Collections.synchronizedList(new ArrayList<IPlayer>());
		playerIDs = Collections.synchronizedMap(new ConcurrentHashMap<UUID, Player>());
		playerNames = Collections.synchronizedMap(new ConcurrentHashMap<String, Player>());
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
	
	public IPlayer getPlayer(UUID uuid) {
		if(!playerIDs.containsKey(uuid))
			return null;
		return playerIDs.get(uuid);
	}
	
	public IPlayer getPlayer(String displayName) {
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

	protected Player passFromLogin(IPlayer player) {
		if(player instanceof Player) {
			putPlayer((Player) player);
			return (Player) player;
		}
		else
			if(player instanceof AbstractPlayer) {
				Player p = new Player((AbstractPlayer) player);
				putPlayer(p);
				return p;
			}
		return null; // This shoulnt happening if id does its wierd :S
	}

	protected void cleanUp(Player p) {
		removePlayer(p);
		server.getNetwork().cleanUp(p.getClient());
	}
	
	public void joinGame(Player p) {
		if(p.getClient().getState() != States.INGAME) {
			cleanUp(p); return;
		}
	 	
		
		
	}
	
}
