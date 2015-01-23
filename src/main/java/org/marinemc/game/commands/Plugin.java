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

import org.marinemc.events.EventManager;
import org.marinemc.game.command.Command;
import org.marinemc.game.command.CommandSender;
import org.marinemc.server.Marine;
import org.marinemc.util.StringUtils;

import java.util.Collection;

/**
 * Created 2015-01-23 for MarineStandalone
 *
 * @author Citymonstret
 */
public class Plugin extends Command {

    /**
     * Constructor
     */
    public Plugin() {
        super("plugin", "marine.plugin", "Show information about a plugin");
    }

    @Override
    public void execute(CommandSender sender, String[] arguments) {
        if (arguments.length < 1) {
            sender.sendMessage("§cYou have to specify a plugin");
        } else {
            String pl = arguments[0].toLowerCase();
            org.marinemc.plugins.Plugin p = null;
            Collection<org.marinemc.plugins.Plugin> pls = Marine.getServer().getPluginLoader().getManager().getEnabledPlugins();
            for (org.marinemc.plugins.Plugin i : pls) {
                if (i.getName().equalsIgnoreCase(pl)) {
                    p = i;
                    break;
                }
            }
            if (p == null) {
                sender.sendMessage("§cThere is no plugin by the name " + pl);
            } else {
                /*
                Name: MarineMC
                Version: 1.0
                Author: Citymonstret
                Listeners: [],[],[]
                Commands: []
                 */
                for (String s : new String[]{
                        "§6Name§c: §6" + p.getName(),
                        "§6Version§c: §6" + p.getVersion(),
                        "§6Author:§c §6" + p.getAuthor(),
                        "§6Listeners: §6" + StringUtils.join(EventManager.getInstance().getAll(p), "§c,§6 ")
                })
                    sender.sendMessage(s);
            }
        }
    }
}
