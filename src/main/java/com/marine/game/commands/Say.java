package com.marine.game.commands;

import com.marine.game.chat.ChatColor;
import com.marine.game.command.Command;
import com.marine.game.command.CommandSender;
import com.marine.server.Marine;
import com.marine.util.StringUtils;

import java.util.Arrays;

/**
 * Created 2014-12-06 for MarineStandalone
 *
 * @author Citymonstret
 */
public class Say extends Command {

    public Say() {
        super("say", new String[]{}, "Say something");
    }

    @Override
    public void execute(CommandSender sender, String[] arguments) {
        arguments = replaceAll(arguments, sender);
        Marine.broadcastMessage(ChatColor.transform('&', (StringUtils.join(Arrays.asList(arguments), " "))));
    }
}
