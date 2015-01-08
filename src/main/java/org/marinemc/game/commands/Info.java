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

package org.marinemc.game.commands;

import org.marinemc.game.chat.ChatColor;
import org.marinemc.game.chat.ChatMessage;
import org.marinemc.game.chat.builder.ClickEvent;
import org.marinemc.game.chat.builder.HoverEvent;
import org.marinemc.game.chat.builder.Part;
import org.marinemc.game.command.Command;
import org.marinemc.game.command.CommandSender;
import org.marinemc.game.player.Player;
import org.marinemc.server.ServerProperties;
import org.marinemc.util.StringUtils;

/**
 * Created 2014-12-05 for MarineStandalone
 *
 * @author Citymonstret
 */
public class Info extends Command {

	public Info() {
		super("info", "marine.info", "Display server info", "version", "i");
	}

	@Override
	public void execute(final CommandSender sender, final String[] arguments) {
		if (!(sender instanceof Player))
			sender.sendMessage(StringUtils
					.format("Server Info - Software: {0}, Version: {1}, Protocol: {2}, Minecraft: {3}",
							"MarineStandalone", ServerProperties.BUILD_VERSION,
							ServerProperties.PROTOCOL_VERSION,
							ServerProperties.MINECRAFT_NAME));
		else
			((Player) sender)
					.sendMessage(new ChatMessage("Server Info\n")
							.color(ChatColor.RED)
							.format(ChatColor.BOLD)
							.with(new Part("  Software: ", ChatColor.RED))
							.with(new Part("MarineStandalone\n",
									ChatColor.WHITE)
									.event(new HoverEvent(
											HoverEvent.Action.SHOW_TEXT,
											"§chttps://marinemc.org §f- Click for URL"))
									.event(new ClickEvent(
											ClickEvent.Action.OPEN_URL,
											"https://marinemc.org")))
							.with(new Part("  Version: ", ChatColor.RED))
							.with(new Part(ServerProperties.BUILD_VERSION,
									ChatColor.WHITE))
							.with(new Part("\n  Protocol: ", ChatColor.RED))
							.with(new Part(ServerProperties.PROTOCOL_VERSION
									+ "", ChatColor.WHITE))
							.with(new Part("\n  Minecraft: ", ChatColor.RED))
							.with(new Part(ServerProperties.MINECRAFT_NAME,
									ChatColor.WHITE)));
	}
}
