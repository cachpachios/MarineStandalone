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

package com.marine.util.mojang;

import java.util.UUID;

/**
 * UUID Saver / Fetcher
 *
 * @author Citymonstret
 */
public interface UUIDSaver {

    /**
     * Fetch uuid from mojang servers
     *
     * @param name Username
     * @return uuid
     * @throws Exception
     */
    public UUID mojangUUID(final String name) throws Exception;

    /**
     * Fetch username from mojang servers
     *
     * @param uuid UUID
     * @return username
     * @throws Exception
     */
    public String mojangName(final UUID uuid) throws Exception;
}