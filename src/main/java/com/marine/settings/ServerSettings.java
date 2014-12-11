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
    public boolean useHasing;
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
                private static final long serialVersionUID = 0L;

                {
                    put("host", "127.0.0.1");
                    put("port", "25565");
                    put("motd", "&cTesting...");
                    put("tickrate", "20");
                    put("useHashing", "false"); // Depending on what server you are running hashing is best for big servers with many players online, linear scanning for small servers with less players
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
            this.useHasing = getBoolean(config.getProperty("useHashing"));

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

    public boolean getBoolean(String value) {
        if (value == "true")
            return true;
        else
            return false;
    }

    public int getInt(String value) {
        try {
            return Integer.parseInt(value);
        } catch (Throwable e) {
            return -1;
        }
    }

    @SuppressWarnings("rawtypes")
    public void verbose() {
        for (Map.Entry entry : config.entrySet()) {
            Logging.getLogger().log("Key: " + entry.getKey() + " | Value: " + entry.getValue());
        }
    }

}
