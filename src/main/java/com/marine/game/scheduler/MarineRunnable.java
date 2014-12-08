package com.marine.game.scheduler;

/**
 * Created 2014-12-08 for MarineStandalone
 *
 * @author Citymonstret
 */
public abstract class MarineRunnable {

    private long requiredTick;
    private long tick;

    public MarineRunnable(final long requiredTick) {
        this.requiredTick = requiredTick;
        this.tick = 0;
    }

    final public void tick() {
        ++this.tick;
        if(this.tick >= this.requiredTick) {
            this.run();
            this.tick = 0;
        }
    }

    public abstract void run();

}
