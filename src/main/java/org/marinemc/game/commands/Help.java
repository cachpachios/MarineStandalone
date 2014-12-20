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
import org.marinemc.game.command.Command;
import org.marinemc.game.command.CommandSender;
import org.marinemc.util.StringUtils;

import java.util.ArrayList;

/**
 * Created 2014-12-05 for MarineStandalone
 *
 * @author Citymonstret
 */
public class Help extends Command {

    final int PER_PAGE = 5;

    public Help() {
        super("help", "marine.help", "Display this help list", "h");
    }

    @Override
    public void execute(CommandSender sender, String[] arguments) {
        final java.util.List<Command> commands = new ArrayList<>(CommandManager.getInstance().getCommands());
        int pages = (int) Math.ceil(commands.size() / PER_PAGE);
        int page;
        if (arguments.length < 1) {
            page = 0;
        } else {
            try {
                page = Integer.parseInt(arguments[0]) - 1;
            } catch (Exception e) {
                page = 0;
            }
        }
        if (page < 0) {
            page = 0;
        }
        if (page > pages) {
            page = pages;
        }
        int start = PER_PAGE * page;
        int max = start + PER_PAGE;
        if (max > commands.size())
            max = commands.size();
        StringBuilder message = new StringBuilder();
        message
                .append("§9§m--------------------[§r §f§lHelp §9§m]-------------------")
                .append("\n")
                .append("§9Page: §f")
                .append(page + 1)
                .append("§9/§f")
                .append(pages + 1)
                .append("§9 Displaying: §f")
                .append(max)
                .append("§9/§f")
                .append(commands.size());
        Command command;
        for (int i = start; i < max; i++) {
            command = commands.get(i);
            message
                    .append("\n§f/")
                    .append(command.toString())
                    .append(" [")
                    .append(StringUtils.join(command.getAliases(), ", "))
                    .append("]")
                    .append("\n§9  ")
                    .append(command.getDescription());
        }
        sender.sendMessage(message.toString());
    }
}