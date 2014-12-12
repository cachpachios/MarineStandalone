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

package com.marine.settings;

import com.marine.StandaloneServer;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;

/**
 * Created 2014-12-03 for MarineStandalone
 *
 * @author Citymonstret
 */
public class JSONFileHandler {

    private final StandaloneServer server;
    private final JSONConfig administrators, banned, whitelist;
    private final File settingsPath, storagePath;
    public JSONFileHandler(final StandaloneServer server, final File settingsPath, final File storagePath) throws JSONException {
        this.server = server;
        this.settingsPath = settingsPath;
        this.storagePath = storagePath;
        this.administrators = new StorageConfig(storagePath, "administrators");
        this.banned = new StorageConfig(storagePath, "banned");
        this.whitelist = new StorageConfig(storagePath, "whitelist");
    }

    public void saveAll() {
        this.administrators.saveFile();
        this.banned.saveFile();
        this.whitelist.saveFile();
    }

    public JSONObject getAdministratorJSONObject() {
        return this.administrators.map;
    }

    public JSONObject getBannedJSONObject() {
        return this.banned.map;
    }

    public JSONObject getWhitelistJSONObject() {
        return this.whitelist.map;
    }

    public File getSettingsPath() {
        return this.settingsPath;
    }

    public File getStoragePath() {
        return this.storagePath;
    }

    private class StorageConfig extends JSONConfig {

        public StorageConfig(File file) {
            super(file);
        }

        public StorageConfig(File parent, String file) throws JSONException {
            super(parent, file);
        }

       /*@Override
        public Map<String, Object> defaultValues() {
            Map<String, Object> defaultUUIDS = new HashMap<>();
            try {
                defaultUUIDS.put("uuid", new JSONArray(new String[]{UUIDHandler.getUUID("notch").toString()}));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return defaultUUIDS;
        }*/

    }
}
