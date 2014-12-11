///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// MarineStandalone is a minecraft server software and API.
// Copyright (C) IntellectualSites (marine.intellectualsites.com)
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

package com.marine.server;

import com.google.common.eventbus.AsyncEventBus;
import com.google.common.eventbus.EventBus;
import com.marine.StandaloneServer;
import com.marine.events.AsyncEvent;
import com.marine.events.Listener;
import com.marine.events.MarineEvent;
import com.marine.player.Player;
import com.marine.world.World;

import java.util.Collection;
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
    public Player getPlayer(short uid) {
        return getServer().getPlayerManager().getPlayer(uid);
    }

    @Override
    public StandaloneServer getServer() {
        return server;
    }

    @Override
    public Collection<Player> getPlayers() {
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
    public int getMaxPlayers() {
        return server.getMaxPlayers();
    }

    @Override
    public void setMaxPlayers(int n) {
        server.setMaxPlayers(n);
    }
}
