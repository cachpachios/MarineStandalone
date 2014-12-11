///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// MarineStandalone is a minecraft server software and API.
//     Copyright (C) IntellectualSites (marine.intellectualsites.com)
//
//     This program is free software; you can redistribute it and/or modify
//     it under the terms of the GNU General Public License as published by
//     the Free Software Foundation; either version 2 of the License, or
//     (at your option) any later version.
//
//     This program is distributed in the hope that it will be useful,
//     but WITHOUT ANY WARRANTY; without even the implied warranty of
//     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
//     GNU General Public License for more details.
//
//     You should have received a copy of the GNU General Public License along
//     with this program; if not, write to the Free Software Foundation, Inc.,
//     51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

package com.marine.settings;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Created 2014-12-03 for MarineStandalone
 *
 * @author Citymonstret
 */
public class JSONConfig {

    public JSONObject map;
    public File file, path;
    public String name;

    public JSONConfig(File file) {
        this.path = file.getParentFile();
        this.file = file;
        this.name = file.getName();
        this.map = new JSONObject();
    }

    public JSONConfig(File path, String name) throws JSONException {
        this.path = path;
        this.name = name;
        if (!path.exists()) {
            if (!path.mkdirs())
                throw new JSONConfigException(name, "Could not create parent folders");
        }
        this.file = new File(path + File.separator + name + ".json");
        if (!file.exists()) {
            try {
                if (!file.createNewFile())
                    throw new JSONConfigException(name, "Could not create the config");
            } catch (Exception e) {
                throw new JSONConfigException(name, "Could not be created", e);
            } finally {
                this.map = new JSONObject();
            }
        } else {
            try {
                BufferedReader reader = new BufferedReader(new FileReader(file));
                JSONTokener tokener = new JSONTokener(reader);
                this.map = new JSONObject(tokener);
                reader.close();
            } catch (IOException e) {
                throw new JSONConfigException(name, "Could not load in the config", e);
            }
        }
        for (Map.Entry<String, Object> entry : defaultValues().entrySet()) {
            setIfNull(entry.getKey(), entry.getValue());
        }
    }

    public Map<String, Object> defaultValues() {
        return new HashMap<>();
    }

    public void setIfNull(String s, Object o) throws JSONConfigException {
        if (map.isNull(s))
            try {
                map.put(s, o);
            } catch (JSONException e) {
                throw new JSONConfigException(name, "Could not set the value", e);
            }
    }

    public void set(String s, Object o) throws JSONConfigException {
        if (!map.isNull(s))
            map.remove(s);
        try {
            map.put(s, o);
        } catch (Exception e) {
            throw new JSONConfigException(name, "Could not set the value", e);
        }
    }

    public void saveFile() throws JSONConfigException {
        BufferedWriter writer;
        try {
            writer = new BufferedWriter(new FileWriter(file));
            map.write(writer);
            writer.close();
        } catch (Exception e) {
            throw new JSONConfigException(name, "Could not be saved", e);
        }
    }
}
