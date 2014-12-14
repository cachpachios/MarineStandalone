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

import org.marinemc.game.TablistManager;
import org.marinemc.game.chat.ChatColor;
import org.marinemc.game.command.Command;
import org.marinemc.game.command.CommandSender;
import org.marinemc.game.inventory.CraftingInventory;
import org.marinemc.game.inventory.PlayerInventory;
import org.marinemc.net.play.clientbound.GameStateChangePacket;
import org.marinemc.player.Player;
import org.marinemc.util.StringUtils;

import java.util.Arrays;

/**
 * Created 2014-12-06 for MarineStandalone
 *
 * @author Citymonstret
 */
public class Test extends Command {

    final String[] acceptableArguments = new String[]{
            "inventory", "kick", "tab", "anvil", "crafting", "credits", "display_name", "exp"
    };

    public Test() {
        super("test", new String[]{"debug"}, "Debug");
    }


    @Override
    public void execute(CommandSender sender, String[] arguments) {
        String test;
        if (!(sender instanceof Player)) {
            sender.sendMessage("§cThis command can only be used by players");
            return;
        }
        if (arguments == null || arguments.length < 1 || !Arrays.asList(acceptableArguments).contains(arguments[0])) {
            sender.sendMessage("§c[§6*§c] §6Unknown test type, acceptable types§c: §6"
                    + StringUtils.join(Arrays.asList(acceptableArguments), "§c, §6"));
            return;
        }
        Player player = (Player) sender;
        test = arguments[0];
        switch (test) {
            case "inventory":
                player.openInventory(new PlayerInventory(player.nextWindowID()));
                break;
            case "kick":
                // player.getClient().sendPacket(new KickPacket(ChatColor.randomColor() + "Kick Worked! xD"));
                if (arguments.length > 1) {
                    String[] newArray = new String[arguments.length - 1];
                    System.arraycopy(arguments, 1, newArray, 0, newArray.length);
                    player.kick(ChatColor.transform('&', StringUtils.join(newArray, " ")));
                } else {
                    player.kick(ChatColor.randomColor() + "Kicked");
                }
                break;
            case "credits":
                player.getClient().sendPacket(new GameStateChangePacket(GameStateChangePacket.Reason.DEMO_MESSAGES, 0f));
                break;
            case "crafting":
                player.openInventory(new CraftingInventory());
                break;
            case "display_name":
                if (arguments.length > 1) {
                    player.setDisplayName(arguments[1]);
                    TablistManager.getInstance().setDisplayName(player);
                } else {
                    player.setDisplayName(player.getName());
                    TablistManager.getInstance().setDisplayName(player);
                }
                break;
            case "exp":
                player.setExp((float) Math.random());
                player.setLevels((int) (Math.ceil(Math.random() * 255)));
                break;
            case "tab":
                if (arguments.length < 3) {
                    player.sendMessage("You need to specify two strings (_ instead of spaces)");
                } else {
                    String header = arguments[1].replace('_', ' ');
                    String footer = arguments[2].replace('_', ' ');
                    TablistManager.getInstance().setHeaderAndFooter(header, footer, player);
                }
                break;
            default:
                break;
        }
    }
}
