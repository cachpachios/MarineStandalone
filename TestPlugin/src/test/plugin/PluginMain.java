package test.plugin;

import org.marinemc.events.EventListener;
import org.marinemc.events.standardevents.ListEvent;
import org.marinemc.game.chat.ChatColor;
import org.marinemc.game.command.Command;
import org.marinemc.game.command.CommandSender;
import org.marinemc.net.handshake.ListResponse;
import org.marinemc.plugins.Plugin;
import org.marinemc.server.Marine;
import org.marinemc.settings.JSONConfig;

/**
 * Plugin Test
 *
 * @author Citymonstret
 */
public class PluginMain extends Plugin {

    JSONConfig config;

    @Override
    public void onEnable() {
        config = new JSONConfig(getDataFolder(), "config");
        registerListeners();
        registerCommand();
    }


    void registerListeners() {
        registerListener(new EventListener<ListEvent>() {
            // We actually want this to be a separate class
            @Override
            public void listen(ListEvent event) {
                event.setResponse(new ListResponse("Hello World", 666, 999, event.getResponse().SAMPLE_PLAYERS, event.getResponse().FAVICON));
            }
        });
    }

    void registerCommand() {
        addCommand(new Command("bajs", new String[]{}, "Do some shitty work") {
            @Override
            public void execute(CommandSender sender, String[] arguments) {
                Marine.broadcastMessage(ChatColor.randomColor() + "Bajs");
            }
        });
        // A stupid override test
        addCommand(new Command("tellraw", new String[0], "") {
            @Override
            public void execute(CommandSender sender, String[] arguments) {
                Marine.broadcastMessage(ChatColor.DARK_AQUA + "Hello World" + replaceAll(new String[]{"@p is @a when @r is @e"}, sender)[0]);
            }
        });
    }
}
