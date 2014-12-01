package com.marine.player;

import java.util.UUID;

import com.marine.net.Client;
import com.marine.util.Location;
import com.marine.world.World;
import com.marine.world.entity.Entity;

public class Player extends Entity implements IPlayer{
	
	private AbstractPlayer absPlayer;
	
	public Player(Client connection, PlayerID id, int entityID, World world, Location pos) {
		super(entityID, world, pos);
		this.absPlayer = new AbstractPlayer(id, connection);
	}
	
	@Override
	public String getName() {
		return absPlayer.getName();
	}
	
	@Override
	public int getSendDistance() { // TODO Make this more dynamic and abilites to use with client render distance
		return 200;
	}

	@Override
	public void update() {
	}

	@Override
	public PlayerID getInfo() {
		return absPlayer.getInfo();
	}

	@Override
	public Client getClient() {
		// TODO Auto-generated method stub
		return absPlayer.getClient();
	}

	@Override
	public UUID getUUID() {
		return absPlayer.getUUID();
	}
	
}
