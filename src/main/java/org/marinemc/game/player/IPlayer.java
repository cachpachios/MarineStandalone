package org.marinemc.game.player;

import java.util.UUID;
/**
 * To help to create a brige between diffrent 'Players'
 * The gameclient non entity players and Players(players with an ingame entity attached)
 * 
 * @author Fozie
 */
public interface IPlayer {
	/**
	 * 
	 * @return
	 */
	public short getUID();
	
	public UUID getUUID();
	public String getUserName();
	
}
