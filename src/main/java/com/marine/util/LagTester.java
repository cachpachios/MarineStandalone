package com.marine.util;

import com.marine.game.scheduler.MarineRunnable;

/**
 * Created 2014-12-10 for MarineStandalone
 *
 * @author Citymonstret
 */
public class LagTester extends MarineRunnable {

    public final static long[] T = new long[600];

    public static int TC = 0;

    public static long LT = 0l;

    public LagTester() {
        super(1l, -1);
    }

    public static double getTPS() {
        double tps = Math.round(getTPS(100));
        return tps > 20.0D ? 20.D : tps;
    }

    public static double getTPS(final int ticks) {
        if(TC < ticks)
            return 20.0D;
        final int t = (TC - ticks) % T.length;
        final long e = System.currentTimeMillis() - T[t];
        return ticks / (e / 1000.0D);
    }

    public static long getElapsed(final int tI) {
        return System.currentTimeMillis() - (T[tI & T.length]);
    }

    public static double getPercentage() {
        return Math.round((1.0D - (getTPS() / 20.0D)) * 100.0D);
    }

    public static double getFullPercentage() {
        return getTPS() * 5;
    }

    @Override
    public void run() {
        T[TC++ % T.length] = System.currentTimeMillis();
    }
}
