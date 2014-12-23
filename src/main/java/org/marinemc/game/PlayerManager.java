package org.marinemc.game;

import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import org.marinemc.game.inventory.PlayerInventory;
import org.marinemc.game.player.Player;
import org.marinemc.game.player.UIDGenerator;
import org.marinemc.net.Client;
import org.marinemc.net.States;
import org.marinemc.net.login.LoginPacket;
import org.marinemc.net.login.LoginSucessPacket;
import org.marinemc.net.play.clientbound.ChatPacket;
import org.marinemc.net.play.clientbound.JoinGamePacket;
import org.marinemc.server.Marine;
import org.marinemc.util.Location;
import org.marinemc.util.annotations.Cautious;
import org.marinemc.util.annotations.Hacky;
import org.marinemc.util.mojang.UUIDHandler;
import org.marinemc.util.wrapper.StringWrapper;
import org.marinemc.world.Gamemode;
import org.marinemc.world.entity.Entity;
import org.marinemc.world.entity.EntityType;

/**
 * The place where players are saved and accessed.
 * 
 * @author Fozie
 * @param <synchoronized>
 */
public class PlayerManager<synchoronized> {
	private Map<Short, Player> players;
	
	private Map<String, Short> namePointers;
	
	public PlayerManager() {
		players = new ConcurrentHashMap<Short, Player>();
		namePointers = new ConcurrentHashMap<String, Short>();
	}
	
	/**
	 * Called when the LoginPacket was intercepted
	 * 
	 * @param connected The client that are connecting
	 */
	public String login(final Client client, final LoginPacket packet) {
		//TODO: Encryption and Compression
		
		UUID uuid;
		String name;
		if (Marine.getServer().isOfflineMode()) {
			uuid = UUIDHandler.getUuidOfflineMode(new StringWrapper(packet.name));
			name = packet.name;
		} else {
			uuid = UUIDHandler.getUUID(packet.name);
			name = UUIDHandler.getName(uuid);
		}

		if (uuid == null) {
			uuid = UUID.randomUUID();
		}
	       
		// TODO This add the encryption stuff etc.. And then separate the following code in to another methoud that is called when encryption response is intercepted.

		Player p = new Player(
				EntityType.PLAYER,
				Entity.generateEntityID(),
				new Location(Marine.getMainWorld(),0,5,0), //TODO: Get an location from file or generate spawnpoint
				UIDGenerator.instance().getUID(name),
				uuid,
				name, 
				0f,
				0,
				Gamemode.SURVIVAL,// TODO: Get from file or get standardgamemode
				2,
				2,
				true,
				false,
				true,
				new PlayerInventory((byte) 0),
				client
		);
		
		//TODO : Some banned/other stuff check :p
		
		client.sendPacket(new LoginSucessPacket(p)); // Send the LoginSuccessPacket
		
		// Begin the join game process
		
		client.setState(States.INGAME);
		
		p.getClient().sendPacket(new JoinGamePacket(p));
		
		p.updateAbilites();
		
		p.sendPositionAndLook();
		
		p.sendMapBulk(Marine.getServer().getWorldManager().getMainWorld(), Marine.getServer().getWorldManager().getMainWorld().getChunks(0, 0, 6, 6));
		
		p.updateExp();
		
		p.sendPositionAndLook();
		
		return null;
	}
	
	
	/**
	 * Adds a player to the main player storage
	 * @param p The player that should be added
	 */
	public void putPlayer(Player p) {synchronized(players) { synchronized(namePointers) { 
		players.putIfAbsent(p.getUID(), p);
		namePointers.putIfAbsent(p.getUserName(), p.getUID());
	}}}
	
	/**
	 * Get all UID's of player online
	 * 
	 * @return A set over all UID's online
	 */
	public Set<Short> getOnlineUIDs() {
		return players.keySet();
	}
	/**
	 * Get all players on the server
	 * @return A collection of all players online
	 */
	public Collection<Player> getPlayers() {
		return players.values();
	}
	/**
	 * Removes a player from the main player storage!
	 * Warning this should only be done if player has been disconnected
	 * 
	 * @param p The player that shall be removed
	 */
	protected void cleanUp(Player p) {synchronized(players) { synchronized(namePointers) { 
		players.remove(p.getUID());
		namePointers.remove(p.getUserName());
	}}}
	
	/**
	 * Ticks/Updates all existing players
	 * Called in Server.java's main loop (Each tick(20hz) )
	 */
	public void tickAllPlayers() {
		// TODO Do this :p
	}

	/**
	 * To get a player by specified UID(short)
	 * 
	 * @param uid The uid of the object
	 * @return The pointed player or null if non existance
	 */
	public Player getPlayer(short uid) {synchronized(players) {
		return players.get(uid);
	}}

	/**
	 * Get the amount of players on the server,
	 * This value however does not include clients. That info is avalible in NetworkManager
	 * 
	 * @return Amount of players stored here
	 */
	public int getPlayersConnected() {
		return this.players.size();
	}

	/**
	 * Used to get a player by its UUID
	 * Highly not recommended!!!
	 * 
	 * @param uuid The given UUID
	 * @return The given UUID or null if no such player is online
	 */
	@Hacky
	@Cautious
	public Player getPlayer(UUID uuid) {synchronized(players) { synchronized(namePointers) { 
		for(Player p : this.getPlayers())
			if(p.getUUID().toString().equals(uuid.toString()))
				return p;
		return null;
	}}}

	/**
	 * To get a online player by its username.
	 * 
	 * @param username The username of the player to get
	 * @return Either the player or null if not online.
	 */
	public Player getPlayer(String username) {
		if(namePointers.containsKey(username))
			return players.get(namePointers.get(username));
		else
			return null;
	}
	
	/**
	 * Checks if player is online
	 * 
	 * @param uid The UID of the player
	 * @return An boolean if the player is online
	 */
	public boolean isPlayerOnline(short uid) {synchronized(players) { 
		return players.containsKey(uid);
	}}

	/**
	 * Send packet to each online player,
	 * Differs from networkmanager in that way that only ingame players gets this packet,
	 * Not perhaps people logingin/pinging
	 * @param Packet
	 */
	public void broadcastPacket(ChatPacket packet) {
		synchronized(players) { 
		for(Player p : this.players.values())
			p.getClient().sendPacket(packet);
	}}
}
