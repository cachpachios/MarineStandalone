package com.marine.events;

import com.google.common.eventbus.Subscribe;
import com.marine.events.standardevents.ChatEvent;
import com.marine.events.standardevents.ListEvent;
import com.marine.game.chat.ChatColor;
import com.marine.net.handshake.ListResponse;

/**
 * Created 2014-12-07 for MarineStandalone
 *
 * @author Citymonstret
 */
public class TestListener implements Listener {

    @Subscribe
    public void onChat(ChatEvent event) {
        if (!event.isCancelled()) {
            event.setMessage(
                    ChatColor.transform('&', event.getMessage())
            );
        }
    }

    @Subscribe
    public void onListRequest(ListEvent event) {
        ListResponse response = event.getResponse();
        ListResponse newResponse = new ListResponse(
                "Handled",
                response.CURRENT_PLAYERS,
                (int) (1 + Math.random() * 255),
                response.SAMPLE_PLAYERS,
                response.FAVICON);
        event.setResponse(newResponse);
    }

}
