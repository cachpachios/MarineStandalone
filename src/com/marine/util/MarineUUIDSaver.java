package com.marine.util;

import java.net.URL;
import java.net.URLConnection;
import java.util.UUID;

import org.json.simple.JSONObject;

/**
 * Created 2014-12-02 for MarineStandalone
 *
 * @author Citymonstret
 */
public class MarineUUIDSaver implements UUIDSaver {

    @Override
    public UUID mojangUUID(final String name) throws Exception {
        final URLConnection connection = new URL("http://intellectualsites.com/minecraft.php?user=" + name).openConnection();
        connection.addRequestProperty("User-Agent", "Mozilla/4.0");
        final JSONTokener tokener = new JSONTokener(connection.getInputStream());
        final JSONObject root = new JSONObject(tokener);
        final String uuid = root.getJSONObject(name).getString("dashed");
        return UUID.fromString(uuid);
    }

    @Override
    public String mojangName(final UUID uuid) throws Exception {
        final URLConnection connection = new URL("http://intellectualsites.com/minecraft.php?user=" + uuid.toString().replace("-", "")).openConnection();
        connection.addRequestProperty("User-Agent", "Mozilla/4.0");
        final JSONTokener tokener = new JSONTokener(connection.getInputStream());
        final JSONObject root = new JSONObject(tokener);
        return root.getJSONObject(uuid.toString().replace("-", "")).getString("username");
    }

}
