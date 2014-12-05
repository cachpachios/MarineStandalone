package com.marine.game.commands;

import com.marine.ServerProperties;
import com.marine.game.command.Command;
import com.marine.game.command.CommandSender;

/**
 * Created 2014-12-05 for MarineStandalone
 *
 * @author Citymonstret
 */
public class Info extends Command {

    public Info() {
        super("info", new String[] { "version", "i" }, "Display server info");
    }

    @Override
    public void execute(CommandSender sender, String[] arguments) {
        sender.sendMessage(
                String.format("Server Software: Marine Standalone - Version: %s | Protocol: %d | Minecraft: %s",
                        ServerProperties.BUILD_VERSION, ServerProperties.PROTOCOL_VERSION, ServerProperties.MINECRAFT_NAME)
        );
    }
}
