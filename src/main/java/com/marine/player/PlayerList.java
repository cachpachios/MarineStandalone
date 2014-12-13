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
import java.util.Iterator;
import java.util.UUID;
import java.util.function.Consumer;

/**
 * Created 2014-12-12 for MarineStandalone
 *
 * @author Citymonstret
 */
public class PlayerList extends ArrayList<Short> implements Listener {

    /**
     * Constructor
     *
     * @param c                  Collection (Players/UUIDs/Shorts) to pre-init with
     * @param registerAsListener Register this a listener (listen to logoutEvent)
     * @param removeOffline      Remove offline players (init)
     */
    public PlayerList(final Collection c, final boolean registerAsListener, final boolean removeOffline) {
        if (c != null && !c.isEmpty()) {
            for (final Object o : c) {
                if (o instanceof Player) {
                    if (removeOffline) {
                        if (Marine.getPlayers().contains(o))
                            this.add(((Player) c));
                    } else {
                        this.add(((Player) c));
                    }
                } else if (o instanceof Short) {
                    final short s = (short) o;
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
                    final Player player = Marine.getPlayer((UUID) o);
                    if (player != null)
                        this.add(player);
                }
            }
        }
        if (registerAsListener)
            Marine.getServer().registerListener(this);
    }

    /**
     * Constructor (with no base collection)
     *
     * @param registerAsListener Register this a listener (listen to logoutEvent)
     */
    public PlayerList(final boolean registerAsListener) {
        this(null, registerAsListener, false);
    }

    /**
     * Constructor
     *
     * Will not register listener, and will not pre-init
     */
    public PlayerList() {
        this(null, false, false);
    }

    @Subscribe
    public void onLeave(final LeaveEvent event) {
        final Player p = event.getPlayer();
        synchronized (this) {
            if (this.contains(p))
                this.remove(p);
        }
    }

    /**
     * Get a collection containing all players
     * (WARNING) CAN BE SLOW!
     *
     * @return Players
     */
    public Collection<Player> getPlayers() {
        final Collection<Player> players = new ArrayList<>();
        synchronized (this) {
            for (final Object s : this)
                players.add(Marine.getPlayer((short) s));
        }
        return players;
    }

    /**
     * @deprecated
     */
    @Deprecated
    public synchronized boolean contains(final UUID uuid) {
        return getPlayers().contains(Marine.getPlayer(uuid));
    }

    /**
     * Check if the list contains the player
     *
     * @param player Player
     * @return true if the player is in the list
     */
    final public synchronized boolean contains(final Player player) {
        return super.contains(player.getUID());
    }

    @Override
    public synchronized boolean contains(final Object o) {
        if (o instanceof Player)
            return this.contains((Player) o);
        if (o instanceof UUID)
            return this.contains((UUID) o);
        else return super.contains(o);
    }

    @Override
    final public synchronized int size() {
        return super.size();
    }

    @Override
    final public synchronized boolean add(final Short s) {
        return super.add(s);
    }

    @Override
    final public synchronized Short get(int n) {
        return super.get(n);
    }

    @Override
    final public synchronized boolean remove(final Object s) {
        return super.remove(s);
    }

    /**
     * @deprecated
     */
    @Deprecated
    public synchronized void add(final UUID uuid) {
        this.add(Marine.getPlayer(uuid));
    }

    /**
     * @deprecated
     */
    @Deprecated
    public synchronized void remove(final UUID uuid) {
        this.add(Marine.getPlayer(uuid));
    }

    /**
     * Add a player to the list
     *
     * @param player to add
     */
    final public synchronized void add(final Player player) {
        super.add(player.getUID());
    }

    /**
     * Remove a player from the list
     *
     * @param player
     */
    final public synchronized void remove(final Player player) {
        super.remove(((Object) player.getUID()));
    }

    @Override
    public Player[] toArray() {
        final Player[] players = new Player[size()];
        synchronized (this) {
            for (int x = 0; x < players.length; x++)
                players[x] = Marine.getPlayer(get(x));
        }
        return players;
    }

    @Override
    public synchronized <Short> Short[] toArray(final Short[] r) {
        return super.toArray((Short[]) new Object[size()]);
    }

    /**
     * Get a player based on the index
     *
     * @param n Index
     *
     * @return Player from index
     */
    public synchronized Player getPlayer(final int n) {
        return Marine.getPlayer(super.get(n));
    }

    /**
     * Perform an action for each player
     *
     * @param f action to perform
     */
    final public synchronized void foreach(final Consumer<Player> f) {
        for (final short s : this) {
            f.accept(Marine.getPlayer(s));
        }
    }

    /**
     * Get the player iterator
     *
     * @return player iterator
     */
    public Iterator<Player> getIterator() {
        return getPlayers().iterator();
    }

    @Override
    final public boolean addAll(final Collection c) {
        boolean all = true;
        for (final Object o : c) {
            if (o instanceof Player)
                this.add((Player) o);
            else if (o instanceof Short)
                synchronized (this) {
                    super.add((short) o);
                }
            else if (o instanceof UUID)
                this.add((UUID) o);
            else
                all = false;
        }
        return all;
    }

}
