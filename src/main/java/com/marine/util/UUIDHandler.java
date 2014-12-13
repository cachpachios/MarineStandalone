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

import com.google.common.base.Charsets;
import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.marine.player.Player;
import com.marine.server.Marine;

import java.util.HashMap;
import java.util.UUID;

/**
 * Created 2014-12-02 for MarineStandalone
 *
 * @author Citymonstret
 */
@SuppressWarnings({"unused", "javadoc"})
public class UUIDHandler {

    private final static boolean online = true;

    private final static BiMap<StringWrapper, UUID> uuidMap = HashBiMap.create(new HashMap<StringWrapper, UUID>());

    private static UUIDSaver uuidSaver;

    protected static UUIDSaver uuidHandler() {
        if (uuidSaver == null)
            uuidSaver = new MarineUUIDSaver();
        return uuidSaver;
    }

    public static BiMap<StringWrapper, UUID> getUuidMap() {
        return uuidMap;
    }

    public static boolean uuidExists(final UUID uuid) {
        return uuidMap.containsValue(uuid);
    }

    public static boolean nameExists(final StringWrapper name) {
        return uuidMap.containsKey(name);
    }

    public static void add(final StringWrapper name, final UUID uuid) {
        if (!uuidMap.containsKey(name) && !uuidMap.inverse().containsKey(uuid)) {
            uuidMap.put(name, uuid);
        }
    }

    /**
     * @param name to use as key
     * @return uuid
     */
    public static UUID getUUID(final String name) {
        final StringWrapper nameWrap = new StringWrapper(name);
        if (uuidMap.containsKey(nameWrap)) {
            return uuidMap.get(nameWrap);
        }
        @SuppressWarnings("deprecation")
        final Player player = Marine.getPlayer(name);
        if (player != null) {
            final UUID uuid = player.getUUID();
            add(nameWrap, uuid);
            return uuid;
        }
        UUID uuid;
        if (online) {
            if ((uuid = getUuidOnlinePlayer(nameWrap)) != null) {
                return uuid;
            }
            try {
                return uuidHandler().mojangUUID(name);

            } catch (final Exception e) {
                /*try {
                    final UUIDFetcher fetcher = new UUIDFetcher(Arrays.asList(name));
                    uuid = fetcher.call().get(name);
                    add(nameWrap, uuid);
                } catch (final Exception ex) {
                    ex.printStackTrace();
                }*/
            }
        } else {
            return getUuidOfflineMode(nameWrap);
        }
        return null;
    }

    /**
     * @param uuid to use as key
     * @return name (cache)
     */
    private static StringWrapper loopSearch(final UUID uuid) {
        return uuidMap.inverse().get(uuid);
    }

    /**
     * @param uuid to use as key
     * @return Name
     */
    public static String getName(final UUID uuid) {
        if (uuidExists(uuid)) {
            return loopSearch(uuid).value;
        }
        String name;
        if ((name = getNameOnlinePlayer(uuid)) != null) {
            return name;
        }
       /* if ((name = getNameOfflinePlayer(uuid)) != null) {
            return name;
        }*/
        if (online) {
            try {
                return uuidHandler().mojangName(uuid);
            } catch (final Exception e) {
                /*try {
                    final NameFetcher fetcher = new NameFetcher(Arrays.asList(uuid));
                    name = fetcher.call().get(uuid);
                    add(new StringWrapper(name), uuid);
                    return name;
                } catch (final Exception ex) {
                    ex.printStackTrace();
                }*/
            }
        } else {
            return "unknown";
        }
        return "";
    }

    /**
     * @param name to use as key
     * @return UUID (name hash)
     */
    private static UUID getUuidOfflineMode(final StringWrapper name) {
        final UUID uuid = UUID.nameUUIDFromBytes(("OfflinePlayer:" + name).getBytes(Charsets.UTF_8));
        add(name, uuid);
        return uuid;
    }

    /**
     * @param uuid to use as key
     * @return String - name
     */
    private static String getNameOnlinePlayer(final UUID uuid) {
        final Player player = Marine.getPlayer(uuid);
        if ((player == null) || !player.isOnline()) {
            return null;
        }
        final String name = player.getName();
        add(new StringWrapper(name), uuid);
        return name;
    }

    /*
    private static String getNameOfflinePlayer(final UUID uuid) {
        final OfflinePlayer player = Bukkit.getOfflinePlayer(uuid);
        if ((player == null) || !player.hasPlayedBefore()) {
            return null;
        }
        final String name = player.getName();
        add(new StringWrapper(name), uuid);
        return name;
    }*/


    /**
     * @param name to use as key
     * @return UUID
     */
    private static UUID getUuidOnlinePlayer(final StringWrapper name) {
        @SuppressWarnings("deprecation")
        final Player player = Marine.getPlayer(name.value);
        if (player == null) {
            return null;
        }
        final UUID uuid = player.getUUID();
        add(name, uuid);
        return uuid;
    }

}