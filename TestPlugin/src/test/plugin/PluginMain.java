package test.plugin;

import org.marinemc.MainComponent;
import org.marinemc.events.EventListener;
import org.marinemc.events.standardevents.ListEvent;
import org.marinemc.game.chat.ChatColor;
import org.marinemc.game.command.Command;
import org.marinemc.game.command.CommandSender;
import org.marinemc.net.handshake.ListResponse;
import org.marinemc.plugins.Plugin;
import org.marinemc.server.Marine;

/**
 * Created 2014-12-10 for MarineStandalone
 *
 * @author Citymonstret
 */
public class PluginMain extends Plugin {

    @Override
    public void onEnable() {
        // Test the security manager
        {
            try {
                System.setSecurityManager(null);
                getLogger().log("I was able to nullify the security manager :(");
            } catch (Exception e) {
                getLogger().log("I was not able to nullify the security manager :)");
            }
            try {
                System.exit(0);
                getLogger().log("I was able to shut down the server... whoops");
            } catch (Exception e) {
                getLogger().log("I wasn't able to shut down the server, yay");
            }
            try {
                System.setErr(null);
                getLogger().log("I was able to set the error stream... whoops");
            } catch (Exception e) {
                getLogger().log("I wasn't able to set the error stream, yay");
            }
            try {
                MainComponent.getJavaVersion();
                new MainComponent().getJavaVersion();
                getLogger().log("I was able to do that");
            } catch (Exception e) {
                getLogger().log("I wasn't able to access that class statically nor normally");
            }
        }
        getLogger().log(getName() + " is enabled");
        registerListener(new EventListener<ListEvent>(this) {
            @Override
            public void listen(ListEvent event) {
                event.setResponse(new ListResponse("Hello World", 666, 999, event.getResponse().SAMPLE_PLAYERS, event.getResponse().FAVICON));
            }
        });
        addCommand(new Command("bajs", new String[]{}, "Do some shitty work") {
            @Override
            public void execute(CommandSender sender, String[] arguments) {
                Marine.broadcastMessage(ChatColor.randomColor() + "Bajs");
            }
        });
        addCommand(new Command("tellraw", new String[0], "") {
            @Override
            public void execute(CommandSender sender, String[] arguments) {
                Marine.broadcastMessage(ChatColor.DARK_AQUA + "Hello World" + replaceAll(new String[]{"@p is @a when @r is @e"}, sender)[0]);
            }
        });
        // Marine.getScheduler().createSyncTask(new TickTack(this));
    }

}
