///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// MarineStandalone is a minecraft server software and API.
// Copyright (C) MarineMC (marinemc.org)
//
// This program is free software; you can redistribute it and/or modify
// it under the terms of the GNU General Public License as published by
// the Free Software Foundation; either version 2 of the License, or
// (at your option) any later version.
//
// This program is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
// GNU General Public License for more details.
//
// You should have received a copy of the GNU General Public License along
// with this program; if not, write to the Free Software Foundation, Inc.,
// 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

package org.marinemc.game.async;

import org.marinemc.events.standardevents.ChatEvent;
import org.marinemc.game.PlayerManager;
import org.marinemc.logging.Logging;
import org.marinemc.net.play.clientbound.ChatPacket;
import org.marinemc.player.Player;
import org.marinemc.server.Marine;

public class ChatManager {

    public static String CHAT_FORMAT = "<%plr> %msg",
            JOIN_MESSAGE = "%plr joined the game",
            LEAVE_MESSAGE = "%plr left the game",
            WELCOME_MESSAGE = "Welcome online §l%plr";
    private final PlayerManager manager;

    public ChatManager(PlayerManager players) {
        this.manager = players;
    }

    public static String format(String s, Player p) {
        return translate(CHAT_FORMAT, p, s);
    }

    private static String translate(String s, Object... strs) {
        for (Object object : strs) {
            if (object instanceof Player) {
                s = s.replace("%plr", ((Player) object).getDisplayName());
            } else if (object instanceof String) {
                s = s.replace("%msg", object.toString());
            }
        }
        return s;
    }

    public void sendJoinMessage(Player player, String message) {
//        Chat chat = new Chat("WARNING ")
//                .color(ChatColor.RED)
//                .format(ChatColor.BOLD)
//                .with(
//                        new Part("You are a cow", ChatColor.WHITE)
//                                .event(new HoverEvent(HoverEvent.Action.SHOW_TEXT, "§cHello World!\n§m---------\n§lLOLOLOL"))
//                )
//                .event(new Event("hoverEvent", "show_text", "§cA Serious warning"));
//        player.sendMessage(chat);

        Marine.broadcastMessage(translate(message, player));
        player.sendAboveActionbarMessage(translate(WELCOME_MESSAGE, player)); // TODO Custom Message, event and toggleable
    }

    public void sendLeaveMessage(Player player) {
        Marine.broadcastMessage(translate(LEAVE_MESSAGE, player));
    }

    public void brodcastMessage(String msg) {
        manager.broadcastPacket(new ChatPacket(msg));
        Logging.getLogger().log(msg);
    }

    public void sendChatMessage(Player player, String message) {
        ChatEvent event = new ChatEvent(player, message);
        Marine.getServer().callEvent(event);
        if (!event.isCancelled()) {
            Marine.broadcastMessage(
                    translate(CHAT_FORMAT, player, event.getMessage())
            );
        }
    }

}
