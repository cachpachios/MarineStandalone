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
