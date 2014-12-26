///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// MarineStandalone is a minecraft server software and API.
// Copyright (C) MarineMC (marinemc.org)
//
// This program is free software; you can redistribute it and/or modify
// it under the terms of the GNU General Public License as published by
// the Free Software Foundation; either version 2 of the License, or
// (at your option) any later version.
//
// This program is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
// GNU General Public License for more details.
//
// You should have received a copy of the GNU General Public License along
// with this program; if not, write to the Free Software Foundation, Inc.,
// 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

package org.marinemc.game;

import org.marinemc.events.standardevents.JoinEvent;
import org.marinemc.events.standardevents.PreLoginEvent;
import org.marinemc.game.async.ChatManager;
import org.marinemc.game.async.TimeoutManager;
import org.marinemc.game.inventory.PlayerInventory;
import org.marinemc.game.player.Player;
import org.marinemc.game.player.PlayerEntityHandler;
import org.marinemc.game.player.UIDGenerator;
import org.marinemc.net.Client;
import org.marinemc.net.States;
import org.marinemc.net.packets.login.LoginPacket;
import org.marinemc.net.packets.login.LoginSucessPacket;
import org.marinemc.net.play.clientbound.ChatPacket;
import org.marinemc.net.play.clientbound.JoinGamePacket;
import org.marinemc.net.play.clientbound.player.SpawnPlayerPacket;
import org.marinemc.server.Marine;
import org.marinemc.util.Location;
import org.marinemc.util.annotations.Cautious;
import org.marinemc.util.annotations.Hacky;
import org.marinemc.util.mojang.UUIDHandler;
import org.marinemc.util.wrapper.StringWrapper;
import org.marinemc.world.entity.EntityType;

import java.util.*;

/**
 * The place where players are saved and accessed.
 * 
 * @author Fozie
 * @author Citymonstret
 */
public class PlayerManager {
	private volatile  Map<Short, Player> players;

	private volatile Map<String, Short> namePointers;
	
	private TimeoutManager timeout;

	private PlayerEntityHandler localEntityHandler;
	
	public PlayerManager() {
		players = new HashMap<Short, Player>();
		localEntityHandler = new PlayerEntityHandler();
		namePointers = new HashMap<String, Short>();
		timeout = new TimeoutManager();
	}

	public String login(Client client, final LoginPacket packet) {
		//TODO: Encryption and Compression

		UUID uuid;
		String name;
		if (Marine.getServer().isOfflineMode()) {
			uuid = UUIDHandler.getUuidOfflineMode(new StringWrapper(packet.name));
			name = packet.name;
		} else {
			// TODO Fix this
			uuid = UUID.fromString("5387e377-24e1-426f-8791-2f82bd0a4581");
			name = packet.name;
		}

		// TODO This add the encryption stuff etc.. And then separate the following code in to another methoud that is called when encryption response is intercepted.

		Player p = new Player(
				EntityType.PLAYER,
				1,
				new Location(Marine.getMainWorld(),0,5,0), //TODO: Get an location from file or generate spawnpoint
				UIDGenerator.instance().getUID(name),
				uuid,
				name, 
				0f,
				0,
				Marine.getServer().getDefaultGamemode(),
				2,
				2,
				true,
				false,
				true,
				new PlayerInventory((byte) 0),
				client
		);
		
		client.setUID(p.getUID());


		PreLoginEvent preEvent = new PreLoginEvent(p);
		Marine.getServer().callEvent(preEvent);
		if (!preEvent.isAllowed()) {
			return preEvent.getMessage(); // Is banned or something xD
		}
		if (Marine.isBanned(client.getAdress())) {
			return "Your IP is banned from the server";
		}
		if (Marine.isBanned(uuid)) {
			return "You are banned from the server";
		}
		
		client.sendPacket(new LoginSucessPacket(p)); // Send the LoginSuccessPacket
		
		// Begin the join game process
		
		client.setState(States.INGAME);
		
		p.getClient().sendPacket(new JoinGamePacket(p));
		
		p.updateAbilites();
		
		p.sendPositionAndLook();
		
		p.sendChunks(Marine.getServer().getWorldManager().getMainWorld().getChunks(0, 0, 6, 6));
		
		//p.updateExp();
		
		p.sendPositionAndLook();
		
		p.sendTime();
		
		JoinEvent event = new JoinEvent(p, ChatManager.JOIN_MESSAGE);
		Marine.getServer().callEvent(event);

		TablistManager.getInstance().addItem(p);
		TablistManager.getInstance().joinList(p);
		TablistManager.getInstance().setHeaderAndFooter("Testing", "MarineStandalone", p);


		ChatManager.getInstance().sendJoinMessage(p, event.getJoinMessage());

		putPlayer(p);
//		//Send them the herobrine :>
//		p.getClient().sendPacket(new SpawnPlayerPacket(new Player(
//				EntityType.PLAYER,
//				5,
//				new Location(Marine.getMainWorld(),3,5,3), //TODO: Get an location from file or generate spawnpoint
//				UIDGenerator.instance().getUID("Herobrine"),
//				UUID.fromString("f84c6a79-0a4e-45e0-879b-cd49ebd4c4e2"),
//				"Herobrine", 
//				0f,
//				0,
//				Marine.getServer().getDefaultGamemode(),
//				2,
//				2,
//				true,
//				false,
//				true,
//				new PlayerInventory((byte) 0),
//				null
//		)));
		
		return null; // To indicate that the player was successfully created and joined
	}
	
	
	/**
	 * Adds a player to the main player storage
	 * @param p The player that should be added
	 */
	public void putPlayer(Player p) {
		synchronized (players) {
			synchronized (namePointers) {
				players.put(p.getUID(), p);
				namePointers.put(p.getUserName(), p.getUID());
		
		System.out.println(players.size());
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
	 * Ticks/Updates all existing players
	 * Called in Server.java's main loop (Each tick(20hz) )
	 */
	public void tickAllPlayers() {
		for(final Player p : players.values())
			p.sendTime();
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
	 * @param packet
	 */
	public void broadcastPacket(ChatPacket packet) {
		synchronized(players) { 
		for(Player p : this.players.values())
			p.getClient().sendPacket(packet);
	}}
	
	public Player getPlayerByClient(final Client c) {
		if(c.getUID() == -1)
			return null;
		else
			return this.getPlayer(c.getUID());
	}
	
	public void removePlayer(short uid) {
		if(players.containsKey(uid))  {
			if(namePointers.containsKey(players.get(uid).getUserName()))
				namePointers.remove(players.get(uid).getUserName());
			players.remove(uid);
		}
		System.out.println(players.size());
	}

	public void disconnect(Player p) {
		if(!this.players.containsKey(p.getUID()))
			return;
		
		ChatManager.getInstance()
		.broadcastMessage(ChatManager.format(ChatManager.LEAVE_MESSAGE, p));
		
		removePlayer(p.getUID());
	}

	public PlayerEntityHandler getEntitySpawner() {
		return localEntityHandler;
	}
}
