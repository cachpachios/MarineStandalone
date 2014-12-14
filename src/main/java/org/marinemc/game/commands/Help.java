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

import org.marinemc.game.CommandManager;
import org.marinemc.game.chat.ChatColor;
import org.marinemc.game.command.Command;
import org.marinemc.game.command.CommandSender;

import java.util.Collection;

/**
 * Created 2014-12-05 for MarineStandalone
 *
 * @author Citymonstret
 */
public class Help extends Command {

    public Help() {
        super("help", new String[]{"h"}, "Display this help list");
    }

    @Override
    public void execute(CommandSender sender, String[] arguments) {
        Collection<Command> commands = CommandManager.getInstance().getCommands();
        StringBuilder message = new StringBuilder();
        message.append(ChatColor.BLUE).append("§lCommands:");
        for (Command command : commands) {
            message.append("\n /").append(command.toString()).append(" §l-§r ").append(command.getDescription());
        }
        sender.sendMessage(message.toString());
    }
}
