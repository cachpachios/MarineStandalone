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

import java.io.File;

import org.marinemc.settings.files.BanFile;
import org.marinemc.settings.files.PermissionFile;
import org.marinemc.settings.files.WhitelistFile;

/**
 * A File handler for the internal JSON Files
 *
 * @author Citymonstret
 */
public class JSONFileHandler {

	public final JSONConfig groups;
	public final WhitelistFile whitelist;
	public final BanFile banned;
	private final File settingsPath, storagePath;

	public JSONFileHandler(final File settingsPath, final File storagePath)
			throws JSONException {
		this.settingsPath = settingsPath;
		this.storagePath = storagePath;
		banned = new BanFile(storagePath);
		whitelist = new WhitelistFile(storagePath);
		groups = new PermissionFile(storagePath);
	}

	public void saveAll() {
		groups.saveFile();
		banned.saveFile();
		whitelist.saveFile();
	}

	public JSONObject getAdministratorJSONObject() {
		return groups.map;
	}

	public JSONObject getBannedJSONObject() {
		return banned.map;
	}

	public JSONObject getWhitelistJSONObject() {
		return whitelist.map;
	}

	public File getSettingsPath() {
		return settingsPath;
	}

	public File getStoragePath() {
		return storagePath;
	}

}
