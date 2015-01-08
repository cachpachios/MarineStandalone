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

package org.marinemc.game.player;

import java.util.IdentityHashMap;
import java.util.Map;

/**
 * Used to generator a unique uid for players represented by a (unsigned) short
 *
 * @author Fozie
 */
public class UIDGenerator {

	private static UIDGenerator instance;
	private final Map<Integer, Short> UIDMap;
	private short nextUnassigned;

	private UIDGenerator() {
		UIDMap = new IdentityHashMap<>(Short.MAX_VALUE);
		nextUnassigned = Short.MIN_VALUE;
	}

	/**
	 * Get the instance
	 *
	 * @return instance
	 */
	public static UIDGenerator instance() {
		if (instance == null)
			instance = new UIDGenerator();
		return instance;
	}

	/**
	 * Return the UID for the player, if no one can be found will return
	 * Short.MIN_VALUE
	 *
	 * @param username
	 *            Player Username
	 * @return UID
	 */
	public short getUID(final String username) {
		if (UIDMap.containsKey(username.hashCode()))
			return UIDMap.get(username.hashCode());
		short uid = ++nextUnassigned;

		if (uid == -1)
			uid = ++nextUnassigned;

		if (UIDMap.containsValue(uid))
			while (true) {
				uid = ++nextUnassigned;
				if (uid == -1)
					continue;

				if (!UIDMap.containsValue(uid))
					return uid;
			}
		else
			return uid;
	}
}
