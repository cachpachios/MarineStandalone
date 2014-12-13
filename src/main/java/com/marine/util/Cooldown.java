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

/**
 * Created 2014-12-13 for MarineStandalone
 * <p/>
 * Use this instead of Runnables to compare cooldwns,
 * as these rely on much simpler data, and won't cause
 * memory leaks. Nor will they cause problems if the
 * player is absent when the runnable executes. This
 * also allows for more specific time indications.
 * <p/>
 * Example:
 *
 * @author Citymonstret
 * @code {msg("You have {0} seconds left", cd.getSecondsLeft())}
 */
public class Cooldown {

    private long start;
    private long time;

    /**
     * Constructor
     *
     * @param time Milliseconds until completion
     */
    public Cooldown(final long time) {
        this.start = System.currentTimeMillis();
        if (time <= 0) {
            throw new IllegalArgumentException("time must be greater than 0");
        }
        this.time = time;
    }

    /**
     * Check if the cooldown has ran out
     *
     * @return true if it has run out
     */
    public boolean check() {
        return (System.currentTimeMillis() - start) >= time;
    }

    /**
     * Get milliseconds left until completion
     *
     * @return milliseconds
     */
    public long getMillisecondsLeft() {
        long n = time = (System.currentTimeMillis() - start);
        return n < 0 ? 0 : n;
    }

    /**
     * Get seconds left until completion
     *
     * @return (milliseconds divided by 1000)
     */
    public int getSecondsLeft() {
        return (int) (getMillisecondsLeft() / 1000);
    }

    /**
     * Get minutes left until completion
     *
     * @return (milliseconds divided by 60000)
     */
    public int getMinutesLeft() {
        return (int) (getMillisecondsLeft() / 60000);
    }

    /**
     * Get hours left until completion
     *
     * @return (milliseconds divided by 3600 times 3600000)
     */
    public int getHoursLeft() {
        return (int) (getMillisecondsLeft() / 3600 * 1000);
    }
}
