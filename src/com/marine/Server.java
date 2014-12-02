package com.marine;

import com.marine.game.*;
import com.marine.player.Player;

import java.util.List;

/**
 * Created 2014-12-02 for MarineStandalone
 *
 * @author Citymonstret
 */
public class Server {

    private static PlayerManagment playerManagment;
    private static ChatManagment chatManagment;
    private static MovmentManagment movmentManagment;
    private static WorldManager worldManager;
    private static CommandManager commandManager;

    public static void setup(StandaloneServer server) {
        playerManagment = new PlayerManagment(server);
        chatManagment = new ChatManagment();
        movmentManagment = new MovmentManagment();
        worldManager = new WorldManager();
        commandManager = new CommandManager();
    }

    public static List<Player> getOnlinePlayers() {
        return playerManagment.allPlayers;
    }

}
