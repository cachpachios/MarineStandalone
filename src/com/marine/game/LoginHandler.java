package com.marine.game;

import java.util.Collections;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import com.marine.StandaloneServer;
import com.marine.net.Client;
import com.marine.player.AbstractPlayer;
import com.marine.player.IPlayer;
import com.marine.player.PlayerID;
import com.marine.util.Position;
import com.marine.world.World;

public class LoginHandler {

	private Map<UUID, IPlayer> loggingInClients;
	
	private final StandaloneServer server;
	
	public LoginHandler(StandaloneServer s, World w, Position spawnLocation) {
		server = s;
		loggingInClients = Collections.synchronizedMap(new ConcurrentHashMap<UUID, IPlayer>());
	}
	
	public void join(String name, Client c) {
		UUID uuid = UUID.randomUUID(); //TODO: Retrive from Mojang
		loggingInClients.put(uuid, new AbstractPlayer(new PlayerID(name, uuid), c));
	}
	
	

}

