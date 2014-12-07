package com.marine.server;

import com.marine.Logging;
import com.marine.StandaloneServer;
import com.marine.game.chat.ChatColor;
import com.marine.net.play.clientbound.KickPacket;
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

    public static void setServer(MarineServer marine) {
        server = marine;
    }

    public static void stop() {
        for (Player player : getPlayers()) {
            player.getClient().sendPacket(new KickPacket(ChatColor.RED + "" + ChatColor.BOLD + "Server stopped"));
        }
        standaloneServer.stop();
    }

    public static void setStandalone(StandaloneServer s) {
        standaloneServer = s;
    }

    public static int getMaxPlayers() {
        return server.getMaxPlayers();
    }

    public static void broadcastMessage(String string) {
        for (Player player : getPlayers()) {
            player.sendMessage(string);
        }
        Logging.getLogger().log(string);
    }

}
