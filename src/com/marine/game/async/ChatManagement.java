package com.marine.game.async;

import com.marine.events.ChatEvent;
import com.marine.player.Player;
import com.marine.server.Marine;

public class ChatManagement {

    private static ChatManagement instance;

    public static String CHAT_FORMAT = "<%plr> %msg",
                         JOIN_MESSAGE = "%plr joined the game",
                         LEAVE_MESSAGE = "%plr left the game",
                         WELCOME_MESSAGE = "Welcome online §l%plr";

    public static ChatManagement getInstance() {
        if(instance == null) instance = new ChatManagement();
        return instance;
    }

    public void sendJoinMessage(Player player) {
        Marine.broadcastMessage(translate(JOIN_MESSAGE, player));
        player.sendAboveActionbarMessage(translate(WELCOME_MESSAGE, player)); // TODO Custom Message, event and toggleable
    }

    public void sendLeaveMessage(Player player) {
        Marine.broadcastMessage(translate(LEAVE_MESSAGE, player));
    }

    private String translate(String s, Object ... strs) {
        for(Object object : strs) {
            if(object instanceof Player) {
                s = s.replace("%plr", ((Player) object).getDisplayName());
            } else if(object instanceof String) {
                s = s.replace("%msg", object.toString());
            }
        }
        return s;
    }

    public void sendChatMessage(Player player, String message) {
        ChatEvent event = new ChatEvent(player, message);
        Marine.getServer().callEvent(event);
        if(!event.isCancelled()) {
            Marine.broadcastMessage(
                translate(CHAT_FORMAT, player, event.getMessage())
            );
        }
    }

}
