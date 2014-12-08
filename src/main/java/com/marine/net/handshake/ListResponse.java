package com.marine.net.handshake;

import org.json.simple.JSONArray;

/**
 * Created 2014-12-02 for MarineStandalone
 *
 * @author Citymonstret
 */
public class ListResponse {

    /**
     * Message of the day
     */
    public final String MOTD;

    /**
     * Current player count
     */
    public final int CURRENT_PLAYERS;

    /**
     * Max player count
     */
    public final int MAX_PLAYERS;

    /**
     * A sample list of players (can be used for messages)
     */
    public JSONArray SAMPLE_PLAYERS;

    /**
     * Server favicon
     */
    public String FAVICON;

    /**
     * Create a new list response
     * @param motd Message of the day
     * @param currentPlayers Current player count
     * @param maxPlayers Max player count
     * @param samplePlayers Sample player list
     * @param favicon Server icon
     */
    public ListResponse(String motd, int currentPlayers, int maxPlayers, JSONArray samplePlayers, String favicon) {
        this.MOTD = motd;
        this.CURRENT_PLAYERS = currentPlayers;
        this.MAX_PLAYERS = maxPlayers;
        this.SAMPLE_PLAYERS = samplePlayers;
        this.FAVICON = favicon;
    }

}
