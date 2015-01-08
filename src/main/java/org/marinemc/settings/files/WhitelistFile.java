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

import java.io.File;
import java.util.UUID;

import org.json.JSONArray;
import org.marinemc.settings.StorageConfig;

/**
 * Created 2014-12-27 for MarineStandalone
 *
 * @author Citymonstret
 */
public class WhitelistFile extends StorageConfig {

	private final JSONArray array;

	public WhitelistFile(final File file) {
		super(file, "whitelist");
		setIfNull("players", new JSONArray());
		array = get("players");
	}

	@Override
	public void saveFile() {
		set("players", array);
		super.saveFile();
	}

	public void setWhitelisted(final UUID uuid, final boolean b) {
		for (int x = 0; x < array.length(); x++)
			if (array.getString(x).equals(uuid.toString())) {
				if (!b)
					array.remove(x);
				return;
			}
		if (b)
			array.put(uuid.toString());
		else
			throw new UnsupportedOperationException(
					"You cannot remove someone from the whitelist, unless they're added");
	}

	public boolean isWhitelisted(final UUID uuid) {
		for (int x = 0; x < array.length(); x++)
			if (array.getString(x).equals(uuid.toString()))
				return true;
		return false;
	}
}
