package com.marine.game.commands;

import com.marine.game.command.Command;
import com.marine.game.command.CommandSender;
import com.marine.plugins.Plugin;
import com.marine.server.Marine;
import com.marine.util.StringUtils;

import java.util.List;

/**
 * Created 2014-12-10 for MarineStandalone
 *
 * @author Citymonstret
 */
public class Plugins extends Command {


    public Plugins() {
        super("plugins", new String[] { "pl", "addons", "scripts" }, "Show a list of the plugins running on this server");
    }

    @Override
    public void execute(CommandSender sender, String[] arguments) {
        List<Plugin> plugins = Marine.getServer().getServer().getPluginLoader().getManager().getPlugins();
        String str = StringUtils.join(plugins, ", ");
        sender.sendMessage("Plugins: " + str);
    }
}
