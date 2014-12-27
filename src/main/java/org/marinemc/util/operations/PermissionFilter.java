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

package org.marinemc.util.operations;

import org.marinemc.game.permission.Permission;
import org.marinemc.game.player.Player;

/**
 * Created 2014-12-27 for MarineStandalone
 *
 * @author Citymonstret
 */
public class PermissionFilter extends FilteredOperation<Player> {

    private Permission permission;
    private ArgumentOperation<Player> operation;

    public PermissionFilter(final Permission permission, final ArgumentOperation<Player> operation) {
        this.permission = permission;
        this.operation = operation;
    }

    public PermissionFilter(final Permission permission) {
        this(permission, null);
    }

    @Override
    public void perform(Player player) {
        if (operation != null) {
            operation.accept(player);
        }
    }

    @Override
    public boolean filter(Player player) {
        return player.hasPermission(permission);
    }
}
