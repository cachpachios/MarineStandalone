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

package com.marine.net.play.clientbound;

import com.marine.game.chat.ChatMessage;
import com.marine.game.chat.builder.Chat;
import com.marine.io.data.ByteData;
import com.marine.net.Packet;
import com.marine.net.PacketOutputStream;
import com.marine.net.States;
import org.json.simple.JSONObject;

import java.io.IOException;

/**
 * Created 2014-12-04 for MarineStandalone
 *
 * @author Citymonstret
 */
public class ChatPacket extends Packet {

    private String message;
    private int position;
    private JSONObject object;

    public ChatPacket(final String message) {
        this(message, 0);
    }

    public ChatPacket(final String message, final boolean test) {
        this.message = message;
        this.position = 0;
        if (test)
            throw new UnsupportedOperationException("JSON Testing isn't implemented yet");
    }

    public ChatPacket(final Chat chat) {
        this.message = chat.toString();
        this.position = 0;
    }

    public ChatPacket(final Chat chat, final int position) {
        this.message = chat.toString();
        this.position = position;
    }

    public ChatPacket(final ChatMessage message) {
        this.message = message.toString();
        this.position = 0;
    }

    @SuppressWarnings("unchecked")
    public ChatPacket(final String message, final int position) {
        if (message.startsWith("{\"")) {
            this.message = message;
        } else {
            this.object = new JSONObject();
            if (message.length() == 0)
                this.message = "";
            else {
                object.put("text", message);
                this.message = object.toJSONString();
            }
        }
        this.position = position;
    }

    @Override
    public int getID() {
        return 0x02;
    }

    @Override
    public void writeToStream(PacketOutputStream stream) throws IOException {
        if (message == null || message.length() == 0)
            return;

        ByteData data = new ByteData();

        if (message.length() < 32767)
            data.writeUTF8(message);
        else
            data.writeUTF8(message.substring(0, 32766));

        data.writeByte((byte) position);
        
        stream.write(getID(), data);
    }

    @Override
    public void readFromBytes(ByteData input) {
    }

    @Override
    public States getPacketState() {
        return States.INGAME;
    }
}
