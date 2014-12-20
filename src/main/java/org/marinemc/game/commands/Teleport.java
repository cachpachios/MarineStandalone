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

import org.marinemc.game.command.Command;
import org.marinemc.game.command.CommandSender;
import org.marinemc.player.Player;
import org.marinemc.util.Location;

/**
 * Created 2014-12-10 for MarineStandalone
 *
 * @author Citymonstret
 */
public class Teleport extends Command {

    public Teleport() {
        super("teleport", "marine.teleport", "Teleport a player", "tp");
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (args.length < 3) {
            sender.sendMessage("You've got to specify: X, Y, Z");
            return;
        }
        Player player = (Player) sender;
        double x = 0, y = 0, z = 0;
        try {
            player.teleport(getRelative(player.getLocation(), args[0], args[1], args[2]));
        } catch (Exception e) {
            sender.sendMessage("Invalid numbers");
        }
        player.teleport(new Location(player.getWorld(), x, y, z));
    }

    private Location getRelative(Location location, String x, String y, String z) throws NumberFormatException {
        // ~, ~-
        if (x.startsWith("~-")) {
            location.setX(location.getX() - Double.parseDouble(x.substring(2)));
        } else if (x.startsWith("~")) {
            location.setX(location.getX() + Double.parseDouble(x.substring(1)));
        } else {
            location.setX(Double.parseDouble(x));
        }
        if (y.startsWith("~-")) {
            location.setY(location.getY() - Double.parseDouble(y.substring(2)));
        } else if (y.startsWith("~")) {
            location.setY(location.getY() + Double.parseDouble(y.substring(1)));
        } else {
            location.setY(Double.parseDouble(y));
        }
        if (z.startsWith("~-")) {
            location.setX(location.getZ() - Double.parseDouble(z.substring(2)));
        } else if (z.startsWith("~")) {
            location.setZ(location.getZ() + Double.parseDouble(z.substring(1)));
        } else {
            location.setZ(Double.parseDouble(z));
        }
        return location;
    }
}
