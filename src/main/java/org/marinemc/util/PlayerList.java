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

package org.marinemc.util;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.UUID;

import org.marinemc.events.EventListener;
import org.marinemc.events.EventManager;
import org.marinemc.events.standardevents.LeaveEvent;
import org.marinemc.game.player.Player;
import org.marinemc.server.Marine;
import org.marinemc.util.annotations.Cautious;
import org.marinemc.util.operations.ArgumentedOperation;
import org.marinemc.util.operations.Filter;

/**
 * Created 2014-12-12 for MarineStandalone
 *
 * @author Citymonstret
 * @author Fozie
 */
public class PlayerList extends ArrayList<Short> implements Serializable {

	private static final long serialVersionUID = -3119271835517339073L;

	/**
	 * Constructor
	 *
	 * @param c
	 *            Collection (Players/UUIDs/Shorts) to pre-init with
	 * @param registerAsListener
	 *            Register this a listener (listen to logoutEvent)
	 * @param removeOffline
	 *            Remove offline players (init)
	 */
	public PlayerList(@SuppressWarnings("rawtypes") final Collection c,
			final boolean registerAsListener, final boolean removeOffline) {
		if (c != null && !c.isEmpty())
			for (final Object o : c)
				if (o instanceof Player) {
					if (removeOffline) {
						if (Marine.getPlayers().contains(o))
							this.add((Player) c);
					} else
						this.add((Player) c);
				} else if (o instanceof Short) {
					final short s = (short) o;
					if (removeOffline) {
						if (Marine.getPlayer(s) != null)
							super.add(s);
					} else
						super.add(s);
				} else if (o instanceof UUID) {
					// Will ignore removeOffline, seeing as
					// we don't have a kept storage of UUIDS
					// locally. RAM must love us ;3
					final Player player = Marine.getPlayer((UUID) o);
					if (player != null)
						this.add(player);
				}
		if (registerAsListener)
			EventManager.getInstance().addListener(
					new EventListener<LeaveEvent>() {
						@Override
						public void listen(final LeaveEvent event) {
							onLeave(event);
						}
					});
	}

	/**
	 * Constructor (with no base collection)
	 *
	 * @param registerAsListener
	 *            Register this a listener (listen to logoutEvent)
	 */
	public PlayerList(final boolean registerAsListener) {
		this(null, registerAsListener, false);
	}

	/**
	 * Constructor
	 * <p/>
	 * Will not register listener, and will not pre-init
	 */
	public PlayerList() {
		this(null, false, false);
	}

	public void onLeave(final LeaveEvent event) {
		synchronized (this) {
			super.remove((Object) event.getPlayer().getUID());
		}
	}

	/**
	 * Get a collection containing all players (WARNING) CAN BE SLOW!
	 *
	 * @return Players
	 */
	public Collection<Player> getPlayers() {
		final Collection<Player> players = new ArrayList<>(super.size());
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
	@Cautious
	public synchronized boolean contains(final UUID uuid) {
		return getPlayers().contains(Marine.getPlayer(uuid));
	}

	/**
	 * Check if the list contains the player
	 *
	 * @param player
	 *            Player
	 * @return true if the player is in the list
	 */
	final public synchronized boolean contains(final Player player) {
		return super.contains(player.getUID());
	}

	@SuppressWarnings("deprecation")
	@Override
	public synchronized boolean contains(final Object o) {
		if (o instanceof Player)
			return super.contains(((Player) o).getUID());
		if (o instanceof UUID)
			return this.contains((UUID) o);
		else
			return super.contains(o);
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
	 * @param player
	 *            to add
	 */
	final public synchronized void add(final Player player) {
		super.add(player.getUID());
	}

	/**
	 * Remove a player from the list
	 *
	 * @param player
	 *            Player to remove
	 */
	final public synchronized void remove(final Player player) {
		super.remove((Object) player.getUID());
	}

	/**
	 * Overrides ArrayList toArray() Converts this list to an array of Players
	 * taken from PlayerManager
	 */
	@SuppressWarnings("ALL")
	@Override
	final public Player[] toArray() {
		final Collection<Player> players;
		synchronized (this) {
			players = getPlayers();
		}
		return players.toArray(new Player[players.size()]);
	}

	@SuppressWarnings("ALL")
	@Override
	final public synchronized <Short> Short[] toArray(final Short[] r) {
		return super.toArray((Short[]) new Object[size()]);
	}

	/**
	 * Get a player based on the index
	 *
	 * @param n
	 *            Index
	 * @return Player from index
	 */
	public synchronized Player getPlayer(final int n) {
		return Marine.getPlayer(super.get(n));
	}

	/**
	 * Perform an action for each player
	 *
	 * @param f
	 *            action to perform
	 */
	final public synchronized void foreach(final ArgumentedOperation<Player> f) {
		for (final short s : this)
			f.action(Marine.getPlayer(s));
	}

	/**
	 * Get the player iterator
	 *
	 * @return player iterator
	 */
	final public Iterator<Player> getIterator() {
		return getPlayers().iterator();
	}

	/**
	 * Overrides from ArrayList
	 *
	 * @param c
	 *            to add
	 */
	@SuppressWarnings({ "rawtypes" })
	@Override
	final public boolean addAll(final Collection c) {
		boolean all = true;
		for (final Object o : c)
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
		return all;
	}

	@Override
	final public String toString() {
		return StringUtils.join(getPlayers(), ", ", " and ");
	}

	final synchronized public void filter(final Filter<Player> filter) {
		for (int x = 0; x < size(); x++)
			if (!filter.filter(getPlayer(x)))
				remove(x);
	}
}
