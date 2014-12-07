package com.marine.settings;

import com.marine.StandaloneServer;
import com.marine.util.UUIDHandler;
import org.json.JSONArray;
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

    public JSONFileHandler(final StandaloneServer server, final File settingsPath, final File storagePath) {
        this.server = server;
        this.settingsPath = settingsPath;
        this.storagePath = storagePath;
        this.administrators =
                new JSONConfig(storagePath, "administrators");
        this.banned =
                new JSONConfig(storagePath, "banned");
        this.whitelist =
                new JSONConfig(storagePath, "whitelist");
    }

    public void loadAll() {
        this.administrators.read();
        this.banned.read();
        this.whitelist.read();
    }

    public void defaultValues() throws Throwable {
        JSONObject object;
        JSONArray defaultValues = new JSONArray();
        defaultValues.put(UUIDHandler.getUUID("notch").toString());
        // Administrators
        {
            object = getAdministratorJSONObject();
            if (object.isNull("administrators")) {
                object.put("administrators", defaultValues);
            }
        }
        // Banned
        {
            object = getBannedJSONObject();
            if (object.isNull("banned-players")) {
                object.put("banned-players", defaultValues);
            }
            if (object.isNull("banned-ips")) {
                object.put("banned-ips", new JSONArray(new String[]{"127.0.0.1"}));
            }
        }
        // Whitelist
        {
            object = getWhitelistJSONObject();
            if (object.isNull("whitelisted")) {
                object.put("whitelisted", defaultValues);
            }
        }
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
