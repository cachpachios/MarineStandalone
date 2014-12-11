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

package com.marine.plugins;


import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.InputStream;

/**
 * Created 2014-12-10 for MarineStandalone
 *
 * @author Citymonstret
 */
public class PluginFile {

    public final String name;
    public final String mainClass;
    public final String author;
    public final String version;

    public PluginFile(InputStream stream) throws Exception {
        final JSONTokener tokener = new JSONTokener(stream);
        final JSONObject object = new JSONObject(tokener);

        this.name = object.getString("name");
        this.mainClass = object.getString("main");
        this.author = object.getString("author");
        this.version = object.getString("version");

        stream.close();
    }

}
