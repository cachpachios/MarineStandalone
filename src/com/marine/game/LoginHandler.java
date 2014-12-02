package com.marine.game;

import java.util.Collections;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import com.marine.net.Client;
import com.marine.net.login.LoginSucessPacket;
import com.marine.player.AbstractPlayer;
import com.marine.player.IPlayer;
import com.marine.player.PlayerAbilites;
import com.marine.player.PlayerID;
import com.marine.util.Location;
import com.marine.util.Position;
import com.marine.world.World;

public class LoginHandler {

	private Map<UUID, IPlayer> loggingInClients;
	
	private final PlayerManager players;
	
	private Location spawnLocation;
	
	public LoginHandler(PlayerManager playerManager, World w, Position spawnLocation) {
		this.spawnLocation = new Location(spawnLocation, w);
	
		players = playerManager;
		loggingInClients = Collections.synchronizedMap(new ConcurrentHashMap<UUID, IPlayer>());
	}
	
	public class LoginResponse {
		public final IPlayer player;
		public final String response;
		
		public LoginResponse(IPlayer p) {
			player = p;
			response = null;
		}
		
		public LoginResponse(String responseString) {
			player = null;
			response = responseString;
		}
		
		public boolean succeed() {
			return player !=  null;
		}
		
	}
	
	public LoginResponse preJoin(String name, Client c) { // Returns null if login succeded, otherwise makes LoginInterceptor drop the client
		UUID uuid = UUID.randomUUID(); //TODO: Retrive from Mojang
		
		if(players.isPlayerOnline(name))
			return new LoginResponse("Failed to login player is allready connected.");
		if(players.isPlayerOnline(uuid))
			return new LoginResponse("Failed to login player is allready connected.");
		
		//TODO: Check if player is banned incase they are drop them.
		
		synchronized(loggingInClients) {
			loggingInClients.put(uuid, new AbstractPlayer(new PlayerID(name, uuid), c, new PlayerAbilites(false, false, false, 10, 10), spawnLocation));
		}
		
		return null;
	}
	

	public void passPlayer(IPlayer player) { //TODO: Encryption
		players.passFromLogin(player);
		
		player.getClient().sendPacket(new LoginSucessPacket());
	}

}

