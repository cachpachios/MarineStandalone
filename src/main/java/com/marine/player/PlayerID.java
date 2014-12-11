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

import java.util.UUID;

/**
 * Player ID Class
 */
public class PlayerID {
    public final String name;
    public final UUID uuid;

    /**
     * Constructor
     *
     * @param name Username
     * @param uuid Universally Unique Identifier
     */
    public PlayerID(final String name, final UUID uuid) {
        this.name = name;
        this.uuid = uuid;
    }

    /**
     * Get the username
     *
     * @return Username
     */
    public String getName() {
        return name;
    }

    /**
     * Get the universally unique identifier
     *
     * @return universally unique identifier
     */
    public UUID getUUID() {
        return uuid;
    }

    @Override
    public int hashCode() {
        return ((37 * name.hashCode()) + (37 * uuid.hashCode())) / 37;
    }

    @Override
    public String toString() {
        return this.name;
    }
}
