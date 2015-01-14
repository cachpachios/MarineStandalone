package test.plugin;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.marinemc.events.EventListener;
import org.marinemc.events.EventManager;
import org.marinemc.events.EventPriority;
import org.marinemc.events.standardevents.JoinEvent;
import org.marinemc.events.standardevents.ListEvent;
import org.marinemc.events.standardevents.PreLoginEvent;
import org.marinemc.game.chat.ChatColor;
import org.marinemc.game.scheduler.MarineRunnable;
import org.marinemc.net.handshake.ListResponse;
import org.marinemc.player.IPlayer;
import org.marinemc.player.Player;
import org.marinemc.plugins.Plugin;
import org.marinemc.server.Marine;
import org.marinemc.util.Base64Image;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.net.URL;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * Plugin Test
 *
 * @author Citymonstret
 */
public class PluginMain extends Plugin {

    // epic custom whitelist
    private final String[] blackList = new String[] {
            "", "Citymonstret", "notch", "Dinnerbone", "xisuma"
    };

    private final String[] adverts = new String[] {
            "Google is our friend",
            "Buy more pizzas",
            "Kittens are cute",
            "Potatoes taste good"
    };

    private Map<String, String> map = new HashMap<>();

    @Override
    public void onEnable() {
        EventManager.getInstance().addListener(new EventListener<PreLoginEvent>(EventPriority.HIGH) {
            @Override
            public void listen(final PreLoginEvent event) {
                final IPlayer player = event.getPlayer();
                if (!Arrays.asList(blackList).contains(player.getName())) {
                    event.setAllowed(false);
                    event.setMessage(
                            ChatColor.red + ChatColor.bold + "Closed Beta!\n" +
                            ChatColor.yellow + "Apply at: " + ChatColor.blue + "https://google.com"
                    );
                }
            }
        });
        EventManager.getInstance().addListener(new EventListener<JoinEvent>(EventPriority.HIGH) {
            @Override
            public void listen(final JoinEvent e) {
                map.put(e.getPlayer().getClient().getAdress().toString(), e.getPlayer().getName());
            }
        });
        EventManager.getInstance().addListener(new EventListener<PreLoginEvent>(EventPriority.LOW) {
            @Override
            public void listen(final PreLoginEvent event) {
                final IPlayer player = event.getPlayer();
                if (!Arrays.asList(blackList).contains(player.getName())) {
                    event.setAllowed(false);
                    event.setMessage(
                            ChatColor.red + ChatColor.bold + "Potato Closed Beta!\n" +
                                    ChatColor.yellow + "Apply at: " + ChatColor.blue + "https://google.com"
                    );
                }
            }
        });
        EventManager.getInstance().addListener(new EventListener<ListEvent>(EventPriority.HIGH) {
            @Override
            public void listen(final ListEvent event) {
                final ListResponse response = event.getResponse();
                response.setMotd(
                        ChatColor.BLUE + "We are currently in " + ChatColor.BOLD + "Beta!\n"
                                + ChatColor.YELLOW + "Apply at " + ChatColor.GOLD + "https://google.com");
                final JSONArray samples = new JSONArray();
                samples.add(getText("&9&lGamemodes: "));
                samples.add(getText("&6"));
                samples.add(getText("&cNONE"));
                response.setSamplePlayers(samples);
                if (map.containsKey(event.getClient().getAdress().toString())) {
                    String name = map.get(event.getClient().getAdress().toString());
                    try {
                        File file = new File(System.getProperty("java.io.tmpdir") + File.separator + "name" + ".png");
                        if (!file.exists()) {
                            URL url = new URL("http://cravatar.eu/helmavatar/" + name + "/20.png");
                            BufferedImage image = ImageIO.read(url);
                            ImageIO.write(image, "png", file);
                        }
                        response.setFavicon(new Base64Image(file));
                    } catch(Throwable e) {
                        e.printStackTrace();
                    }
                }
                event.setResponse(response);
            }
        });
        Marine.getScheduler().createSyncTask(new MarineRunnable(this, 15 * 20l, -1) {
            final Random random = new Random();
            @Override
            public void run() {
                final String message = adverts[random.nextInt(adverts.length)];
                for (final Player player : Marine.getPlayers()) {
                    player.sendAboveActionbarMessage(message);
                }
            }
        });
        /*
        Uncomment this to enable a pretty decent measurement tool
        Every 100th tick (should be 5 seconds) it will print out the difference
        May be ~10 to 30 ms off
        Marine.getScheduler().createSyncTask(new MarineRunnable(this, 1l, -1) {
            int tick = 0;
            long start = System.currentTimeMillis();
            @Override
            public void run() {
                if (tick >= 100) {
                    Logging.getLogger().log("100 Ticks = " + (System.currentTimeMillis() - start) + "ms");
                    tick = 0;
                    start = System.currentTimeMillis();
                }
                ++tick;
            }
        });
        */
    }

    private JSONObject getText(final String text) {
        return ListResponse.getText(text);
    }
}
