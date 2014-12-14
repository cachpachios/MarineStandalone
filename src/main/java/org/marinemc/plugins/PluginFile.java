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

package org.marinemc.plugins;


import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.InputStream;

/**
 * The plugin description file
 *
 * @author Citymonstret
 */
public class PluginFile {

    public final String name;
    public final String mainClass;
    public final String author;
    public final String version;

    /**
     * Constructor
     *
     * @param stream Stream with desc.json incoming
     * @throws Exception If anything bad happens
     */
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
