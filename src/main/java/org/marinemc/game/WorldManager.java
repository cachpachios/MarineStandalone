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

package org.marinemc.game;

import org.marinemc.server.StandaloneServer;
import org.marinemc.world.World;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Fozie
 */
public class WorldManager {

    private static byte nextUID = Byte.MIN_VALUE;
    private final StandaloneServer server;
    public Map<Byte, World> loadedWorlds;
    private Byte mainWorld;

    public WorldManager(StandaloneServer server) {
        this.server = server;
        loadedWorlds = Collections.synchronizedMap(new ConcurrentHashMap<Byte, World>());
        mainWorld = null;
    }

    // TODO World loading..

    public static byte getNextUID() {
        return ++nextUID;
    }

    public List<World> getWorlds() {
        return new ArrayList<>(loadedWorlds.values());
    }

    public World getMainWorld() {
        if (mainWorld == null) { // Temporary code when no world loader is implemented
            World w = new World(server, "MainWorld");
            addWorld(w);
            mainWorld = w.getUID();
            return w;
        } else
            return loadedWorlds.get(mainWorld);
    }

    public void addWorld(World w) {
        if (loadedWorlds.containsKey(w.getUID()))
            return;

        loadedWorlds.put(w.getUID(), w);
    }

    public void tick() {
        for (World w : loadedWorlds.values())
            w.tick();
    }
}
