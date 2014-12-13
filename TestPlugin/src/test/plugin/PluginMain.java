package test.plugin;

import com.google.common.eventbus.Subscribe;
import com.marine.MainComponent;
import com.marine.events.Listener;
import com.marine.events.standardevents.ListEvent;
import com.marine.game.CommandManager;
import com.marine.game.chat.ChatColor;
import com.marine.game.command.Command;
import com.marine.game.command.CommandSender;
import com.marine.net.handshake.ListResponse;
import com.marine.plugins.Plugin;
import com.marine.server.Marine;

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
        Marine.getServer().registerListener(new Listener() {
            @Subscribe
            public void onPing(ListEvent event) {
                event.setResponse(new ListResponse("Hello World", 666, 999, event.getResponse().SAMPLE_PLAYERS, event.getResponse().FAVICON));
            }
        });
        CommandManager.getInstance().registerCommand(new Command("bajs", new String[] {}, "Do some shitty work") {
            @Override
            public void execute(CommandSender sender, String[] arguments) {
                Marine.broadcastMessage(ChatColor.randomColor() + "Bajs");
            }
        });
        CommandManager.getInstance().registerCommand(new Command("potatis", new String[0], "") {
            @Override
            public void execute(CommandSender sender, String[] arguments) {
                Marine.broadcastMessage(ChatColor.DARK_AQUA + "Hello World" + replaceAll(new String[] { "@p is @a when @r is @e" }, sender)[0]);
            }
        });
        Marine.getScheduler().createSyncTask(new TickTack(this));
    }

}
