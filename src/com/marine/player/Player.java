package com.marineapi.player;

import java.util.UUID;

import com.marineapi.net.Client;
import com.marineapi.net.States;
import com.marineapi.util.Position;
import com.marineapi.world.World;
import com.marineapi.world.entity.Entity;

public class Player extends Entity {
	
	
	public Player(Client connection, PlayerID id, int entityID, World world, Position pos) {
		super(entityID, world, pos);
		this.connection = connection;
		this.id = id;
	}

	private Client connection;
	private PlayerID id;
	
	public UUID getUUID() {
		return id.getUUID();
	}
	
	public Client getClient() {
		return connection;
	}
	
	public States getCurrentState() {
		return connection.getState();
	}

	@Override
	public int getSendDistance() { // TODO Make this more dynamic and abilites to use with client render distance
		return 200;
	}
	
}
