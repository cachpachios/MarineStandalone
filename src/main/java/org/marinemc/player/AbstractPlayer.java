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

package org.marinemc.player;

import org.marinemc.net.Client;
import org.marinemc.server.MarineServer;
import org.marinemc.util.Location;
import org.marinemc.util.Position;
import org.marinemc.world.World;

import java.util.UUID;

/**
 * Abstract Player Class
 * <p/>
 * Used for communication with the client/login process/**
 * @author Fozie
 */
public class AbstractPlayer implements IPlayer {

    private final MarineServer s;

    private final PlayerID id;
    private final Client client;
    private PlayerAbilities abilities;
    private Location location;

    private World w;

    public AbstractPlayer(MarineServer server, World w, PlayerID id, Client c, PlayerAbilities abilites, Location spawnLocation) {
        this.s = server;
        this.w = w;
        this.client = c;
        this.location = spawnLocation;
        this.abilities = abilites;
        this.id = id;
    }

    public String getName() {
        return id.getName();
    }

    @Override
    public PlayerID getInfo() {
        return id;
    }

    public PlayerAbilities getAbilities() {
        return abilities;
    }

    @Override
    public Client getClient() {
        return client;
    }

    @Override
    public UUID getUUID() {
        return id.getUUID();
    }

    @Override
    public void update() {
        if (abilities.needUpdate())
            client.sendPacket(abilities.getPacket());
    }

    @Override
    public World getWorld() {
        return w;
    }

    @Override
    public Location getLocation() {
        return location;
    }

    @Override
    public Position getRelativePosition() {
        return location.getRelativePosition();
    }

    public MarineServer getServer() {
        return s;
    }
}