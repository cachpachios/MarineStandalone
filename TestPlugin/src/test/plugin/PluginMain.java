package test.plugin;

import com.google.common.eventbus.Subscribe;
import com.marine.events.Listener;
import com.marine.events.standardevents.ListEvent;
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
        getLogger().log(getName() + " is enabled");
        Marine.getServer().registerListener(new Listener() {
            @Subscribe
            public void onPing(ListEvent event) {
                event.setResponse(new ListResponse("Hello World", 666, 999, event.getResponse().SAMPLE_PLAYERS, event.getResponse().FAVICON));
            }
        });
    }

}
