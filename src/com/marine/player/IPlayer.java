package com.marine.player;

import java.util.UUID;

import com.marine.net.Client;

public interface IPlayer {

	public PlayerID getInfo();
	
	public String getName();
	
	public Client getClient();
	
	public UUID getUUID();
}
