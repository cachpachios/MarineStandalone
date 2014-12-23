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

package org.marinemc.game.chat;

import org.marinemc.game.chat.builder.Event;
import org.marinemc.game.chat.builder.Part;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Chat Message
 *
 * @author Citymonstret
 */
public class ChatMessage {

    public List<Event> events = new ArrayList<>();
    public List<Part> with = new ArrayList<>();
    private String text;
    private ChatColor color = ChatColor.WHITE;
    private List<ChatColor> formats = new ArrayList<>();

    public ChatMessage(String text) {
        this.text = text;
    }

    public ChatMessage color(ChatColor color) {
        this.color = color;
        return this;
    }

    public ChatMessage with(Part part) {
        with.add(part);
        return this;
    }

    public ChatMessage event(Event event) {
        events.add(event);
        return this;
    }

    public ChatMessage format(ChatColor color) {
        if (!color.isFormat())
            throw new RuntimeException(color.getDataString() + " is not a color");
        if (formats.contains(color))
            formats.remove(color);
        else
            formats.add(color);
        return this;
    }

    @Override
    public String toString() {
        String format = "{\"text\":\"%s\",\"color\":\"%s\"%s%s%s}";
        format = format.replaceFirst("%s", text);
        format = format.replaceFirst("%s", color.getDataString());
        StringBuilder w = new StringBuilder("");
        if (with != null && !with.isEmpty()) {
            Iterator<Part> iterator = with.iterator();
            w.append(",\"extra\":[");
            while (iterator.hasNext()) {
                w.append(iterator.next());
                if (iterator.hasNext())
                    w.append(",");
            }
            w.append("]");
        }
        format = format.replaceFirst("%s", w.toString());
        w = new StringBuilder();
        if (events.isEmpty()) {
            format = format.replaceFirst("%s", "");
        } else {
            for (Event event : events) {
                w.append(",").append(event);
            }
            format = format.replaceFirst("%s", w.toString());
        }
        w = new StringBuilder();
        if (formats.isEmpty()) {
            format = format.replaceFirst("%s", "");
        } else {
            for (ChatColor f : formats) {
                w.append(",\"").append(f.getDataString()).append("\":\"true\"");
            }
            format = format.replaceFirst("%s", w.toString());
        }
        return format;
    }
}
