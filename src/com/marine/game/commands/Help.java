package com.marine.game.commands;

import com.marine.game.CommandManager;
import com.marine.game.chat.ChatColor;
import com.marine.game.command.Command;
import com.marine.game.command.CommandSender;

import java.util.List;

/**
 * Created 2014-12-05 for MarineStandalone
 *
 * @author Citymonstret
 */
public class Help extends Command {

    public Help() {
        super("help", new String[] {"h"}, "Display this help list");
    }

    @Override
    public void execute(CommandSender sender, String[] arguments) {
        List<Command> commands = CommandManager.getInstance().getCommands();
        StringBuilder message = new StringBuilder();
        message.append(ChatColor.BLUE).append("§lCommands:");
        for(Command command : commands) {
            message.append("\n /").append(command.toString()).append(" §l-§r ").append(command.getDescription());
        }
        sender.sendMessage(message.toString());
    }
}
