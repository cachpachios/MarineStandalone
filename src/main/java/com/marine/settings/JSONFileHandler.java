package com.marine.settings;

import com.marine.StandaloneServer;
import com.marine.util.UUIDHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * Created 2014-12-03 for MarineStandalone
 *
 * @author Citymonstret
 */
public class JSONFileHandler {

    private class StorageConfig extends JSONConfig {

        public StorageConfig(File file) {
            super(file);
        }

        public StorageConfig(File parent, String file) {
            super(parent, file);
        }

        @Override
        public Map<String, Object> defaultValues() {
            Map<String, Object> defaultUUIDS = new HashMap<>();
            try {
				defaultUUIDS.put("uuid", new JSONArray(new String[] { UUIDHandler.getUUID("notch").toString() }));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return defaultUUIDS;
        }

    }

    private final StandaloneServer server;
    private final JSONConfig administrators, banned, whitelist;
    private final File settingsPath, storagePath;

    public JSONFileHandler(final StandaloneServer server, final File settingsPath, final File storagePath) {
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
}
