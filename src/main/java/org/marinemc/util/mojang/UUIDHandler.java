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

package org.marinemc.util.mojang;

import com.google.common.base.Charsets;
import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.marinemc.game.player.Player;
import org.marinemc.server.Marine;
import org.marinemc.settings.JSONConfig;
import org.marinemc.util.StringUtils;

import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

/**
 * UUID Handler - Undocumented!
 *
 * @author Citymonstret
 */
@SuppressWarnings({"unused", "javadoc"})
public class UUIDHandler {

    private static UUIDHandler instance;
    private final BiMap<String, String> uuidMap;
    private final boolean online;
    private final JSONParser parser;
    private final JSONConfig file;

    public UUIDHandler() {
        uuidMap = HashBiMap.create();
        online = !Marine.getServer().isOfflineMode();
        parser = new JSONParser();
        file = new JSONConfig(Marine.getServer().getStorageFolder(), "uuids");

        Set keys = file.map.keySet();
        String name, uuid;
        for (Object o : keys) {
            try {
                uuid = o.toString();
                name = file.map.getString(uuid);
                add(name, UUID.fromString(uuid));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static UUIDHandler instance() {
        if (instance == null) {
            instance = new UUIDHandler();
        }
        return instance;
    }

    public void save() {
        for (Map.Entry<String, String> entries : uuidMap.entrySet()) {
            file.setIfNull(entries.getValue(), entries.getKey());
        }
        file.saveFile();
    }

    public Map<String, String> getMap() {
        return uuidMap;
    }

    private void add(final String name, final UUID uuid) {
        if (!uuidMap.containsKey(name) && !uuidMap.inverse().containsKey(uuid.toString())) {
            uuidMap.put(name, uuid.toString());
        }
    }

    public boolean uuidExists(final UUID uuid) {
        return uuidMap.inverse().containsKey(uuid.toString());
    }

    public boolean nameExist(final String name) {
        return uuidMap.containsKey(name);
    }

    public UUID getUUID(final String name) {
        if (nameExist(name)) {
            return UUID.fromString(uuidMap.get(name));
        }
        final Player player = Marine.getPlayer(name);
        if (player != null) {
            final UUID uuid = player.getUUID();
            add(name, uuid);
            return uuid;
        }
        if (online) {
            try {
                UUID uuid = getMojangUUID(name);
                add(name, uuid);
                return uuid;
            } catch (Throwable e) {
                e.printStackTrace();
            }
        } else {
            return getUuidOfflineMode(name);
        }
        return null;
    }

    public String getName(final UUID uuid) {
        if (uuidExists(uuid)) {
            return uuidMap.inverse().get(uuid.toString());
        }
        final Player player = Marine.getPlayer(uuid);
        if (player != null) {
            final String name = player.getUserName();
            add(name, uuid);
            return name;
        }
        if (online) {
            try {
                String name = getMojangName(uuid);
                add(name, uuid);
                return name;
            } catch (Throwable e) {
                e.printStackTrace();
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
    public UUID getUuidOfflineMode(final String name) {
        final UUID uuid = UUID.nameUUIDFromBytes(("OfflinePlayer:" + name).getBytes(Charsets.UTF_8));
        add(name, uuid);
        return uuid;
    }

    private String getMojangName(UUID uuid) throws Throwable {
        HttpURLConnection connection = (HttpURLConnection) new URL("https://sessionserver.mojang.com/session/minecraft/profile/" + uuid.toString().replace("-", "")).openConnection();
        JSONObject response = (JSONObject) parser.parse(new InputStreamReader(connection.getInputStream()));
        if (response.get("cause") != null) {
            throw new IllegalStateException(response.get("errorMessage").toString());
        }
        String name = response.get("name").toString();
        connection.disconnect();
        return name;
    }

    private UUID getMojangUUID(String name) throws Throwable {
        HttpURLConnection connection = (HttpURLConnection) (new URL("https://api.mojang.com/profiles/minecraft").openConnection());
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setUseCaches(false);
        connection.setDoInput(true);
        connection.setDoOutput(true);
        String request = JSONArray.toJSONString(Arrays.asList(name));
        {
            final OutputStream stream = connection.getOutputStream();
            stream.write(request.getBytes());
            stream.flush();
            stream.close();
        }
        final JSONArray array = (JSONArray) parser.parse(new InputStreamReader(connection.getInputStream()));
        UUID uuid = null;
        for (Object o : array) {
            uuid = UUID.fromString(StringUtils.fixUUID(((JSONObject) o).get("id").toString()));
        }
        connection.disconnect();
        if (uuid == null) {
            throw new NullPointerException("Couldn't fetch the uuid");
        }
        return uuid;
    }
}