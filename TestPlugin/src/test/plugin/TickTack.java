package test.plugin;

import com.marine.game.scheduler.MarineRunnable;
import com.marine.plugins.Plugin;

/**
 * Created 2014-12-10 for MarineStandalone
 *
 * @author Citymonstret
 */
public class TickTack extends MarineRunnable {

    private final Plugin plugin;

    public TickTack(final Plugin plugin) {
        super(20l);
        this.plugin = plugin;
    }

    long t = 1L;
    @Override
    public void run() {
        if(t++ % 2 == 0) {
            plugin.getLogger().log("tack");
        } else {
            plugin.getLogger().log("tick");
        }
    }
}
