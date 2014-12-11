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
        if (TC < ticks)
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
