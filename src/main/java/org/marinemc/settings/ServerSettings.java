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

package org.marinemc.settings;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.marinemc.logging.Logging;

/**
 * Server settings (.properties) implementation
 *
 * @author Citymonstret
 */
public class ServerSettings {

	private static ServerSettings instance;
	public int port = 25565;
	public int tickrate = 20;
	public int maxPlayers = 20;
	public int cacheHours = 720;
	public int network_threshould = 256;
	public String host = "0.0.0.0";
	public String motd = "&cNo MOTD";
	public String gamemode = "survival";
	public boolean useHasing, offlineMode, whitelist;
	public String difficulty = "peaceful";
	private Properties config;

	public ServerSettings() {
		try {
			config = new Properties();
			final File file = new File("./settings.properties");
			if (!file.exists())
				if (!file.createNewFile())
					throw new RuntimeException(
							"Failed to create settings.properties");
			final BufferedReader reader = new BufferedReader(new FileReader(
					file));
			// config.load(new FileInputStream(file));
			config.load(reader);
			final Map<String, String> options = new HashMap<String, String>() {
				private static final long serialVersionUID = 0L;

				{
					put("host", "127.0.0.1");
					put("port", "25565");
					put("motd", "&cTesting...");
					put("tickrate", "20");
					put("useHashing", "false"); // Depending on what server you
												// are running hashing is best
												// for big servers with many
												// players online, linear
												// scanning for small servers
												// with less players
					put("network-threshould", "256");
					put("gamemode", "survival");
					put("difficulty", "peaceful");
					put("maxPlayers", "20");
					put("offlineMode", "false");
					put("cacheHours", "720");
					put("whitelist", "false");
				}
			};

			boolean changed = false;

			for (final String string : options.keySet())
				if (!config.containsKey(string)) {
					config.setProperty(string, options.get(string));
					changed = true;
				}

			if (changed)
				config.store(new FileOutputStream(file), null);

			port = getInt(config.getProperty("port"));
			host = config.getProperty("host");
			motd = config.getProperty("motd");
			tickrate = getInt(config.getProperty("tickrate"));
			useHasing = getBoolean(config.getProperty("useHashing"));
			gamemode = config.getProperty("gamemode");
			difficulty = config.getProperty("difficulty");
			maxPlayers = getInt(config.getProperty("maxPlayers"));
			offlineMode = getBoolean(config.getProperty("offlineMode"));
			whitelist = getBoolean(config.getProperty("whitelist"));
			cacheHours = getInt(config.getProperty("cacheHours"));
			network_threshould = getInt(config
					.getProperty("network-threshould"));
			reader.close();
		} catch (final Exception e) {
			e.printStackTrace();
		}
	}

	public static ServerSettings getInstance() {
		if (instance == null)
			instance = new ServerSettings();
		return instance;
	}

	public boolean getBoolean(final String value) {
		return value.equalsIgnoreCase("true") || value.equals("1")
				|| value.equalsIgnoreCase("on")
				|| value.equalsIgnoreCase("yes");
	}

	public int getInt(final String value) {
		try {
			return Integer.parseInt(value);
		} catch (final Throwable e) {
			return -1;
		}
	}

	@SuppressWarnings("rawtypes")
	public void verbose() {
		for (final Map.Entry entry : config.entrySet())
			Logging.getLogger().debug(
					"Key: " + entry.getKey() + " | Value: " + entry.getValue());
	}

}
