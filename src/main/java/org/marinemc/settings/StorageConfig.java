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

import org.json.JSONException;

import java.io.File;

/**
 * Created 2014-12-27 for MarineStandalone
 *
 * @author Citymonstret
 */
public class StorageConfig extends JSONConfig {

    protected StorageConfig(File file) {
        super(file);
    }

    protected StorageConfig(File parent, String file) throws JSONException {
        super(parent, file);
    }

       /*@Override
        public Map<String, Object> defaultValues() {
            Map<String, Object> defaultUUIDS = new HashMap<>();
            try {
                defaultUUIDS.put("uuid", new JSONArray(new String[]{UUIDHandler.getUUID("notch").toString()}));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return defaultUUIDS;
        }*/

}