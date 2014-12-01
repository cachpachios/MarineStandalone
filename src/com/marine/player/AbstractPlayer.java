package com.marine.player;

import java.util.UUID;

import com.marine.net.Client;

public  class AbstractPlayer implements IPlayer{ // Used for communication with client/login process
	private PlayerID id;
	
	private Client client;
	
	public AbstractPlayer(PlayerID id, Client c) {
		this.client = c;
		this.id = id;
	}
	
	public String getName() {
		return id.getName();
	}

	@Override
	public PlayerID getInfo() {
		return id;
	}

	@Override
	public Client getClient() {
		return client;
	}

	@Override
	public UUID getUUID() {
		return id.getUUID();
	}
	
	
	
	
	
}
