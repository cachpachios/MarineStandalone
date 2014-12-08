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
    private final BiMap<Long, MarineRunnable> runnables;

    public Scheduler() {
        this.id = 0;
        this.runnables = HashBiMap.create();
    }

    public long getNextId() {
        return this.id++;
    }

    public void removeTask(final long l) {
        if(runnables.containsKey(l)) {
            runnables.remove(l);
        }
    }

    private void tickAll() {
        for(MarineRunnable runnable : runnables.values()) {
            runnable.tick();
        }
    }

    public long createTask(final MarineRunnable runnable) {
        long id = getNextId();
        runnables.put(id, runnable);
        return id;
    }

    private final long total = (1000 / 20);
    private long lastTick = -1l;

    @Override
    public void run() {
        if(lastTick == -1l) {
            lastTick = System.currentTimeMillis();
        }
        if((System.currentTimeMillis() - lastTick) >= total) {
            lastTick = System.currentTimeMillis();
            tickAll();
        }
        run();
    }
}
