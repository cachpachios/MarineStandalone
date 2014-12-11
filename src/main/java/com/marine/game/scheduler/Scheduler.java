package com.marine.game.scheduler;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;

/**
 * Created 2014-12-08 for MarineStandalone
 *
 * @author Citymonstret
 */
public class Scheduler extends Thread {

    private long id;
    private final BiMap<Long, MarineRunnable> asyncRunnables;
    private final BiMap<Long, MarineRunnable> syncRunnables;

    public Scheduler() {
        this.id = 0;
        this.asyncRunnables = HashBiMap.create();
        this.syncRunnables = HashBiMap.create();
    }

    public long getNextId() {
        return this.id++;
    }

    public void removeTask(final long l) {
        if(syncRunnables.containsKey(l)) {
            syncRunnables.remove(l);
        } else if(asyncRunnables.containsKey(l)) {
            asyncRunnables.remove(l);
        }
    }

    private void tickAsync() {
        for(long n : asyncRunnables.keySet()) {
            asyncRunnables.get(n).tick(this, n);
        }
    }

    public void tickSync() {
        for(long n : syncRunnables.keySet()) {
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

    private int tick = 0;

    @Override
    public void run() {
        tickAsync();
    }
}
