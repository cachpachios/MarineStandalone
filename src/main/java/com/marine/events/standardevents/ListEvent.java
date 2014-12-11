package com.marine.events.standardevents;

import com.marine.events.Cancellable;
import com.marine.events.MarineEvent;
import com.marine.net.handshake.ListResponse;

/**
 * Created 2014-12-02 for MarineStandalone
 *
 * @author Citymonstret
 */
public class ListEvent extends MarineEvent implements Cancellable {

    private ListResponse response;
    private boolean cancelled;

    public ListEvent(ListResponse response) {
        super("list");
        this.response = response;
        this.cancelled = false;
    }

    public ListResponse getResponse() {
        return this.response;
    }

    public void setResponse(ListResponse response) {
        this.response = response;
    }

    @Override
    public boolean isCancelled() {
        return this.cancelled;
    }

    @Override
    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }
}
