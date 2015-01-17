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
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

/**
 * JSON Config Class
 * <p/>
 * Used for all internal and external configuration files used by
 * MarineStandalone and its plugins. Feel free to use this for all your plugin
 * needs.
 *
 * @author Citymonstret
 */
public class JSONConfig {

	public JSONObject map;
	public File file, path;
	public String name;

	public JSONConfig(final File file) {
		path = file.getParentFile();
		this.file = file;
		name = file.getName();
		map = new JSONObject();
	}

	public JSONConfig(final File path, final String name) throws JSONException {
		this.path = path;
		this.name = name;
		if (!path.exists())
			if (!path.mkdirs())
				throw new JSONConfigException(name,
						"Could not create parent folders");
		file = new File(path + File.separator + name + ".json");
		
		if(file.length() == 0) {
			map = new JSONObject();
			return;
		}
		if (!file.exists())
			try {
				if (!file.createNewFile())
					throw new JSONConfigException(name,
							"Could not create the config");
			} catch (final Exception e) {
				throw new JSONConfigException(name, "Could not be created", e);
			} finally {
				map = new JSONObject();
			}
		else
			try {
				final BufferedReader reader = new BufferedReader(
						new FileReader(file));
				final JSONTokener tokener = new JSONTokener(reader);
				map = new JSONObject(tokener);
				reader.close();
			} catch (final IOException e) {
				throw new JSONConfigException(name,
						"Could not load in the config", e);
			}
		for (final Map.Entry<String, Object> entry : defaultValues().entrySet())
			setIfNull(entry.getKey(), entry.getValue());
	}

	public Map<String, Object> defaultValues() {
		return new HashMap<>();
	}

	public void setIfNull(final String s, final Object o)
			throws JSONConfigException {
		if (map.isNull(s))
			try {
				map.put(s, o);
			} catch (final JSONException e) {
				throw new JSONConfigException(name, "Could not set the value",
						e);
			}
	}

	public void set(final String s, final Object o) throws JSONConfigException {
		if (!map.isNull(s))
			map.remove(s);
		try {
			map.put(s, o);
		} catch (final Exception e) {
			throw new JSONConfigException(name, "Could not set the value", e);
		}
	}

	public void saveFile() throws JSONConfigException {
		BufferedWriter writer;
		try {
			writer = new BufferedWriter(new FileWriter(file));
			map.write(writer);
			writer.close();
		} catch (final Exception e) {
			throw new JSONConfigException(name, "Could not be saved", e);
		}
	}

	public <T> T get(final String key) {
		return (T) map.get(key);
	}

	public <T> boolean contains(final T t) {
		return !map.isNull(t.toString());
	}
}
