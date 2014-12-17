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

import org.marinemc.game.CommandManager;
import org.marinemc.player.Player;
import org.marinemc.server.Marine;
import org.marinemc.util.Location;
import org.marinemc.util.StringUtils;
import org.marinemc.util.annotations.Protected;
import org.marinemc.world.entity.Entity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

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
    private CommandManager provider;
    private CommandProvider commandProvider;

    /**
     * Constructor
     *
     * @param command     Command
     * @param aliases     Aliases
     * @param description Description
     */
    public Command(String command, String[] aliases, String description) {
        this.command = command.toLowerCase();
        this.aliases = new ArrayList<>(Arrays.asList(aliases == null ? new String[]{} : aliases));
        this.description = description;
    }

    @Override
    final public String toString() {
        return command;
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
     * @param sender    CommandSender
     * @param arguments Command arguments
     */
    public abstract void execute(CommandSender sender, String[] arguments);

    /**
     * Replace @p, @r, @a and @e
     *
     * @param args   String[] arguments to be checked
     * @param sender CommandSender
     * @return replaced strings
     */
    final public String[] replaceAll(String[] args, CommandSender sender) {
        String[] returner = new String[args.length];
        // Retrieve the objects
        Player closestPlayer = getClosestPlayer(sender), randomPlayer = getRandomPlayer();
        Entity closestEntity = getClosestEntity(sender);
        // Make strings
        String cPlr = closestPlayer == null ? "none" : closestPlayer.getName();
        String cEnt = closestEntity == null ? "none" : closestEntity.toString();
        String rPlr = randomPlayer == null ? "none" : randomPlayer.getName();
        String aPlr = getAllPlayers();
        // Loop through the args
        for (int x = 0; x < args.length; x++) {
            returner[x] = args[x]
                    .replace("@p", cPlr)
                    .replace("@e", cEnt)
                    .replace("@r", rPlr)
                    .replace("@a", aPlr);
        }
        return returner;
    }

    /**
     * Get a player based on the argument (@a, @r, @p)
     *
     * @param sender Sender of the command
     * @param s      Argument
     * @return Null if none found, else a collection
     */
    public Collection<Player> getPlayers(CommandSender sender, String s) {
        if (s.equals("@a")) {
            return Marine.getPlayers();
        }
        if (s.equals("@p")) {
            if (sender instanceof Player)
                return Arrays.asList((Player) sender);
            return Arrays.asList(getLocationClosestPlayer(sender.getLocation()));
        }
        if (s.equals("@r")) {
            return Arrays.asList(getRandomPlayer());
        }
        return null;
    }

    private Player getLocationClosestPlayer(final Location l) {
        Player p = null;
        double c, d = Double.MAX_VALUE;
        for (Player player : Marine.getPlayers()) {
            if ((c = l.getEuclideanDistanceSquared(player.getLocation())) < d) {
                d = c;
                p = player;
            }
        }
        return p;
    }

    /**
     * Get the closets entity
     *
     * @param sender CommandSender
     * @return Closets Entity
     */
    public Entity getClosestEntity(CommandSender sender) {
        if (sender instanceof Player) return (Entity) sender;
        return null;
    }

    /**
     * Get a random Player
     *
     * @return Random player
     */
    public Player getRandomPlayer() {
        List<Player> players = new ArrayList<>(Marine.getPlayers());
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
     * @param sender CommandSender
     * @return Closets player
     */
    public Player getClosestPlayer(CommandSender sender) {
        if (sender instanceof Player) return (Player) sender;
        return getLocationClosestPlayer(sender.getLocation());
    }

    /**
     * Get the command provider
     *
     * @return provider
     */
    public CommandManager getProvider() {
        return provider;
    }

    /**
     * Set the command provider
     *
     * @param provider Command provider
     */
    public void setProvider(CommandManager provider) {
        this.provider = provider;
    }

    /**
     * Set the command name, should only
     * be used in very special events
     *
     * @param name new name
     */
    @Protected
    public void setName(String name) {
        this.command = name;
    }

    /**
     * Return a new collection of a string based
     * on the command (including arguments) specified
     *
     * @param sender  Sender wanting the completion
     * @param command Command inputted at the time of request
     * @return Suggestions
     */
    public Collection<String> getCompletion(CommandSender sender, String command) {
        return Arrays.asList();
    }

    public CommandProvider getCommandProvider() {
        return this.commandProvider;
    }

    public void setCommandProvider(final CommandProvider commandProvider) {
        if (this.commandProvider != null) {
            throw new UnsupportedOperationException("Cannot replace command provider");
        }
        this.commandProvider = commandProvider;
    }
}
