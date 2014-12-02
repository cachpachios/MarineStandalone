package com.marine.settings;

import com.marine.Logging;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Map;
import java.util.Properties;

/**
 * Created 2014-12-02 for MarineStandalone
 *
 * @author Citymonstret
 */
public class ServerSettings {

    private static ServerSettings instance;

    private Properties config;

    public int port = 25565;
    public String host = "0.0.0.0";
    public String motd = "&cNo MOTD";

    public ServerSettings() {
        try {
            config = new Properties();
            File file = new File("./settings.properties");
            if (!file.exists()) {
                if (!file.createNewFile())
                    throw new RuntimeException("Failed to create settings.properties");
                config.setProperty("host", "127.0.0.1");
                config.setProperty("port", "25565");
                config.setProperty("motd", "&cTesting...");
                config.store(new FileOutputStream(file), null);
            }
            config.load(new FileInputStream(file));

            this.port = getInt(config.getProperty("port"));
            this.host = config.getProperty("host");
            this.motd = config.getProperty("motd");

        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public int getInt(String value) {
        try {
            return Integer.parseInt(value);
        } catch(Throwable e) {
            return -1;
        }
    }

    public static ServerSettings getInstance() {
        if(instance == null) {
            instance = new ServerSettings();
        }
        return instance;
    }

    public void verbose() {
        for(Map.Entry entry : config.entrySet()) {
            Logging.getLogger().log("Key: " + entry.getKey() + " | Value: " + entry.getValue());
        }
    }

}
