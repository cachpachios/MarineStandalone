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

package org.marinemc.game.command;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.marinemc.game.CommandManager;
import org.marinemc.game.player.Player;
import org.marinemc.server.Marine;
import org.marinemc.util.Location;
import org.marinemc.util.StringUtils;
import org.marinemc.util.annotations.Protected;
import org.marinemc.world.entity.Entity;

/**
 * Created 2014-12-01 for MarineStandalone
 *
 * @author Citymonstret
 */
public abstract class Command {

	/**
	 * Command aliases
	 */
	private final List<String> aliases;

	/**
	 * Command description
	 */
	private final String description;

	/**
	 * The actual command
	 */
	private String command;

	/**
	 * The manager in which the command was "created"
	 */
	private CommandManager provider;

	/**
	 * The provider of the command, containing the priority level and such
	 */
	private ServiceProvider serviceProvider;

	/**
	 * Required permission
	 */
	private final String permission;

	/**
	 * Constructor
	 *
	 * @param command
	 *            Command
	 * @param aliases
	 *            Aliases
	 * @param description
	 *            Description
	 */
	public Command(final String command, final String permission,
			final String description, final String... aliases) {
		this.command = command.toLowerCase();
		this.aliases = new ArrayList<>(Arrays.asList(aliases));
		this.description = description;
		this.permission = permission;
	}

	@Override
	final public String toString() {
		return command;
	}

	/**
	 * Get the required permission node
	 *
	 * @return required permission
	 */
	public String getPermission() {
		return permission;
	}

	/**
	 * Get the command aliases
	 *
	 * @return Array containing aliases
	 */
	public List<String> getAliases() {
		return aliases;
	}

	/**
	 * Get the command description
	 *
	 * @return command description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Execute the command
	 *
	 * @param sender
	 *            CommandSender
	 * @param arguments
	 *            Command arguments
	 */
	public abstract void execute(CommandSender sender, String[] arguments);

	/**
	 * Replace @p, @r, @a and @e
	 *
	 * @param args
	 *            String[] arguments to be checked
	 * @param sender
	 *            CommandSender
	 * @return replaced strings
	 */
	final public String[] replaceAll(final String[] args,
			final CommandSender sender) {
		final String[] returner = new String[args.length];
		// Retrieve the objects
		final Player closestPlayer = getClosestPlayer(sender), randomPlayer = getRandomPlayer();
		final Entity closestEntity = getClosestEntity(sender);
		// Make strings
		final String cPlr = closestPlayer == null ? "none" : closestPlayer
				.getUserName();
		final String cEnt = closestEntity == null ? "none" : closestEntity
				.toString();
		final String rPlr = randomPlayer == null ? "none" : randomPlayer
				.getUserName();
		final String aPlr = getAllPlayers();
		// Loop through the args
		for (int x = 0; x < args.length; x++)
			returner[x] = args[x].replace("@p", cPlr).replace("@e", cEnt)
					.replace("@r", rPlr).replace("@a", aPlr);
		return returner;
	}

	/**
	 * Get a player based on the argument (@a, @r, @p)
	 *
	 * @param sender
	 *            Sender of the command
	 * @param s
	 *            Argument
	 * @return Null if none found, else a collection
	 */
	public Collection<Player> getPlayers(final CommandSender sender,
			final String s) {
		if (s.equals("@a"))
			return Marine.getPlayers();
		if (s.equals("@p")) {
			if (sender instanceof Player)
				return Arrays.asList((Player) sender);
			return Arrays
					.asList(getLocationClosestPlayer(sender.getLocation()));
		}
		if (s.equals("@r"))
			return Arrays.asList(getRandomPlayer());
		return null;
	}

	/**
	 * Get the closets player
	 *
	 * @param l
	 *            Location to test from
	 * @return player
	 */
	private Player getLocationClosestPlayer(final Location l) {
		Player p = null;
		double c, d = Double.MAX_VALUE;
		for (final Player player : Marine.getPlayers())
			if ((c = l.getEuclideanDistanceSquared(player.getLocation())) < d) {
				d = c;
				p = player;
			}
		return p;
	}

	/**
	 * Get the closets entity
	 *
	 * @param sender
	 *            CommandSender
	 * @return Closets Entity
	 */
	public Entity getClosestEntity(final CommandSender sender) {
		if (sender instanceof Player)
			return (Entity) sender;
		return null;
	}

	/**
	 * Get a random Player
	 *
	 * @return Random player
	 */
	public Player getRandomPlayer() {
		final List<Player> players = new ArrayList<>(Marine.getPlayers());
		return players.get((int) (Math.random() * players.size()));
	}

	/**
	 * Get all players as a string
	 *
	 * @return string containing all player names separated by "," and "and"
	 */
	public String getAllPlayers() {
		return StringUtils.join(Marine.getPlayers(), ", ", " and ");
	}

	/**
	 * Get the closets player
	 *
	 * @param sender
	 *            CommandSender
	 * @return Closets player
	 */
	public Player getClosestPlayer(final CommandSender sender) {
		if (sender instanceof Player)
			return (Player) sender;
		return getLocationClosestPlayer(sender.getLocation());
	}

	/**
	 * Get the command provider
	 *
	 * @return provider
	 */
	final public CommandManager getProvider() {
		return provider;
	}

	/**
	 * Set the command provider
	 *
	 * @param provider
	 *            Command provider
	 */
	final public void setProvider(final CommandManager provider) {
		this.provider = provider;
	}

	/**
	 * Set the command name, should only be used in very special events
	 *
	 * @param name
	 *            new name
	 */
	@Protected
	final public void setName(final String name) {
		command = name;
	}

	/**
	 * Return a new collection of a string based on the command (including
	 * arguments) specified
	 *
	 * @param sender
	 *            Sender wanting the completion
	 * @param command
	 *            Command inputted at the time of request
	 * @return Suggestions
	 */
	public Collection<String> getCompletion(final CommandSender sender,
			final String command) {
		return Arrays.asList();
	}

	/**
	 * Get the command provider
	 *
	 * @return command provider
	 */
	final public ServiceProvider getServiceProvider() {
		return serviceProvider;
	}

	/**
	 * Set the command provider
	 *
	 * @param serviceProvider
	 *            Provider
	 * @throws java.lang.UnsupportedOperationException
	 *             if the provider is already set
	 */
	final public void setServiceProvider(final ServiceProvider serviceProvider) {
		if (this.serviceProvider != null)
			throw new UnsupportedOperationException(
					"Cannot replace command provider");
		this.serviceProvider = serviceProvider;
	}
}
