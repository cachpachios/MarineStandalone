package com.marine.server;

import com.marine.player.Player;

import java.util.List;

/**
 * Created 2014-12-02 for MarineStandalone
 *
 * @author Citymonstret
 */
public class Marine {

    protected static MarineServer server;

    protected static void setServer(MarineServer marine) {
        server = marine;
    }

    public static List<Player> getPlayers() {
        return server.getPlayers();
    }

	public static String getMOTD() {
		return server.getMOTD();
	}
}
