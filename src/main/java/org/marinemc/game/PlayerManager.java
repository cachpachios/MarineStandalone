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
import org.marinemc.game.async.MovmentValidator;
import org.marinemc.game.async.TimeoutManager;
import org.marinemc.game.async.WorldStreamingThread;
import org.marinemc.game.inventory.PlayerInventory;
import org.marinemc.game.player.Player;
import org.marinemc.game.player.UIDGenerator;
import org.marinemc.io.binary.ByteInput;
import org.marinemc.net.Client;
import org.marinemc.net.Packet;
import org.marinemc.net.States;
import org.marinemc.net.packets.login.LoginPacket;
import org.marinemc.net.packets.login.LoginSucessPacket;
import org.marinemc.net.packets.player.PlayerLookPositionPacket;
import org.marinemc.net.packets.player.PlayerPositionPacket;
import org.marinemc.net.play.clientbound.JoinGamePacket;
import org.marinemc.server.Marine;
import org.marinemc.settings.ServerSettings;
import org.marinemc.util.Assert;
import org.marinemc.util.Location;
import org.marinemc.util.annotations.Cautious;
import org.marinemc.util.annotations.Hacky;
import org.marinemc.util.mojang.UUIDHandler;
import org.marinemc.util.vectors.Vector3d;
import org.marinemc.util.wrapper.Movment;
import org.marinemc.world.entity.EntityType;

import java.util.*;
import java.util.regex.Pattern;

import static org.marinemc.game.async.ChatManager.*;

/**
 * The place where players are saved and accessed.
 * 
 * @author Fozie
 * @author Citymonstret
 */
public class PlayerManager {
	private final Pattern validName;
	private final TimeoutManager timeout;
	private final MovmentValidator movmentValidator;
	private final WorldStreamingThread worldStreamer;
	/**
	 * Player list:
	 */
	private volatile Map<Short, Player> players;
	/**
	 * Name pointers: (Returns the UID from the username of an online player)
	 */
	private volatile Map<String, Short> namePointers;

	public PlayerManager() {
		players = new HashMap<Short, Player>();
		namePointers = new HashMap<String, Short>();
		timeout = new TimeoutManager();
		validName = Pattern.compile("^[a-zA-Z0-9_]{2,16}$");
		worldStreamer = new WorldStreamingThread(this);
		worldStreamer.start();

		movmentValidator = new MovmentValidator();
		movmentValidator.start();
	}

	public String login(final Client client, final LoginPacket packet) {
		// TODO: Encryption and Compression
		// TODO Proper authentication

		final String name = packet.name;
		final UUID uuid = UUIDHandler.instance().getUUID(name);

		if (uuid == null)
			return "Invalid UUID";
		if (!validName.matcher(name).matches())
			return "Invalid Username";

		// TODO This add the encryption stuff etc.. And then separate the
		// following code in to another method that is called when encryption
		// response is intercepted.

		final Player p = new Player(EntityType.PLAYER, 1, new Location(
				Marine.getMainWorld(), 0, 5, 0), // TODO: Get an location from
													// file or generate
													// spawnpoint
				UIDGenerator.instance().getUID(name), uuid, name, 0f, 0, Marine
						.getServer().getDefaultGamemode(), 0.2f, 0.2f, true,
				false, true, new PlayerInventory((byte) 0), client);

		client.setUID(p.getUID());

		final PreLoginEvent preEvent = new PreLoginEvent(p);
		Marine.getServer().callEvent(preEvent);

		if (!preEvent.isCancelled())
			return preEvent.getMessage();

		if (Marine.getServer().usingWhitelist()
				&& !Marine.getServer().isWhitelisted(p))
			return "You are not whitelisted";
		if (Marine.isBanned(client.getAdress()))
			return "Your IP is banned from the server";
		if (Marine.isBanned(uuid))
			return "You are banned from the server";

		client.setCompressionThreshold(ServerSettings.getInstance().network_threshould);

		client.sendPacket(new LoginSucessPacket(p)); // Send the
														// LoginSuccessPacket

		// Begin the join game process

		client.setState(States.INGAME);

		p.getClient().sendPacket(new JoinGamePacket(p));

		final JoinEvent event = new JoinEvent(p, JOIN_MESSAGE);
		Marine.getServer().callEvent(event);
		if (event.isCancelled())
			return "Unable to join server";

		p.updateAbilites();

		p.sendPositionAndLook();

		// p.updateExp(); TODO Error
		p.sendChunks(p.getWorld().getSpawnChunks(p));

		p.sendPositionAndLook();

		p.sendTime();

		getInstance().sendJoinMessage(p, event.getJoinMessage());

		putPlayer(p);

		// TODO LET FOZIE HELP ME FIX THESE ERRORS
		// TablistManager.getInstance().joinList(p);
		// TablistManager.getInstance().addItem(p);
		TablistManager.getInstance().setHeaderAndFooter("Testing",
				"MarineStandalone", p);
		// Send them the herobrine :>
		// p.getClient().sendPacket(new SpawnPlayerPacket(new Player(
		// EntityType.PLAYER,
		// 5,
		// new Location(Marine.getMainWorld(),3,5,3), //TODO: Get an location
		// from file or generate spawnpoint
		// UIDGenerator.instance().getUID("Herobrine"),
		// UUID.fromString("f84c6a79-0a4e-45e0-879b-cd49ebd4c4e2"),
		// "Herobrine",
		// 0f,
		// 0,
		// Marine.getServer().getDefaultGamemode(),
		// 2,
		// 2,
		// true,
		// false,
		// true,
		// new PlayerInventory((byte) 0),
		// null
		// )));
		return null;
	}
	
	public void moveLookPlayerFromPacket(final Client c, final ByteInput data) {
		final Player p;

		System.out.println("Move!");
		if(c.getUID() == -1)
			return;
		else
			p = this.getPlayerByClient(c);
		
		if(p == null) return;
		
			PlayerLookPositionPacket packet = new PlayerLookPositionPacket();
			
			packet.readFromBytes(data);
			
			boolean anyChange = false;
			
			// Coping to not cause async errors :/
			
			Location copy = null;
			boolean copied = true;
			try {
				copy = (Location) p.getLocation().clone();
			} catch (CloneNotSupportedException e) {
				copied = false;
			}
			
			if(packet.getX() != p.getX()) {
				p.getLocation().setX(packet.getX());
				anyChange = true;
			}
			
			if(packet.getY() != p.getY()) {
				p.getLocation().setY(packet.getY());
				anyChange = true;
			}
			
			if(packet.getZ() != p.getZ()) {
				p.getLocation().setZ(packet.getZ());
				anyChange = true;
			}
			
			// If no change has accure why bother?
			if(!anyChange)
				return;
			
			// Add the movment to the async movmentvalidator to validate it
			final Vector3d finalPos = new Vector3d(packet.getX(),packet.getY(), packet.getZ());
			
			movmentValidator.putForValidation(p, new Movment(p.getLocation(), finalPos));
			if(copied)
				if(copy.getEuclideanDistanceSquared(finalPos) > 16*16)
					worldStreamer.asyncStreaming(p.getUID());
				else
					worldStreamer.asyncStreaming(p.getUID());
			
			copy = null;
	}

	public void movePlayerFromPacket(final Client c, final ByteInput data) {
		final Player p;

		System.out.println("Move!");
		if(c.getUID() == -1)
			return;
		else
			p = this.getPlayerByClient(c);
		
		if(p == null) return;
		
			PlayerPositionPacket packet = new PlayerPositionPacket();
			
			packet.readFromBytes(data);
			
			if(packet.getX() != p.getX()) {
				p.getLocation().setX(packet.getX());
			}
			
			if(packet.getY() != p.getY()) {
				p.getLocation().setY(packet.getY());
			}
			
			if(packet.getZ() != p.getZ()) {
				p.getLocation().setZ(packet.getZ());
			}
			
			// Add the movment to the async movmentvalidator to validate it
			movmentValidator.putForValidation(p, new Movment(p.getLocation(), packet.getLocation()));
			
			worldStreamer.asyncStreaming(p.getUID());
	}
	
	/**
	 * Adds a player to the main player storage
	 * 
	 * @param p
	 *            The player that should be added
	 */
	public void putPlayer(final Player p) {
		players.put(p.getUID(), p);
		namePointers.put(p.getUserName(), p.getUID());
	}

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
	 * 
	 * @return A collection of all players online
	 */
	public Collection<Player> getPlayers() {
		return players.values();
	}

	/**
	 * Ticks/Updates all existing players Called in Server.java's main loop
	 * (Each tick(20hz) )
	 */
	public void tickAllPlayers() {
		for (final Player p : players.values())
			p.sendTime();
	}

	/**
	 * To get a player by specified UID(short)
	 * 
	 * @param uid
	 *            The uid of the object
	 * @return The pointed player or null if non existance
	 */
	public Player getPlayer(final short uid) {
		return players.get(uid);
	}

	/**
	 * Get the amount of players on the server, This value however does not
	 * include clients. That info is avalible in NetworkManager
	 * 
	 * @return Amount of players stored here
	 */
	public int getPlayersConnected() {
		return players.size();
	}

	/**
	 * Used to get a player by its UUID Highly not recommended!!!
	 * 
	 * @param uuid
	 *            The given UUID
	 * @return The given UUID or null if no such player is online
	 */
	@Hacky
	@Cautious
	public Player getPlayer(final UUID uuid) {
		for (final Player p : getPlayers())
			if (p.getUUID().toString().equals(uuid.toString()))
				return p;
		return null;
	}

	/**
	 * To get a online player by its username.
	 * 
	 * @param username
	 *            The username of the player to get
	 * @return Either the player or null if not online.
	 */
	public Player getPlayer(final String username) {
		if (namePointers.containsKey(username))
			return players.get(namePointers.get(username));
		return null;
	}

	/**
	 * Checks if player is online
	 * 
	 * @param uid
	 *            The UID of the player
	 * @return An boolean if the player is online
	 */
	public boolean isPlayerOnline(final short uid) {
		return players.containsKey(uid);
	}

	/**
	 * Send packet to each online player, Differs from networkmanager in that
	 * way that only ingame players gets this packet, Not perhaps people
	 * logingin/pinging
	 * 
	 * @param packet
	 */
	public void broadcastPacket(final Packet packet) {
		for (final Player p : players.values())
			p.getClient().sendPacket(packet);
	}

	public Player getPlayerByClient(final Client c) {
		return c.getUID() != -1 ? getPlayer(c.getUID()) : null;
	}

	public void removePlayer(final short uid) {
		if (players.containsKey(uid)) {
			if (namePointers.containsKey(players.get(uid).getUserName()))
				namePointers.remove(players.get(uid).getUserName());
			players.remove(uid);
		}
	}

	public void disconnect_nonnewtork(Player p) {
		if (p == null)
			return;
		Assert.contains(players, p.getUID());
		getInstance().broadcastMessage(format(LEAVE_MESSAGE, p));
		removePlayer(p.getUID());
		p = null;
	}

	public void disconnect(final Player p) {
		// if(!this.players.containsKey(p.getUID())) {
		// return;
		// }
		Assert.contains(players, p.getUID());
		getInstance().broadcastMessage(format(LEAVE_MESSAGE, p));
		removePlayer(p.getUID());
		Marine.getServer().getNetworkManager().cleanUp(p.getClient());
	}

	public boolean isEmpty() {
		return players.isEmpty();
	}

	public WorldStreamingThread getWorldStreamer() {
		return worldStreamer;
	}

}
