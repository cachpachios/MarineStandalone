package com.marine.server;

import com.marine.StandaloneServer;
import com.marine.player.Player;
import com.marine.world.World;

import java.util.List;
import java.util.UUID;

/**
 * Created 2014-12-02 for MarineStandalone
 *
 * @author Citymonstret
 */
public class Server implements MarineServer {

    private final StandaloneServer server;

    public Server(StandaloneServer server) {
        this.server = server;
    }

    @Override
    public List<Player> getPlayers() {
        return server.getPlayerManager().getPlayers();
    }

    @Override
    public int getPlayerCount() {
        return this.getPlayers().size();
    }

    @Override
    public World getWorld(String name) {
        return null;
    }

    @Override
    public List<World> getWorlds() {
        return server.getWorldManager().getWorlds();
    }

    @Override
    public Player getPlayer(UUID uuid) {
        return null;
    }

    @Override
    public Player getPlayer(String username) {
        return null;
    }
}
