package com.intellectualsites.marine.object;

/**
 * Created 2014-12-26 for MarineStandalone
 *
 * @author Citymonstret
 */
public enum ServerState {
    MAINTENANCE("&c&lWe are currently in maintenance mode"),
    OPEN();

    private final String specialMOTD;

    ServerState(final String specialMOTD) {
        this.specialMOTD = specialMOTD;
    }

    ServerState() {
        this("");
    }

    @Override
    public String toString() {
        return this.specialMOTD;
    }

    public boolean hasMOTD() {
        return !this.specialMOTD.equals("");
    }
}
