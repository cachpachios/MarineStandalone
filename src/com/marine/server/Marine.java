package com.marine.server;

import com.marine.StandaloneServer;
import com.marine.player.Player;
import com.marine.world.World;

import java.util.List;
import java.util.UUID;

/**
 * Created 2014-12-02 for MarineStandalone
 *
 * @author Citymonstret
 */
public class Marine {

    protected static MarineServer server;
    protected static StandaloneServer standaloneServer;

    public static void setServer(MarineServer marine) {
        server = marine;
    }

    public static List<Player> getPlayers() {
        return server.getPlayers();
    }

    public static Player getPlayer(String username) {
        return null;
    }

    public static Player getPlayer(UUID uuid) {
        return null;
    }

    public static World getWorld(String name) {
        return server.getWorld(name);
    }

	public static String getMOTD() {
		return server.getMOTD();
	}

    public static MarineServer getServer() {
        return server;
    }

    public static void stop() {
        standaloneServer.stop();
    }

    public static void setStandalone(StandaloneServer s) {
        standaloneServer = s;
    }

    public static int getMaxPlayers() {
        return server.getMaxPlayers();
    }
}
