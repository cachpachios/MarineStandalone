package com.marine.events;

import com.google.common.eventbus.Subscribe;

/**
 * Created 2014-12-07 for MarineStandalone
 *
 * @author Citymonstret
 */
public class TestListener implements Listener {

    @Subscribe
    public void onChat(ChatEvent event) {
        if(!event.isCancelled()) {
            event.setMessage("1__" + event.getMessage());
        }
    }

}
