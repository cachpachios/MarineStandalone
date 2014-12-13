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

package com.marine.game.commands;

import com.marine.game.command.Command;
import com.marine.game.command.CommandSender;
import com.marine.server.Marine;
import com.marine.util.StringUtils;

/**
 * Created 2014-12-10 for MarineStandalone
 *
 * @author Citymonstret
 */
public class Plugins extends Command {


    public Plugins() {
        super("plugins", new String[]{"pl", "addons", "scripts"}, "Show a list of the plugins running on this server");
    }

    @Override
    public void execute(CommandSender sender, String[] arguments) {
        sender.sendMessage("Plugins: " + StringUtils.join(Marine.getServer().getServer().getPluginLoader().getManager().getPlugins(), ", "));
    }
}
