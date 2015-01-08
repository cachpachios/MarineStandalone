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

package org.marinemc.server;

import java.net.InetAddress;
import java.util.Collection;
import java.util.UUID;

import org.marinemc.game.chat.ChatMessage;
import org.marinemc.game.permission.Permission;
import org.marinemc.game.player.Player;
import org.marinemc.game.scheduler.Scheduler;
import org.marinemc.game.system.MarineSecurityManager;
import org.marinemc.logging.Logging;
import org.marinemc.util.annotations.Protected;
import org.marinemc.util.operations.ArgumentedOperation;
import org.marinemc.util.operations.PermissionFilter;
import org.marinemc.util.operations.PlayerOperation;
import org.marinemc.world.World;

/**
 * Static API Class - Fun stuff in here
 * <p/>
 * Static implementation of commonly used methods. It wraps around the global
 * instances of: StandaloneServer MarineServer
 *
 * @author Citymonstret
 * @author Fozie
 */
@SuppressWarnings("unused")
public class Marine {

	protected static MarineServer server;

	/**
	 * Get the current motd
	 *
	 * @return Message Of the Day
	 */
	public static String getMotd() {
		return server.getMotd();
	}

	/**
	 * Get all players
	 *
	 * @return List containing all players
	 */
	public static Collection<Player> getPlayers() {
		return server.getPlayers();
	}

	/**
	 * Get a player based on the username
	 *
	 * @param username
	 *            Username
	 * @return Player
	 */
	public static Player getPlayer(final String username) {
		return server.getPlayer(username);
	}

	/**
	 * Get a player based on its uid
	 *
	 * @param uuid
	 *            player uuid
	 * @return player or null
	 */
	public static Player getPlayer(final UUID uuid) {
		for (final Player player : getPlayers())
			if (player.getUUID().equals(uuid))
				return player;
		return null;
	}

	/**
	 * Get a world based on its name
	 *
	 * @param name
	 *            World Name
	 * @return World or null
	 */
	public static World getWorld(final String name) {
		return server.getWorld(name);
	}

	/**
	 * Get the MarineServer
	 *
	 * @return Marine Server
	 */
	public static MarineServer getServer() {
		return server;
	}

	@Protected
	protected static void setServer(final MarineServer marine) {
		// Security Check Start
		System.getSecurityManager().checkPermission(
				MarineSecurityManager.MARINE_PERMISSION);
		// Security Check end
		if (server != null)
			throw new RuntimeException("Cannot replace the MarineServer");
		server = marine;
	}

	/**
	 * Stop the server
	 */
	public static void stop() {
		server.stop();
	}

	/**
	 * Get the max player number
	 *
	 * @return max players
	 */
	public static int getMaxPlayers() {
		return server.getMaxPlayers();
	}

	/**
	 * Get the scheduler class
	 *
	 * @return scheduler class
	 */
	public static Scheduler getScheduler() {
		return getServer().getScheduler();
	}

	/**
	 * Broadcast a message to all players
	 *
	 * @param string
	 *            message
	 */
	public static void broadcastMessage(final String string) {
		for (final Player player : getPlayers())
			player.sendMessage(string);
		Logging.getLogger().log(string);
	}

	/**
	 * Send raw JSON
	 *
	 * @param string
	 *            json
	 */
	public static void broadcastMessageRaw(final String string) {
		for (final Player player : getPlayers())
			player.sendMessageRaw(string);
		Logging.getLogger().log("Raw JSON Sent: " + string);
	}

	/**
	 * Broadcast a message to all players
	 *
	 * @param chat
	 *            message
	 */
	public static void broadcastMessage(final ChatMessage chat) {
		for (final Player player : getPlayers())
			player.sendMessage(chat);
		Logging.getLogger().log("Raw Chat Sent: " + chat.toString());
	}

	/**
	 * Broadcast a message to all players with the specified permission
	 *
	 * @param permission
	 *            Required Permission
	 * @param message
	 *            Message To Send
	 */
	public static void broadcast(final Permission permission,
			final String message) {
		foreach(new PermissionFilter(permission, new PlayerOperation() {
			@Override
			public void action(final Player player) {
				player.sendMessage(message);
			}
		}));
	}

	/**
	 * Get a player based on its uid
	 *
	 * @param uid
	 *            Unique ID
	 * @return Player or null
	 */
	public static Player getPlayer(final short uid) {
		return getServer().getPlayer(uid);
	}

	/**
	 * Check if a player is banned
	 *
	 * @param uuid
	 *            Player UUID
	 * @return boolean (banned)
	 */
	public static boolean isBanned(final UUID uuid) {
		return server.isBanned(uuid);
	}

	/**
	 * Check if an IP is banned
	 *
	 * @param address
	 *            IP
	 * @return boolean (banned)
	 */
	public static boolean isBanned(final InetAddress address) {
		return server.isBanned(address);
	}

	/**
	 * Gives the main world of the server
	 * 
	 * @return The main world, or the spawn world
	 */
	public static World getMainWorld() {
		return getServer().getWorldManager().getMainWorld();
	}

	/**
	 * Perform an action for each online player
	 *
	 * @param o
	 *            Operation to perform
	 */
	public static void foreach(final ArgumentedOperation<Player> o) {
		for (final Player player : getPlayers())
			o.action(player);
	}

}
