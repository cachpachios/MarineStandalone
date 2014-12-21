package org.marinemc.game.player;

import java.util.UUID;

import org.marinemc.net.Client;
import org.marinemc.net.Packet;
import org.marinemc.util.Location;
import org.marinemc.world.Gamemode;
import org.marinemc.world.entity.Entity;
import org.marinemc.world.entity.EntityType;

public class Player extends Entity implements IPlayer{
	
	private final short 	uid;
	private final UUID 		uuid;
	private final String	name;
	
	private Gamemode currentGameMode;
	
	private final Client client;
	
	public Player(Client client, short uid, UUID uuid, String name, Location location, Gamemode gm) {
		super(EntityType.PLAYER, Entity.generateEntityID(), location);
		this.client = client;
		this.uid = uid;
		this.uuid = uuid;
		this.name = name;
		this.currentGameMode = gm;
	}

	@Override
	public short getUID() {
		return uid;
	}

	@Override
	public UUID getUUID() {
		return uuid;
	}

	@Override
	public String getUserName() {
		return name;
	}

	public void kick(String reason) {
		//TODO
	}

	@Override
	public int getSendDistance() {
		return 100;
	}

	@Override
	public void update() {
		//TODO
	}
	
	public void updateGamemode(Gamemode gm) {
		if(this.currentGameMode != gm) {
			//TODO: Send packet to client
			
			currentGameMode = gm;
		}
	}

	public Gamemode getGamemode() {
		return currentGameMode;
	}

	public Client getClient() {
		return client;
	}

	public boolean isInCreativeMode() {
		return getGamemode() == Gamemode.CREATIVE;
	}
}
