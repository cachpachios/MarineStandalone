package com.marine.game.commands;

import com.marine.game.chat.ChatColor;
import com.marine.game.command.Command;
import com.marine.game.command.CommandSender;
import com.marine.player.Player;
import com.marine.util.StringUtils;

import java.util.Arrays;

/**
 * Created 2014-12-10 for MarineStandalone
 *
 * @author Citymonstret
 */
public class SendAboveActionBarMessage extends Command {

    public SendAboveActionBarMessage() {
        super("sendaboveactionbarmessage", new String[] {}, "Send above action bar message");
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if(!(sender instanceof Player))
            return;
        Player player = (Player) sender;
        args = replaceAll(args, sender);
        player.sendAboveActionbarMessage(ChatColor.transform('&', StringUtils.join(Arrays.asList(args), " ")));
    }
}
