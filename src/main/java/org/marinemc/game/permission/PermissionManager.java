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

package org.marinemc.game.permission;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import org.marinemc.game.player.Player;
import org.marinemc.server.Marine;
import org.marinemc.settings.JSONConfig;

/**
 * Created 2014-12-24 for MarineStandalone
 *
 * @author Citymonstret
 */
public class PermissionManager {

	private static PermissionManager instance;
	private final Map<String, Permission> permissionMap;
	private final Map<String, Group> groupMap;
	private final Map<String, String> playerGroupMap;

	public PermissionManager() {
		permissionMap = new ConcurrentHashMap<>();
		groupMap = new ConcurrentHashMap<>();
		playerGroupMap = new ConcurrentHashMap<>();
	}

	public static PermissionManager instance() {
		if (instance == null)
			instance = new PermissionManager();
		return instance;
	}

	public Permission getPerm(final String name) {
		return getPerm(name, false);
	}

	public void load() {
		final JSONConfig config = Marine.getServer().getJsonFileHandler().groups;
		config.setIfNull("groups", new JSONArray());
		final JSONArray array = Marine.getServer().getJsonFileHandler().groups
				.get("groups");
		JSONObject object;
		for (int x = 0; x < array.length(); x++) {
			object = array.getJSONObject(x);
			playerGroupMap.put(object.getString("uuid"),
					object.getString("group"));
		}
	}

	public Group getGroup(final UUID uuid) {
		Group group;
		try {
			group = getGroup(playerGroupMap.get(uuid.toString()));
		} catch (final NullPointerException e) {
			group = null;
		}
		return group == null ? Groups.ADMIN : group;
	}

	public Permission getPerm(final String name, final boolean create) {
		if (!permissionMap.containsKey(name))
			if (create)
				permissionMap.put(name, new Permission(name));
			else
				throw new NullPointerException("There is no such permission");
		return permissionMap.get(name);
	}

	public void addPermission(final Permission permission) {
		permissionMap.put(permission.toString(), permission);
	}

	public Group getGroup(final String name) {
		return groupMap.get(name);
	}

	public void addGroup(final Group group) {
		groupMap.put(group.toString(), group);
	}

	public boolean hasPermission(final Group group, final Permission permission) {
		return group.getPermissions().contains(permission);
	}

	public boolean hasPermission(final Group group, final String permission) {
		return hasPermission(group, getPerm(permission));
	}

	public boolean hasPermission(final Player player, final String permission) {
		return hasPermission(player, getPerm(permission));
	}

	public boolean hasPermission(final Player player,
			final Permission permission) {
		return player.getPermissions().contains(permission)
				|| hasPermission(player.getGroup(), permission)
				|| player.getPermissions()
						.contains(Permissions.ALL_PERMISSIONS)
				|| hasPermission(player.getGroup(), Permissions.ALL_PERMISSIONS);
	}
}
