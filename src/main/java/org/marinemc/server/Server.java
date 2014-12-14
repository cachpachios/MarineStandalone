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

import com.google.common.eventbus.AsyncEventBus;
import com.google.common.eventbus.EventBus;
import org.marinemc.StandaloneServer;
import org.marinemc.events.AsyncEvent;
import org.marinemc.events.Listener;
import org.marinemc.events.MarineEvent;
import org.marinemc.game.system.MarineSecurityManager;
import org.marinemc.player.Player;
import org.marinemc.world.World;

import java.util.Collection;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.Executor;

/**
 * Server implementation
 *
 * @author Citymonstret
 * @author Fozie
 */
public class Server implements MarineServer {

    private final EventBus eventBus;
    private final StandaloneServer server;
    private final AsyncEventBus asyncEventBus;

    public Server(final StandaloneServer server) {
        // Security Check Start
        System.getSecurityManager().checkPermission(MarineSecurityManager.MARINE_PERMISSION);
        // Security Check end
        this.server = server;
        this.eventBus = new EventBus();
        this.asyncEventBus = new AsyncEventBus(new Executor() {
            @Override
            public void execute(final Runnable command) {
                command.run();
            }
        });
    }

    @Override
    final public Player getPlayer(final short uid) {
        return this.getServer().getPlayerManager().getPlayer(uid);
    }

    @Override
    final public StandaloneServer getServer() {
        return this.server;
    }

    @Override
    final public Collection<Player> getPlayers() {
        return this.server.getPlayerManager().getPlayers();
    }

    @Override
    final public int getPlayerCount() {
        return this.getPlayers().size();
    }

    @Override
    final public World getWorld(final String name) {
        return null;
    }

    @Override
    final public List<World> getWorlds() {
        return this.server.getWorldManager().getWorlds();
    }

    @Override
    final public Player getPlayer(final UUID uuid) {
        return this.server.getPlayerManager().getPlayer(uuid);
    }

    @Override
    final public Player getPlayer(final String username) {
        return this.server.getPlayerManager().getPlayer(username);
    }

    @Override
    final public void registerListener(final Listener listener) {
        this.eventBus.register(listener);
    }

    @Override
    final public void unregisterListener(final Listener listener) {
        this.eventBus.unregister(listener);
    }

    @Override
    final public void callEvent(final MarineEvent event) {
        if (event instanceof AsyncEvent) {
            this.asyncEventBus.post(event);
        } else {
            this.eventBus.post(event);
        }
    }

    @Override
    final public String getMOTD() {
        return this.server.getMOTD();
    }

    @Override
    final public int getMaxPlayers() {
        return this.server.getMaxPlayers();
    }

    @Override
    final public void setMaxPlayers(final int n) {
        this.server.setMaxPlayers(n);
    }
}
