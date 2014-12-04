package com.marine.game;

import com.marine.game.async.ChatManagment;
import com.marine.net.Client;
import com.marine.net.States;
import com.marine.net.login.LoginSucessPacket;
import com.marine.net.play.clientbound.PlayerListHeaderPacket;
import com.marine.player.*;
import com.marine.util.Location;
import com.marine.util.Position;
import com.marine.util.UUIDHandler;
import com.marine.world.World;

import java.util.UUID;

public class LoginHandler {
	
	private final PlayerManager playerManager;
	
	private Location spawnLocation;
	
	public LoginHandler(PlayerManager playerManager, World w, Position spawnLocation) {
		this.spawnLocation = new Location(spawnLocation, w);
	
		this.playerManager = playerManager;
		//players = Collections.synchronizedMap(new ConcurrentHashMap<Client, IPlayer>());
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
	
	private LoginResponse preJoin(String name, Client c) { // Returns null if login succeded, otherwise makes LoginInterceptor drop the client
		UUID uuid = UUIDHandler.getUUID(name); //UUID.randomUUID();
		
		if(uuid == null) {
			uuid = UUID.randomUUID();
		}
		
		if(playerManager.isPlayerOnline(name))
			return new LoginResponse("Failed to login player is allready connected.");
		if(playerManager.isPlayerOnline(uuid))
			return new LoginResponse("Failed to login player is allready connected.");
		
		//TODO: Check if player is banned in that case drop them.
		
		IPlayer p = new AbstractPlayer(playerManager.getServer(),playerManager.getServer().getWorldManager().getMainWorld(), new PlayerID(name, uuid), c, new PlayerAbilities(false, false, false, 0.1f, 0.2f), spawnLocation);

		return new LoginResponse(p);
	}
	

	public void passPlayer(IPlayer player) { //TODO: Encryption
		Player p = playerManager.passFromLogin(player);
		
		p.getClient().sendPacket(new LoginSucessPacket(p));
		
		p.getClient().setState(States.INGAME);
		
		playerManager.joinGame(p);

        ChatManagment.getInstance().sendJoinMessage(p);
        p.getClient().sendPacket(new PlayerListHeaderPacket("&cWelcome to the server", "&6" + player.getName()));
	}


	public LoginResponse login(String name, Client c) {
		return preJoin(name, c);
	}

}

