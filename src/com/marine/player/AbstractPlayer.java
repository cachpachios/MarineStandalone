package com.marine.player;

import java.util.UUID;

import com.marine.net.Client;

public class AbstractPlayer implements IPlayer{ // Used for communication with client/login process
	private PlayerID id;
	
	private PlayerAbilites abilites;
	
	private Client client;
	
	public AbstractPlayer(PlayerID id, Client c, PlayerAbilites abilites) {
		this.client = c;
		this.abilites = abilites;
		this.id = id;
	}
	
	public String getName() {
		return id.getName();
	}

	@Override
	public PlayerID getInfo() {
		return id;
	}

	public PlayerAbilites getAbilites() {
		return abilites;
	}
	
	@Override
	public Client getClient() {
		return client;
	}

	@Override
	public UUID getUUID() {
		return id.getUUID();
	}

	@Override
	public void update() {
		if(abilites.needUpdate())
			client.sendPacket(abilites.getPacket());
	}	
}