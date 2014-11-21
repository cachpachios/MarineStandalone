package com.marineapi.player;

import java.util.UUID;

public class PlayerID {
	public String name;
	public UUID uuid;
	
	public PlayerID(String name, UUID uuid) {
		this.name = name;
		this.uuid = uuid;
	}
	
	public String getName() {
		return name;
	}
	
	public UUID getUUID() {
		return uuid;
	}
}
