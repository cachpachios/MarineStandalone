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

package com.marine.util.mojang;

import org.json.JSONObject;
import org.json.JSONTokener;

import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.UUID;

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
        ((HttpURLConnection) connection).disconnect();
        return UUID.fromString(uuid);
    }

    @Override
    public String mojangName(final UUID uuid) throws Exception {
        final URLConnection connection = new URL("http://intellectualsites.com/minecraft.php?user=" + uuid.toString().replace("-", "")).openConnection();
        connection.addRequestProperty("User-Agent", "Mozilla/4.0");
        final JSONTokener tokener = new JSONTokener(connection.getInputStream());
        final JSONObject root = new JSONObject(tokener);
        ((HttpURLConnection) connection).disconnect();
        return root.getJSONObject(uuid.toString().replace("-", "")).getString("username");
    }

}
