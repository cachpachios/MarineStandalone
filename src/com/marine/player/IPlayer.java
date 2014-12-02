package com.marine.player;

import java.util.UUID;

import com.marine.net.Client;
import com.marine.util.Location;
import com.marine.util.Position;
import com.marine.world.World;

public interface IPlayer {

	public PlayerID getInfo();
	
	public String getName();
	
	public Client getClient();
	
	public World getWorld();
	
	public Location getLocation();
	public Position getRealtivePosition();
	
	
	public UUID getUUID();
	
	public void update();
}
