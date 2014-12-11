///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// MarineStandalone is a minecraft server software and API.
// Copyright (C) IntellectualSites (marine.intellectualsites.com)
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

package com.marine.net.play.clientbound.player;

import com.marine.game.chat.ChatComponent;
import com.marine.io.data.ByteData;
import com.marine.net.Packet;
import com.marine.net.PacketOutputStream;
import com.marine.net.States;
import com.marine.player.Player;

import java.io.IOException;

/**
 * Created 2014-12-05 for MarineStandalone
 *
 * @author Citymonstret
 */
public class PlayerListItemPacket extends Packet {

    private final Action action;
    private final Player player;
    private final String s;

    public PlayerListItemPacket(Action action, Player player) {
        this.action = action;
        this.player = player;
        this.s = "";
    }

    public PlayerListItemPacket(Action action, Player player, String s) {
        this.action = action;
        this.player = player;
        this.s = s;
    }


    @Override
    public int getID() {
        return 0x38;
    }

    @Override
    public void writeToStream(PacketOutputStream stream) throws IOException {
        ByteData data = new ByteData();
        data.writeVarInt(action.getActionID());
        data.writeVarInt(1);
        data.writeUUID(player.getUUID());
        switch (action) {
            case ADD_PLAYER: {
                data.writeUTF8(player.getName());
                data.writeVarInt(0);
                data.writeVarInt(player.getGamemode().getID());
                data.writeVarInt(10);
                data.writeBoolean(false);
            }
            break;
            case UPDATE_GAME_MODE: {
                data.writeVarInt(player.getGamemode().getID());
            }
            break;
            case UPDATE_LATENCY: {
                data.writeVarInt(10);
            }
            break;
            case UPDATE_DISPLAY_NAME: {
                boolean hasDisplayName = player.hasDisplayName();
                data.writeBoolean(hasDisplayName);
                if (hasDisplayName)
                    data.writeUTF8(new ChatComponent(player.getDisplayName()).toString());
            }
            break;
            case REMOVE_PLAYER:
                break;
            default:
                return;
        }
        stream.write(getID(), data.getBytes());
    }

    @Override
    public void readFromBytes(ByteData input) {

    }

    @Override
    public States getPacketState() {
        return States.INGAME;
    }


    public static enum Action {
        ADD_PLAYER(0),
        UPDATE_GAME_MODE(1),
        UPDATE_LATENCY(2),
        UPDATE_DISPLAY_NAME(3),
        REMOVE_PLAYER(4);

        private final int actionID;

        Action(final int actionID) {
            this.actionID = actionID;
        }

        public int getActionID() {
            return this.actionID;
        }
    }
}
