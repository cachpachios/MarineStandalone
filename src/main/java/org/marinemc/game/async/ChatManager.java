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
import org.marinemc.game.player.Player;
import org.marinemc.io.binary.ByteData;
import org.marinemc.logging.Logging;
import org.marinemc.net.Client;
import org.marinemc.net.play.clientbound.ChatPacket;
import org.marinemc.net.play.serverbound.IncomingChatPacket;
import org.marinemc.server.Marine;
/**
 * A chat manager :)
 * 
 * @author Citymonstret
 */
public class ChatManager {

    public static String CHAT_FORMAT = "<%plr> %msg",
            JOIN_MESSAGE = "%plr joined the game",
            LEAVE_MESSAGE = "%plr left the game",
            WELCOME_MESSAGE = "Welcome online §l%plr";

    private static ChatManager instance;

    public static ChatManager getInstance() {
        if (instance == null) {
            instance = new ChatManager();
        }
        return instance;
    }

    public static String format(String s, Player p) {
        return translate(CHAT_FORMAT, p, s);
    }

    private static String translate(String s, Object... strs) {
        for (Object object : strs) {
            if (object instanceof Player) {
                s = s.replace("%plr", ((Player) object).getUserName());
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

    public void broadcastMessage(String msg) {
        Marine.getServer().getPlayerManager().broadcastPacket(new ChatPacket(msg));
        Logging.getLogger().log(msg);
    }

    public void sendChatMessage(Player player, String message) {
        if (player.checkForSpam()) {
            player.kick("Do not spam");
        } else {
            ChatEvent event = new ChatEvent(player, message);
            Marine.getServer().callEvent(event);
            if (!event.isCancelled()) {
                Marine.broadcastMessage(
                        translate(CHAT_FORMAT, player, event.getMessage())
                );
            }
        }
    }
    
    public void interceptChatPacket(ByteData data, Client c) {
        IncomingChatPacket p = new IncomingChatPacket();
        p.readFromBytes(data);
        if (p.getMessage().startsWith("/")) {
            String[] parts = p.getMessage().split(" ");
            String[] args;
            if (parts.length < 2) {
                args = new String[]{};
            } else {
                args = new String[parts.length - 1];
                System.arraycopy(parts, 1, args, 0, parts.length - 1);
            }
            Marine.getServer().getPlayerManager().getPlayerByClient(c).executeCommand(parts[0], args);
        } else {
            broadcastMessage(ChatManager.format(p.getMessage(), Marine.getServer().getPlayerManager().getPlayerByClient(c)));
        }
    }
}
