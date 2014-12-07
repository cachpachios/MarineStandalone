package com.marine.server;

import com.marine.StandaloneServer;
import com.marine.events.Listener;
import com.marine.events.MarineEvent;
import com.marine.player.Player;
import com.marine.world.World;

import java.util.List;
import java.util.UUID;

/**
 * Created 2014-12-02 for MarineStandalone
 *
 * @author Citymonstret
 */
public interface MarineServer {

    public StandaloneServer getServer();

    /**
     * Get all online players
     *
     * @return A list containing all players
     */
    public List<Player> getPlayers();

    /**
     * Get the current player count
     *
     * @return The current Player Count
     */
    public int getPlayerCount();

    /**
     * Get a world based on its name
     *
     * @param name World Name
     * @return World if found, else null
     */
    public World getWorld(String name);

    /**
     * Get all worlds
     *
     * @return A list containg all worlds
     */
    public List<World> getWorlds();

    /**
     * Get a player from its uuid
     *
     * @param uuid UUID
     * @return Player
     */
    public Player getPlayer(UUID uuid);

    /**
     * Get a player based on its username
     *
     * @param username Username
     * @return Player
     */
    public Player getPlayer(String username);

    public void registerListener(Listener listener);

    public void unregisterListener(Listener listener);

    public void callEvent(MarineEvent event);

    public String getMOTD();

    public int getMaxPlayers();

    public void setMaxPlayers(int n);


}
