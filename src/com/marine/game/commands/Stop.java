package com.marine.game.commands;

import com.marine.game.command.Command;
import com.marine.game.command.CommandSender;
import com.marine.server.Marine;

/**
 * Created 2014-12-07 for MarineStandalone
 *
 * @author Citymonstret
 */
public class Stop extends Command {

    public Stop() {
        super("stop", new String[] {}, "Stop the server");
    }

    @Override
    public void execute(CommandSender sender, String[] arguments) {
        Marine.broadcastMessage("Stopping the server");
        Marine.stop();
    }
}
