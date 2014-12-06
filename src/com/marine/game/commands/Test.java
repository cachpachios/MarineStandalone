package com.marine.game.commands;

import com.marine.game.chat.ChatColor;
import com.marine.game.command.Command;
import com.marine.game.command.CommandSender;
import com.marine.game.inventory.PlayerInventory;
import com.marine.net.play.clientbound.KickPacket;
import com.marine.player.Player;
import com.sun.deploy.util.StringUtils;

import java.util.Arrays;

/**
 * Created 2014-12-06 for MarineStandalone
 *
 * @author Citymonstret
 */
public class Test extends Command {

    final String[] acceptableArguments = new String[] {
            "inventory", "kick"
    };

    public Test() {
        super("test", new String[] { "debug" }, "Debug");
    }


    @Override
    public void execute(CommandSender sender, String[] arguments) {
        String test;
        if(!(sender instanceof Player)) {
            sender.sendMessage("§cThis command can only be used by players");
            return;
        }
        if(arguments == null || arguments.length < 1 || !Arrays.asList(acceptableArguments).contains(arguments[0])) {
            sender.sendMessage("§c[§6*§c] §6Unknown test type, acceptable types§c: "
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
                player.getClient().sendPacket(new KickPacket(ChatColor.randomColor() + "Kick Worked! xD"));
                break;
            default:
                break;
        }
    }
}
