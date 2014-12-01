package com.marine.player;

import com.marine.net.Client;
import com.marine.net.States;
import com.marine.util.Position;
import com.marine.world.World;
import com.marine.world.entity.Entity;

import java.util.UUID;

public class Player extends Entity {

    private final Client connection;
    private final PlayerID id;

    public Player(Client connection, PlayerID id, int entityID, World world, Position pos) {
		super(entityID, world, pos);
		this.connection = connection;
		this.id = id;
	}
	
	public String getName() {
		return id.getName();
	}
	
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

	@Override
	public void update() {
		// TODO Auto-generated method stub
		
	}
	
}
