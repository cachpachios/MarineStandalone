package com.marine.game.commands;

import com.marine.game.TablistManager;
import com.marine.game.chat.ChatColor;
import com.marine.game.command.Command;
import com.marine.game.command.CommandSender;
import com.marine.game.inventory.CraftingInventory;
import com.marine.game.inventory.PlayerInventory;
import com.marine.net.play.clientbound.GameStateChangePacket;
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
            "inventory", "kick", "tab", "anvil", "crafting", "credits", "display_name"
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
                player.getClient().sendPacket(new KickPacket(ChatColor.randomColor() + "Kick Worked! xD"));
                break;
            case "credits":
                player.getClient().sendPacket(new GameStateChangePacket(GameStateChangePacket.Reason.DEMO_MESSAGES, 0f));
                break;
            case "crafting":
                player.openInventory(new CraftingInventory());
                break;
            case "display_name":
                if(arguments.length > 1) {
                    player.setDisplayName(arguments[1]);
                    TablistManager.getInstance().setDisplayName(player);
                } else {
                    player.setDisplayName(player.getName());
                    TablistManager.getInstance().setDisplayName(player);
                }
                break;
            case "tab":
                if(arguments.length < 3) {
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
