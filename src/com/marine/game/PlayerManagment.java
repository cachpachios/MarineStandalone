package com.marine.game;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import com.marine.StandaloneServer;
import com.marine.player.Player;

public class PlayerManagment {
	
	private List<Player> allPlayers;
	
	private Map<UUID, Player> playerIDs;
	private Map<String, Player> playerNames;
	
	public void updateThemAll() {
		
		for(Player p : allPlayers)
			p.update();
	}
	
	public PlayerManagment() {
		allPlayers = Collections.synchronizedList(new ArrayList<Player>());
		playerIDs = Collections.synchronizedMap(new ConcurrentHashMap<UUID, Player>());
		playerNames = Collections.synchronizedMap(new ConcurrentHashMap<String, Player>());
	}
	
	public void putPlayer(Player p) {
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
	
	public void tickAllPlayers() {
		for(Player p : allPlayers)
			p.tick();
	}
	
}
