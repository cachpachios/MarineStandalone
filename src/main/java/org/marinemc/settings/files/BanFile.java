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

package org.marinemc.settings.files;

import org.json.JSONArray;
import org.marinemc.game.player.Player;
import org.marinemc.settings.StorageConfig;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;

/**
 * Created 2014-12-27 for MarineStandalone
 *
 * @author Citymonstret
 */
public class BanFile extends StorageConfig {

    public BanFile(File file) {
        super(file, "banned");
    }

    public boolean contains(Player player) {
        return contains(player.getUUID());
    }

    public Collection<UUID> getBannedUUIDs() {
        JSONArray array = get("players");
        Collection<UUID> c = new ArrayList<>();
        Object o;
        for (int x = 0; x < array.length(); x++) {
            if ((o = array.get(x)) instanceof String) {
                c.add(UUID.fromString(o.toString()));
            }
        }
        return c;
    }
}
