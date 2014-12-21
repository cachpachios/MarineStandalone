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

package org.marinemc.net.interceptors;

import org.marinemc.game.PlayerManager;
import org.marinemc.io.data.ByteData;
import org.marinemc.net.Client;
import org.marinemc.net.play.KeepAlivePacket;
import org.marinemc.net.play.serverbound.IncomingChatPacket;
import org.marinemc.net.play.serverbound.player.PlayerLookPacket;
import org.marinemc.net.play.serverbound.player.PlayerPositionPacket;
import org.marinemc.net.play.serverbound.player.ServerboundPlayerLookPositionPacket;
import org.marinemc.util.Location;
/**
 * @author Fozie
 */
public class IngameInterceptor implements PacketInterceptor {

    PlayerManager players;

    //int lastID; // Just for debugging

    public IngameInterceptor(PlayerManager m) {
        players = m;
    }

    @Override
    public boolean intercept(int id, ByteData data, Client c) {
        if (id == 0x00) {
            KeepAlivePacket p = new KeepAlivePacket();
            p.readFromBytes(data);
            players.keepAlive(c.getUID(), p.getID());
        } else if (id == 0x01) {
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
                players.getPlayerByClient(c).executeCommand(parts[0], args);
            } else {
                // players.getChat().brodcastMessage(ChatManager.format(p.getMessage(), players.getPlayerByClient(c)));
                players.getChat().sendChatMessage(players.getPlayerByClient(c), p.getMessage());
            }
        } else if (id == 0x06) {
            ServerboundPlayerLookPositionPacket packet = new ServerboundPlayerLookPositionPacket();
            packet.readFromBytes(data);
            players.getMovementManager().registerMovement(players.getPlayerByClient(c), packet.getLocation());
            return true;
        } else if (id == 0x05) {
            PlayerLookPacket packet = new PlayerLookPacket();
            packet.readFromBytes(data);
            players.getMovementManager().registerLook(players.getPlayerByClient(c), packet.getYaw(), packet.getPitch());
            return true;
        } else if (id == 0x04) {
            PlayerPositionPacket packet = new PlayerPositionPacket();
            packet.readFromBytes(data);
            players.getMovementManager().registerMovement(players.getPlayerByClient(c), new Location(null, packet.X, packet.Y, packet.Z));
            return true;
        }
        return false;
    }

}
