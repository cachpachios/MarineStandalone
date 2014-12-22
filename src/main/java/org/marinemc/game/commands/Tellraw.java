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

import java.util.Arrays;
import java.util.Collection;

import org.json.JSONObject;
import org.json.JSONTokener;
import org.marinemc.game.chat.ChatColor;
import org.marinemc.game.command.Command;
import org.marinemc.game.command.CommandSender;
import org.marinemc.game.player.Player;
import org.marinemc.server.Marine;

/**
 * Created 2014-12-14 for MarineStandalone
 *
 * @author Citymonstret
 */
public class Tellraw extends Command {

    public Tellraw() {
        super("tellraw", "marine.tellraw", "Send a pure JSON Message", "tr");
    }

    public void execute(CommandSender sender, String... args) {
        if (args.length < 2)
            sender.sendMessage("/tellraw [specifier/username] {message}");
        else {
            String sp = args[0];
            Collection<Player> r;
            if (sp.equals("@p") || sp.equals("@a") || sp.equals("@r")) {
                r = getPlayers(sender, sp);
            } else {
                Player p;
                if ((p = Marine.getPlayer(sp)) == null) {
                    sender.sendMessage("No player with that name was found");
                    return;
                }
                r = Arrays.asList(p);
            }
            if (r == null) {
                sender.sendMessage("No players found");
            } else {
                StringBuilder builder = new StringBuilder();
                args = replaceAll(args, sender);
                for (int i = 1; i < args.length; i++)
                    builder.append(args[i]).append(" ");
                String msg = ChatColor.transform('&', builder.toString());
                if (msg.endsWith(" "))
                    msg = msg.substring(0, msg.length() - 1);
                if (!msg.startsWith("{") || !msg.endsWith("}"))
                    msg = "{\"text\":\"" + msg + "\"}";
                JSONObject o;
                try {
                    JSONTokener tokener = new JSONTokener(msg);
                    o = new JSONObject(tokener);
                } catch (Exception e) {
                    o = null;
                }
                if (o == null) {
                    sender.sendMessage("Invalid JSON");
                } else {
                    for (final Player p : r)
                        p.sendMessageRaw(msg);
                }
            }
        }
    }
}
