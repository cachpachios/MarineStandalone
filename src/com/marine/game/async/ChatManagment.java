package com.marine.game.async;

import com.marine.player.Player;
import com.marine.server.Marine;

public class ChatManagment {

    private static ChatManagment instance;

    public static ChatManagment getInstance() {
        if(instance == null) instance = new ChatManagment();
        return instance;
    }

    public void sendJoinMessage(Player player) {
        Marine.broadcastMessage(String.format("%s joined the game", player.getName()));
        player.sendAboveActionbarMessage("Welcome online " + player.getName()); // TODO Custom Message, event and toggleabel
    }

    public void sendLeaveMessage(Player player) {
        Marine.broadcastMessage(String.format("%s left the game", player.getName()));
    }


    public void sendChatMessage(String player, String message) {
        Marine.broadcastMessage(String.format("<%s> %s", player, message));
    }

}
