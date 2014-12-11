package com.marine.game.command;

import com.marine.player.Player;
import com.marine.server.Marine;
import com.marine.util.Location;
import com.marine.world.entity.Entity;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created 2014-12-01 for MarineStandalone
 *
 * @author Citymonstret
 */
public abstract class Command {

    /**
     * The actual command
     */
    private final String command;

    /**
     * Command aliases
     */
    private final String[] aliases;

    /**
     * Command description
     */
    private final String description;

    /**
     * Constructor
     * @param command Command
     * @param aliases Aliases
     * @param description Description
     */
    public Command(String command, String[] aliases, String description) {
        this.command = command.toLowerCase();
        this.aliases = aliases == null ? new String[] {} : aliases;
        this.description = description;
    }

    @Override
    final public String toString() {
        return command;
    }

    /**
     * Get the command aliases
     * @return Array containing aliases
     */
    public String[] getAliases() {
        return aliases;
    }

    /**
     * Get the command description
     * @return command description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Execute the command
     * @param sender CommandSender
     * @param arguments Command arguments
     */
    public abstract void execute(CommandSender sender, String[] arguments);

    /**
     * Replace @p, @r, @a and @e
     * @param args String[] arguments to be checked
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
     * Get the closets entity
     * @param sender CommandSender
     * @return Closets Entity
     */
    public Entity getClosestEntity(CommandSender sender) {
        if (sender instanceof Player) return (Entity) sender;
        return null;
    }

    /**
     * Get a random Player
     * @return Random player
     */
    public Player getRandomPlayer() {
        List<Player> players = new ArrayList<>(Marine.getPlayers());
        return players.get((int) (Math.random() * players.size()));
    }

    /**
     * Get all players as a string
     * @return string containing all player names separated by "," and "and"
     */
    public String getAllPlayers() {
        StringBuilder s = new StringBuilder();
        Iterator<Player> i = Marine.getPlayers().iterator();
        int lastL = 0;
        while (i.hasNext()) {
            Player player = i.next();
            if (!i.hasNext()) {
                s.append(player.getName()).append(" and ");
                lastL = 5;
            } else {
                s.append(player.getName()).append(", ");
                lastL = 2;
            }
        }
        return s.toString().substring(0, s.toString().length() - lastL);
    }

    /**
     * Get the closets player
     * @param sender CommandSender
     * @return Closets player
     */
    public Player getClosestPlayer(CommandSender sender) {
        if (sender instanceof Player) return (Player) sender;
        Player closets = null;
        double distance = Double.MAX_VALUE, current;
        Location loc = sender.getLocation();
        for(Player player : Marine.getPlayers()) {
            if((current = loc.getEuclideanDistance(player.getLocation())) < distance) {
                distance = current;
                closets = player;
            }
        }
        return closets;
    }
}
