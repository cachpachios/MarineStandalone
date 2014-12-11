package com.marine.events.standardevents;

import com.marine.PlayerEvent;
import com.marine.events.Cancellable;
import com.marine.player.Player;

/**
 * Created 2014-12-07 for MarineStandalone
 *
 * @author Citymonstret
 */
public class ChatEvent extends PlayerEvent implements Cancellable {

    private String message;
    private boolean cancelled;

    public ChatEvent(final Player sender, String message) {
        super(sender, "chat");
        this.message = message;
        this.cancelled = false;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String newMessage) {
        this.message = newMessage;
    }

    public Player getSender() {
        return this.getPlayer();
    }

    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    @Override
    public void setCancelled(boolean b) {
        this.cancelled = b;
    }
}
