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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

/**
 * Permission Group class
 *
 * @author Citymonstret
 */
public class Group {

    private String name;
    private Collection<Permission> permissions;
    private String prefix;

    public Group(final String name, final String prefix, final Permission... permissions) {
        this.name = name;
        this.prefix = prefix;
        this.permissions = new ArrayList<>(Arrays.asList(permissions));
    }

    @Override
    public String toString() {
        return this.name;
    }

    public Collection<Permission> getPermissions() {
        return this.permissions;
    }

    public String getPrefix() {
        return this.prefix;
    }

    public boolean has(final Permission permission) {
        return permissions.contains(permission);
    }

    public void add(final Permission permission) {
        this.permissions.add(permission);
    }

    public void remove(final Permission permission) {
        this.permissions.remove(permission);
    }
}
