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

package com.marine.player;

import com.marine.settings.JSONConfig;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * Created 2014-12-07 for MarineStandalone
 *
 * @author Citymonstret
 */
public class PlayerFile extends JSONConfig {

    private final Player player;

    public PlayerFile(Player player) throws Exception {
        super(new File("./players"), player.getUUID().toString());
        this.player = player;
        if (!map.getString("name").equalsIgnoreCase(player.getName()))
            set("name", player.getName());
    }

    @Override
    public Map<String, Object> defaultValues() {
        Map<String, Object> d = new HashMap<>();
        d.put("exp", 0);
        d.put("levels", 0);
        d.put("name", "");
        return d;
    }
}
