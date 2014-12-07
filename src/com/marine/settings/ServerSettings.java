package com.marine.settings;

import com.marine.Logging;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * Created 2014-12-02 for MarineStandalone
 *
 * @author Citymonstret
 */
public class ServerSettings {

    private static ServerSettings instance;
    public int port = 25565;
    public int tickrate = 20;
    public String host = "0.0.0.0";
    public String motd = "&cNo MOTD";
    private Properties config;

    public ServerSettings() {
        try {
            config = new Properties();
            File file = new File("./settings.properties");
            if (!file.exists()) {
                if (!file.createNewFile())
                    throw new RuntimeException("Failed to create settings.properties");
            }
            config.load(new FileInputStream(file));
            Map<String, String> options = new HashMap<String, String>() {
                {
                    put("host", "127.0.0.1");
                    put("port", "25565");
                    put("motd", "&cTesting...");
                    put("tickrate", "20");
                }
            };

            boolean changed = false;

            for (String string : options.keySet()) {
                if (!config.containsKey(string)) {
                    config.setProperty(string, options.get(string));
                    changed = true;
                }
            }

            if (changed)
                config.store(new FileOutputStream(file), null);

            this.port = getInt(config.getProperty("port"));
            this.host = config.getProperty("host");
            this.motd = config.getProperty("motd");
            this.tickrate = getInt(config.getProperty("tickrate"));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static ServerSettings getInstance() {
        if (instance == null) {
            instance = new ServerSettings();
        }
        return instance;
    }

    public int getInt(String value) {
        try {
            return Integer.parseInt(value);
        } catch (Throwable e) {
            return -1;
        }
    }

    public void verbose() {
        for (Map.Entry entry : config.entrySet()) {
            Logging.getLogger().log("Key: " + entry.getKey() + " | Value: " + entry.getValue());
        }
    }

}
