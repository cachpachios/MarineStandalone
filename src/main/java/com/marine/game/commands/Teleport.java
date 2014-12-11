package com.marine.game.commands;

import com.marine.game.command.Command;
import com.marine.game.command.CommandSender;
import com.marine.player.Player;
import com.marine.util.Location;

/**
 * Created 2014-12-10 for MarineStandalone
 *
 * @author Citymonstret
 */
public class Teleport extends Command {

    public Teleport() {
        super("teleport", new String[]{"tp", "setloc"}, "Teleport a player");
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

    private Location getRelative(Location location, String x, String y, String z) {
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
