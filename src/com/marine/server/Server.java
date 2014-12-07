package com.marine.server;

import com.google.common.eventbus.AsyncEventBus;
import com.google.common.eventbus.EventBus;
import com.marine.StandaloneServer;
import com.marine.events.AsyncEvent;
import com.marine.events.Listener;
import com.marine.events.MarineEvent;
import com.marine.player.Player;
import com.marine.world.World;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.Executor;

/**
 * Created 2014-12-02 for MarineStandalone
 *
 * @author Citymonstret
 */
public class Server implements MarineServer {

    private final EventBus eventBus;
    private final StandaloneServer server;
    private final AsyncEventBus asyncEventBus;

    public Server(StandaloneServer server) {
        this.server = server;
        this.eventBus = new EventBus();
        this.asyncEventBus = new AsyncEventBus(new Executor() {
            @Override
            public void execute(Runnable command) {
                command.run();
            }
        });
    }

    @Override
    public StandaloneServer getServer() {
        return server;
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

    @Override
    public void registerListener(Listener listener) {
        eventBus.register(listener);
    }

    @Override
    public void unregisterListener(Listener listener) {
        eventBus.unregister(listener);
    }

    @Override
    public void callEvent(MarineEvent event) {
        if (event instanceof AsyncEvent) {
            asyncEventBus.post(event);
        } else {
            eventBus.post(event);
        }
    }

    @Override
	public String getMOTD() {
		return server.getMOTD();
	}

    @Override
    public void setMaxPlayers(int n) { server.setMaxPlayers(n); }

    @Override
    public int getMaxPlayers() { return server.getMaxPlayers(); }
}
