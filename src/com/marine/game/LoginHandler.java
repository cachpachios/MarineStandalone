package com.marine.game;

import java.util.Collections;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import com.marine.net.Client;
import com.marine.net.States;
import com.marine.net.login.LoginSucessPacket;
import com.marine.player.AbstractPlayer;
import com.marine.player.IPlayer;
import com.marine.player.Player;
import com.marine.player.PlayerAbilites;
import com.marine.player.PlayerID;
import com.marine.util.Location;
import com.marine.util.Position;
import com.marine.util.UUIDHandler;
import com.marine.world.World;

public class LoginHandler {
	
	private Map<Client, IPlayer> players;
	
	private final PlayerManager playerManager;
	
	private Location spawnLocation;
	
	public LoginHandler(PlayerManager playerManager, World w, Position spawnLocation) {
		this.spawnLocation = new Location(spawnLocation, w);
	
		this.playerManager = playerManager;
		players = Collections.synchronizedMap(new ConcurrentHashMap<Client, IPlayer>());
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
		UUID uuid = UUIDHandler.getUUID(name); //UUID.randomUUID();
		
		if(playerManager.isPlayerOnline(name))
			return new LoginResponse("Failed to login player is allready connected.");
		if(playerManager.isPlayerOnline(uuid))
			return new LoginResponse("Failed to login player is allready connected.");
		
		//TODO: Check if player is banned in that case drop them.
		
		IPlayer p = new AbstractPlayer(playerManager.getServer(),playerManager.getServer().getWorldManager().getMainWorld(), new PlayerID(name, uuid), c, new PlayerAbilites(false, false, false, 10, 10), spawnLocation);
		
			synchronized(players) {
				players.put(c, p);
			}
			
		return new LoginResponse(p);
	}
	

	public void passPlayer(Client player) { //TODO: Encryption
		Player p = playerManager.passFromLogin(players.get(player));
		
		p.getClient().sendPacket(new LoginSucessPacket(p));
		
		p.getClient().setState(States.INGAME);
		
		playerManager.joinGame(p);
	}

	public void loginDone(Client c) {
		if(players.containsKey(c))
			players.remove(c);
	}
	
	public boolean clientExists(Client c) {
		return players.containsKey(c);
	}

}

