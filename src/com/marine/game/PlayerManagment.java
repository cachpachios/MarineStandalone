package com.marine.game;

import java.util.ArrayList;
import java.util.List;

import com.marine.StandaloneServer;
import com.marine.player.Player;

public class PlayerManagment {
	
	public List<Player> allPlayers;
	
	private final StandaloneServer server;
	
	public PlayerManagment(StandaloneServer server) {
		this.server = server;
		
		allPlayers = new ArrayList<Player>();
	}
	
	public void putPlayer(Player p) {
		allPlayers.add(p);
	}
	
	public void tickAllPlayers() {
		for(Player p : allPlayers)
			p.tick();
	}
	
}
