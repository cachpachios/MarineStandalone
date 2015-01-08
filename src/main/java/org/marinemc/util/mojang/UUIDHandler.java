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

import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import jdk.nashorn.internal.parser.JSONParser;

import org.marinemc.Bootstrap;
import org.marinemc.game.player.Player;
import org.marinemc.logging.Logging;
import org.marinemc.server.Marine;
import org.marinemc.settings.JSONConfig;
import org.marinemc.settings.ServerSettings;
import org.marinemc.util.StringUtils;

/**
 * UUID Handler - Undocumented!
 *
 * @author Citymonstret
 */
@SuppressWarnings({ "unused", "javadoc" })
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

		if (!file.contains("information")) {
			final org.json.JSONObject info = new org.json.JSONObject();
			info.put("mode", online);
			file.map.put("information", info);
		}

		final org.json.JSONObject info = file.get("information");

		boolean remove = false;
		if (info.getBoolean("mode") != online) {
			Logging.getLogger().info(
					"Clearing the UUID cache -> Server Modes not matching");
			remove = true;
			info.put("mode", online);
		}

		final Set keys = file.map.keySet();
		String name, uuid;
		double since;
		org.json.JSONObject object;
		final int hours = ServerSettings.getInstance().cacheHours;
		for (final Object o : keys) {
			if (o.toString().equals("information"))
				continue;
			if (remove) {
				file.map.remove(o.toString());
				continue;
			}
			try {
				uuid = o.toString();
				object = file.get(uuid);
				name = object.getString("name");
				since = hoursSince(object.getLong("time"));
				if (since < hours) {
					if (Bootstrap.debug())
						Logging.getLogger()
								.debug(StringUtils
										.format("Cache - UUID: {0}, Username: {1}, Time since {2}",
												uuid, name, since));
					add(name, UUID.fromString(uuid));
				} else if (Bootstrap.debug()) {
					Logging.getLogger()
							.debug(StringUtils
									.format("Cache - UUID {0} (username: {1}) is older than {2} hours, removing from cache",
											uuid, name, hours));
					file.map.remove(uuid);
				}
			} catch (final Exception e) {
				e.printStackTrace();
			}
		}
	}

	public static UUIDHandler instance() {
		if (instance == null)
			instance = new UUIDHandler();
		return instance;
	}

	public double hoursSince(final long time) {
		final long difference = System.currentTimeMillis() - time;
		return difference / 1000 / 3600;
	}

	public void save() {
		org.json.JSONObject o;
		for (final Map.Entry<String, String> entries : uuidMap.entrySet()) {
			o = new org.json.JSONObject();
			o.put("name", entries.getKey());
			o.put("time", System.currentTimeMillis());
			file.setIfNull(entries.getValue(), o);
		}
		file.saveFile();
	}

	public Map<String, String> getMap() {
		return uuidMap;
	}

	private void add(final String name, final UUID uuid) {
		if (!uuidMap.containsKey(name)
				&& !uuidMap.inverse().containsKey(uuid.toString()))
			uuidMap.put(name, uuid.toString());
	}

	public boolean uuidExists(final UUID uuid) {
		return uuidMap.inverse().containsKey(uuid.toString());
	}

	public boolean nameExist(final String name) {
		return uuidMap.containsKey(name);
	}

	public UUID getUUID(final String name) {
		if (nameExist(name))
			return UUID.fromString(uuidMap.get(name));
		final Player player = Marine.getPlayer(name);
		if (player != null) {
			final UUID uuid = player.getUUID();
			add(name, uuid);
			return uuid;
		}
		if (online)
			try {
				final UUID uuid = getMojangUUID(name);
				add(name, uuid);
				return uuid;
			} catch (final Throwable e) {
				e.printStackTrace();
			}
		else
			return getUuidOfflineMode(name);
		return null;
	}

	public String getName(final UUID uuid) {
		if (uuidExists(uuid))
			return uuidMap.inverse().get(uuid.toString());
		final Player player = Marine.getPlayer(uuid);
		if (player != null) {
			final String name = player.getUserName();
			add(name, uuid);
			return name;
		}
		if (online)
			try {
				final String name = getMojangName(uuid);
				add(name, uuid);
				return name;
			} catch (final Throwable e) {
				e.printStackTrace();
			}
		else
			return "unknown";
		return "";
	}

	/**
	 * @param name
	 *            to use as key
	 * @return UUID (name hash)
	 */
	public UUID getUuidOfflineMode(final String name) {
		final UUID uuid = UUID.nameUUIDFromBytes(("OfflinePlayer:" + name)
				.getBytes(Charsets.UTF_8));
		add(name, uuid);
		return uuid;
	}

	private String getMojangName(final UUID uuid) throws Throwable {
		final HttpURLConnection connection = (HttpURLConnection) new URL(
				"https://sessionserver.mojang.com/session/minecraft/profile/"
						+ uuid.toString().replace("-", "")).openConnection();
		final JSONObject response = (JSONObject) parser
				.parse(new InputStreamReader(connection.getInputStream()));
		if (response.get("cause") != null)
			throw new IllegalStateException(response.get("errorMessage")
					.toString());
		final String name = response.get("name").toString();
		connection.disconnect();
		return name;
	}

	private UUID getMojangUUID(final String name) throws Throwable {
		final HttpURLConnection connection = (HttpURLConnection) new URL(
				"https://api.mojang.com/profiles/minecraft").openConnection();
		connection.setRequestMethod("POST");
		connection.setRequestProperty("Content-Type", "application/json");
		connection.setUseCaches(false);
		connection.setDoInput(true);
		connection.setDoOutput(true);
		final String request = JSONArray.toJSONString(Arrays.asList(name));
		{
			final OutputStream stream = connection.getOutputStream();
			stream.write(request.getBytes());
			stream.flush();
			stream.close();
		}
		final JSONArray array = (JSONArray) parser.parse(new InputStreamReader(
				connection.getInputStream()));
		UUID uuid = null;
		for (final Object o : array)
			uuid = UUID.fromString(StringUtils.fixUUID(((JSONObject) o).get(
					"id").toString()));
		connection.disconnect();
		if (uuid == null)
			throw new NullPointerException("Couldn't fetch the uuid");
		return uuid;
	}
}