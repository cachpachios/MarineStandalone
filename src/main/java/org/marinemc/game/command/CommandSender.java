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

package org.marinemc.game.command;

import org.marinemc.game.chat.ChatMessage;
import org.marinemc.game.chat.ChatReciever;
import org.marinemc.game.chat.ChatSender;
import org.marinemc.game.permission.Permission;
import org.marinemc.util.Location;

/**
 * CommandSender = Player, Console, RCON & Command Blocks
 *
 * @author Citymonstret
 */
public interface CommandSender extends ChatReciever, ChatSender {

	@Override
	public void sendMessage(String message);

	@Override
	public void sendMessage(ChatMessage message);

	/**
	 * Execute a command without any arguments
	 *
	 * @param command
	 *            Command to execute
	 */
	public void executeCommand(String command);

	/**
	 * Execute a command with arguments
	 *
	 * @param command
	 *            Command to execute
	 * @param arguments
	 *            Arguments
	 */
	public void executeCommand(String command, String[] arguments);

	/**
	 * Execute a command with arguments
	 *
	 * @param command
	 *            Command to execute
	 * @param arguments
	 *            Arguments
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
	 * @param permission
	 *            Permission
	 * @return has permission?
	 */
	@Override
	public boolean hasPermission(String permission);

	/**
	 * Check if the sender has the given permission
	 *
	 * @param permission
	 *            Permission
	 * @return has permission?
	 */
	public boolean hasPermission(Permission permission);
}
