package com.marine.game.async;

import com.marine.net.play.clientbound.ChatPacket;
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
        sendAboveActionbarMessage(player, "Welcome online " + player.getName());
    }

    public void sendLeaveMessage(Player player) {
        Marine.broadcastMessage(String.format("%s left the game", player.getName()));
    }


    public void sendChatMessage(String player, String message) {
        Marine.broadcastMessage(String.format("<%s> %s", player, message));
    }

    public void sendAboveActionbarMessage(Player player, String message) {
        player.getClient().sendPacket(new ChatPacket(message, 2));
    }
}
