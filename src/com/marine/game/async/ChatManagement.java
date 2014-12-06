package com.marine.game.async;

import com.marine.game.chat.ChatColor;
import com.marine.player.Player;
import com.marine.server.Marine;

public class ChatManagement {

    private static ChatManagement instance;

    public static ChatManagement getInstance() {
        if(instance == null) instance = new ChatManagement();
        return instance;
    }

    public void sendJoinMessage(Player player) {
        Marine.broadcastMessage(String.format("%s joined the game", player.getName()));
        player.sendAboveActionbarMessage("Welcome online " + ChatColor.BOLD + player.getName()); // TODO Custom Message, event and toggleabel
    }

    public void sendLeaveMessage(Player player) {
        Marine.broadcastMessage(String.format("%s left the game", player.getName()));
    }


    public void sendChatMessage(Player player, String message) {
        Marine.broadcastMessage(String.format("<%s> %s", player.getDisplayName(), message));
    }

}
