package com.marine.player;

import java.util.UUID;

import com.marine.StandaloneServer;
import com.marine.net.Client;
import com.marine.util.Location;
import com.marine.util.Position;
import com.marine.world.World;

public class AbstractPlayer implements IPlayer{ // Used for communication with client/login process
	private final StandaloneServer s;
	
	private PlayerID id;
	
	private PlayerAbilities abilites;
	
	private Client client;
	
	private Location location;
	
	private World w;
	
	public AbstractPlayer(StandaloneServer server, World w, PlayerID id, Client c, PlayerAbilities abilites, Location spawnLocation) {
		this.s = server;
		this.w = w;
		this.client = c;
		this.location = spawnLocation;
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

	public PlayerAbilities getAbilites() {
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

	@Override
	public World getWorld() {
		return w;
	}

	@Override
	public Location getLocation() {
		return location;
	}

	@Override
	public Position getRealtivePosition() {
		return location.getRelativePosition();
	}

	public StandaloneServer getServer() {
		return s;
	}	
}