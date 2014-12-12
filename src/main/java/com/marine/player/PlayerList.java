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

package com.marine.player;

import com.google.common.eventbus.Subscribe;
import com.marine.events.Listener;
import com.marine.events.standardevents.LeaveEvent;
import com.marine.server.Marine;

import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;
import java.util.function.Consumer;

/**
 * Created 2014-12-12 for MarineStandalone
 *
 * @author Citymonstret
 */
public class PlayerList extends ArrayList<Short> implements Listener {

    public PlayerList(Collection c, boolean registerAsListener, boolean removeOffline) {
        if (c != null && !c.isEmpty()) {
            for (Object o : c) {
                if (o instanceof Player) {
                    if (removeOffline) {
                        if (Marine.getPlayers().contains(o))
                            this.add(((Player) c));
                    } else {
                        this.add(((Player) c));
                    }
                } else if (o instanceof Short) {
                    short s = (short) o;
                    if (removeOffline) {
                        if (Marine.getPlayer(s) != null)
                            super.add(s);
                    } else {
                        super.add(s);
                    }
                } else if (o instanceof UUID) {
                    // Will ignore removeOffline, seeing as
                    // we don't have a kept storage of UUIDS
                    // locally. RAM must love us ;3
                    Player player = Marine.getPlayer((UUID) o);
                    if (player != null)
                        this.add(player);
                }
            }
        }
        if (registerAsListener)
            Marine.getServer().registerListener(this);
    }

    public PlayerList(boolean registerAsListener, boolean removeOffline) {
        this(null, registerAsListener, removeOffline);
    }

    public PlayerList() {
        this(null, false, false);
    }

    @Subscribe
    public void onLeave(final LeaveEvent event) {
        final Player p = event.getPlayer();
        if (this.contains(p))
            this.remove(p);
    }

    public Collection<Player> getPlayers() {
        Collection<Player> players = new ArrayList<>();
        for (Object s : this)
            players.add(Marine.getPlayer((short) s));
        return players;
    }

    /**
     * @deprecated
     */
    @Deprecated
    public boolean contains(final UUID uuid) {
        return getPlayers().contains(Marine.getPlayer(uuid));
    }

    public boolean contains(final Player player) {
        return super.contains(player.getUID());
    }

    @Override
    public boolean contains(Object o) {
        if (o instanceof Player)
            return this.contains((Player) o);
        if (o instanceof UUID)
            return this.contains((UUID) o);
        else return super.contains(o);
    }

    /**
     * @deprecated
     */
    @Deprecated
    public void add(final UUID uuid) {
        this.add(Marine.getPlayer(uuid));
    }

    /**
     * @deprecated
     */
    @Deprecated
    public void remove(final UUID uuid) {
        this.add(Marine.getPlayer(uuid));
    }

    public void add(final Player player) {
        super.add(player.getUID());
    }

    public void remove(final Player player) {
        super.remove(((Object) player.getUID()));
    }

    @Override
    public Player[] toArray() {
        Player[] players = new Player[size()];
        for (int x = 0; x < players.length; x++)
            players[x] = Marine.getPlayer(get(x));
        return players;
    }

    @Override
    public <Short> Short[] toArray(Short[] r) {
        return super.toArray((Short[]) new Object[size()]);
    }

    public Player getPlayer(final int n) {
        return Marine.getPlayer(super.get(n));
    }

    public void foreach(Consumer<Player> f) {
        for (short s : this) {
            f.accept(Marine.getPlayer(s));
        }
    }

    @Override
    public boolean addAll(Collection c) {
        for (Object o : c) {
            if (o instanceof Player)
                this.add((Player) o);
            else if (o instanceof Short)
                super.add((short) o);
            else if (o instanceof UUID)
                this.add((UUID) o);
        }
        return true;
    }


}
