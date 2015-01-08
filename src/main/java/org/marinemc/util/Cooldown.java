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

package org.marinemc.util;

/**
 * Cooldown Utility (long desc.)
 * <p/>
 * <p/>
 * Use this instead of Runnables to compare cooldowns, as these rely on much
 * simpler data, and won't cause memory leaks. Nor will they cause problems if
 * the player is absent when the runnable executes. This also allows for more
 * specific time indications.
 * <p/>
 *
 * @author Citymonstret
 *         <p/>
 *         Example:
 * @code {msg("You have {0} seconds left", cd.getSecondsLeft())}
 */
public class Cooldown {

	private final long start;
	private final long time;

	/**
	 * Constructor
	 *
	 * @param time
	 *            Milliseconds until completion
	 */
	public Cooldown(final long time) {
		start = System.currentTimeMillis();
		if (time <= 0)
			throw new IllegalArgumentException("time must be greater than 0");
		this.time = time;
	}

	/**
	 * Check if the cooldown has ran out
	 *
	 * @return true if it has run out
	 */
	public boolean check() {
		return System.currentTimeMillis() - start >= time;
	}

	/**
	 * Get milliseconds left until completion
	 *
	 * @return milliseconds
	 */
	public long getMillisecondsLeft() {
		final long n = time - (System.currentTimeMillis() - start);
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

	/**
	 * Get the formatted time
	 *
	 * @return Formatted time
	 */
	public String formatTime() {
		double sec, min, hou;
		sec = getSecondsLeft();
		min = sec % 3600 / 60;
		hou = sec / 3600;
		sec %= 60;
		final String hS = (int) hou + " " + ((int) hou != 1 ? "hours" : "hour"), mS = (int) min
				+ " " + ((int) min != 1 ? "minutes" : "minute"), sS = (int) sec
				+ " " + ((int) sec != 1 ? "seconds" : "second");
		final StringBuilder builder = new StringBuilder();
		boolean p;
		if (hou >= 1) {
			builder.append(hS);
			p = true;
		} else
			p = false;
		if (min >= 1) {
			if (p)
				builder.append(", ");
			p = true;
			builder.append(mS);
		} else
			p = false;
		if (sec >= 1) {
			if (p)
				builder.append(" and ");
			else
				return builder.append(sS).toString().replace(",", " and");
			builder.append(sS);
		}
		return builder.toString();
	}

}
