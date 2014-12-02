package com.marine.events;

/**
 * Created 2014-12-02 for MarineStandalone
 *
 * @author Citymonstret
 */
public interface Cancellable {
    public boolean isCancelled();
    public void setCancelled(boolean b);
}
