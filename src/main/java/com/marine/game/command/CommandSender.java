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

package com.marine.game.command;

import com.marine.game.chat.ChatReciver;
import com.marine.util.Location;


/**
 * CommandSender = Player, Console, RCON & Command Blocks
 *
 * @author Citymonstret
 */
public interface CommandSender extends ChatReciver {

    /**
     * Execute a command without any arguments
     *
     * @param command Command to execute
     */
    public void executeCommand(String command);

    /**
     * Execute a command with arguments
     *
     * @param command   Command to execute
     * @param arguments Arguments
     */
    public void executeCommand(String command, String[] arguments);

    /**
     * Execute a command with arguments
     *
     * @param command Command to execute
     * @param arguments Arguments
     */
    public void executeCommand(Command command, String[] arguments);

    /**
     * Get the sender location
     *
     * @return current location
     */
    public Location getLocation();

    /**
     * Check if the sender has the given permission
     *
     * @param permission Permission
     * @return has permission?
     */
    public boolean hasPermission(String permission);
}
