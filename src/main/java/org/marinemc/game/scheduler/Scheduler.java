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

package org.marinemc.game.scheduler;

import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentHashMap;

import org.marinemc.game.command.ServiceProvider;

/**
 * A Scheduler implementation made for MarineStandalone
 *
 * @author Citymonstret
 */
public class Scheduler {

	private final Map<Long, MarineRunnable> asyncRunnables;
	private final Map<Long, MarineRunnable> syncRunnables;
	private long id;
	private final int tick = 0;
	private int tickRate;
	private boolean started = false;

	/**
	 * Constructor
	 */
	public Scheduler() {
		id = -1;
		asyncRunnables = new ConcurrentHashMap<>();
		syncRunnables = new ConcurrentHashMap<>();
	}

	/**
	 * Get the next ID
	 *
	 * @return next ID
	 */
	public long getNextId() {
		return ++id;
	}

	/**
	 * Start the scheduler thread
	 *
	 * @param tickRate
	 *            Number of ticks per second {n = (1000 / tickRate)}
	 * @throws IllegalArgumentException
	 *             If the timer is already started
	 * @throws UnsupportedOperationException
	 *             If the tickRate is below 1 or above 119
	 */
	public void start(final int tickRate) throws IllegalArgumentException,
			UnsupportedOperationException {
		if (started)
			throw new UnsupportedOperationException("Timer already started");
		if (tickRate < 1)
			throw new IllegalArgumentException("TickRate cannot be below 1");
		if (tickRate > 119)
			throw new IllegalArgumentException("TickRate cannot be above 119");
		this.tickRate = tickRate;
		new Timer("scheduler").scheduleAtFixedRate(new TimerTask() {
			@Override
			public void run() {
				tickAsync();
			}
		}, 0l, 1000 / 20);
		started = true;
	}

	/**
	 * Remove a runnable based on its instance
	 *
	 * @param runnable
	 *            Runnable to remove
	 * @return true if the runnable was found and removed
	 */
	public boolean removeTask(final MarineRunnable runnable) {
		for (final Map.Entry<Long, MarineRunnable> sync : syncRunnables
				.entrySet())
			if (sync.getValue().equals(runnable)) {
				removeTask(sync.getKey());
				return true;
			}
		for (final Map.Entry<Long, MarineRunnable> async : asyncRunnables
				.entrySet())
			if (async.getValue().equals(runnable)) {
				removeTask(async.getKey());
				return true;
			}
		return false;
	}

	/**
	 * Remove a task based on it's ID
	 *
	 * @param l
	 *            Final to remove
	 * @return true if the runnable was found and removed
	 */
	public boolean removeTask(final long l) {
		if (syncRunnables.containsKey(l)) {
			syncRunnables.remove(l);
			return true;
		}
		if (asyncRunnables.containsKey(l)) {
			asyncRunnables.remove(l);
			return true;
		}
		return false;
	}

	private void tickAsync() {
		for (final long n : asyncRunnables.keySet())
			asyncRunnables.get(n).tick(this, n);
	}

	/**
	 * Do not use this, unless you're a singleton server...
	 */
	final synchronized public void tickSync() {
		for (final long n : syncRunnables.keySet())
			syncRunnables.get(n).tick(this, n);
	}

	/**
	 * Create a task that will be ticked using a special async thread, and thus
	 * will NOT be synchronized. Use this when executing code that would block
	 * the main thread, and making the tickrate go down.
	 * <p/>
	 * An example would be SQL Queries or heavy math
	 *
	 * @param runnable
	 *            Runnable to create
	 * @return Runnable ID
	 */
	public long createAsyncTask(final MarineRunnable runnable) {
		final long id = getNextId();
		asyncRunnables.put(id, runnable);
		return id;
	}

	/**
	 * Get a task based on its ID. This will check both the list of async and
	 * sync runnables, so you do not need to know if the task is sync or async.
	 * <p/>
	 * The method will return null if the runnable isn't found in any of the
	 * lists.
	 *
	 * @param n
	 *            Task ID
	 * @return Task, if found - else null
	 * @code { MarineRunnable r = getTask(32l); if (r == null) // your code else
	 *       // was found }
	 */
	public MarineRunnable getTask(final long n) {
		if (asyncRunnables.containsKey(n))
			return asyncRunnables.get(n);
		if (syncRunnables.containsKey(n))
			return syncRunnables.get(n);
		return null;
	}

	/**
	 * Create a task that will be ticked using the main thread, and thus will be
	 * synchronized. Use this when accessing API methods that involves changing
	 * global states (most of the internal methods)
	 *
	 * @param runnable
	 *            Runnable to start
	 * @return Task ID
	 */
	public long createSyncTask(final MarineRunnable runnable) {
		final long id = getNextId();
		syncRunnables.put(id, runnable);
		return id;
	}

	public void removeAll(final ServiceProvider provider) {
		synchronized (this) {
			for (final Map.Entry<Long, MarineRunnable> r : asyncRunnables
					.entrySet())
				if (r.getValue().getProvider().equals(provider))
					removeTask(r.getKey());
			for (final Map.Entry<Long, MarineRunnable> r : syncRunnables
					.entrySet())
				if (r.getValue().getProvider().equals(provider))
					removeTask(r.getKey());
		}
	}
}
