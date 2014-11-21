package com.marineapi.player;

import java.util.UUID;

import com.marineapi.net.Client;

public class Player { //TODO: Extend LivingEntity
	private Client connection;
	private PlayerID id;
	
	public UUID getUUID() {
		return id.getUUID();
	}
	
	public Client getConnectedClient() {
		return connection;
	}
}
