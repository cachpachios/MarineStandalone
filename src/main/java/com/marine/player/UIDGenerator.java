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

package com.marine.player;

import java.util.HashMap;
import java.util.Map;

/**
* Used to generator a unique uid for players represented by a (unsigned) short
*
* @author Fozie
*/ 
public class UIDGenerator {

	private static UIDGenerator instance;
	private Map<Integer, Short> UIDMap;
	private short nextUnassigned = Short.MIN_VALUE;
	
	public UIDGenerator() {
		UIDMap = new HashMap<>();
		nextUnassigned = Short.MIN_VALUE;
	}

	public static UIDGenerator instance() {
		if (instance == null)
			instance = new UIDGenerator();
		return instance;
	}

	public short getUID(String username) { // Will return a UID for the player, if no one can be found will return Short.MIN_VALUE
		if(UIDMap.containsKey(username.hashCode()))
			return UIDMap.get(username.hashCode());
		short uid = ++nextUnassigned;
		try {
			while(UIDMap.containsValue(uid)) // Too make sure u get a unused uid
				uid = ++nextUnassigned;
		} catch(Exception e) {
			return Short.MIN_VALUE; // Error code
		}
		return uid;
	}
}
