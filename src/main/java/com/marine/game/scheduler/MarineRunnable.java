package com.marine.game.scheduler;

/**
 * Created 2014-12-08 for MarineStandalone
 *
 * @author Citymonstret
 */
public abstract class MarineRunnable {

    private long requiredTick;
    private long tick;
    private long totalRuns;
    private long ran;

    /**
     * Constructor
     *
     * @param requiredTick ticks between each #run();
     * @param runs         number of times the runnable will run, set to -1 for inf.
     */
    public MarineRunnable(final long requiredTick, final long runs) {
        this.requiredTick = requiredTick;
        this.totalRuns = runs;
        this.ran = 0;
        this.tick = 0;
    }

    /**
     * Will cancel the task, and GC the object
     *
     * @param scheduler Scheduler class
     */
    public void cancel(final Scheduler scheduler, final long n) {
        scheduler.removeTask(n);
        this.requiredTick = Long.MIN_VALUE;
        this.tick = Long.MAX_VALUE;
        this.totalRuns = Long.MIN_VALUE;
        this.ran = Long.MAX_VALUE;
        // Will make sure to GC it, to free up some memory
        scheduler.forceGC(this);
    }

    final public void tick(Scheduler scheduler, long n) {
        ++this.tick;
        if (this.tick >= this.requiredTick) {
            if ((totalRuns == -1 || ran++ <= totalRuns)) {
                this.run();
                this.tick = 0;
            } else {
                this.cancel(scheduler, n);
            }
        }
    }

    public abstract void run();
}
