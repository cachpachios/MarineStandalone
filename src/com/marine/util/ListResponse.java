package com.marine.util;

import org.json.simple.JSONArray;

/**
 * Created 2014-12-02 for MarineStandalone
 *
 * @author Citymonstret
 */
public class ListResponse {

    public final String MOTD;
    public final int CURRENT_PLAYERS;
    public final int MAX_PLAYERS;
    public JSONArray SAMPLE_PLAYERS;
    public String FAVICON;

    public ListResponse(String motd, int currentPlayers, int maxPlayers, JSONArray samplePlayers, String favicon) {
        this.MOTD = motd;
        this.CURRENT_PLAYERS = currentPlayers;
        this.MAX_PLAYERS = maxPlayers;
        this.SAMPLE_PLAYERS = samplePlayers;
        this.FAVICON = favicon;
    }

}
