package com.marine.events;

import com.marine.player.Player;

/**
 * Created 2014-12-07 for MarineStandalone
 *
 * @author Citymonstret
 */
public class ChatEvent extends MarineEvent implements Cancellable {

    private String message;
    private Player sender;
    private boolean cancelled;

    public ChatEvent(Player sender, String message) {
        super("chat");
        this.message = message;
        this.sender = sender;
        this.cancelled = false;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String newMessage) {
        this.message = newMessage;
    }

    public Player getSender() {
        return this.sender;
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
