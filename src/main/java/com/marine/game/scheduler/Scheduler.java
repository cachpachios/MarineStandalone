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

package com.marine.game.scheduler;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;

import java.lang.ref.WeakReference;
import java.util.Map;

/**
 * Created 2014-12-08 for MarineStandalone
 *
 * @author Citymonstret
 */
public class Scheduler extends Thread {

    private final BiMap<Long, MarineRunnable> asyncRunnables;
    private final BiMap<Long, MarineRunnable> syncRunnables;
    private long id;
    private int tick = 0;

    public Scheduler() {
        this.id = 0;
        this.asyncRunnables = HashBiMap.create();
        this.syncRunnables = HashBiMap.create();
    }

    public long getNextId() {
        return this.id++;
    }

    public boolean removeTask(final MarineRunnable runnable) {
        for (Map.Entry<Long, MarineRunnable> sync : syncRunnables.entrySet()) {
            if (sync.getValue().equals(runnable)) {
                removeTask(sync.getKey());
                return true;
            }
        }
        for (Map.Entry<Long, MarineRunnable> async : asyncRunnables.entrySet()) {
            if (async.getValue().equals(runnable)) {
                removeTask(async.getKey());
                return true;
            }
        }
        return false;
    }

    public void removeTask(final long l) {
        if (syncRunnables.containsKey(l)) {
            syncRunnables.remove(l);
        } else if (asyncRunnables.containsKey(l)) {
            asyncRunnables.remove(l);
        }
    }

    private void tickAsync() {
        for (long n : asyncRunnables.keySet()) {
            asyncRunnables.get(n).tick(this, n);
        }
    }

    public void tickSync() {
        for (long n : syncRunnables.keySet()) {
            syncRunnables.get(n).tick(this, n);
        }
    }

    public long createAsyncTask(final MarineRunnable runnable) {
        long id = getNextId();
        asyncRunnables.put(id, runnable);
        return id;
    }

    public long createSyncTask(final MarineRunnable runnable) {
        long id = getNextId();
        syncRunnables.put(id, runnable);
        return id;
    }

    @Override
    public void run() {
        tickAsync();
    }

    public void forceGC(MarineRunnable runnable) {
        WeakReference ref = new WeakReference<>(runnable);
        runnable = null;
        while (ref.get() != null) {
            System.gc();
        }
    }
}
