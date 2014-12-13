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

package com.marine.game.chat.builder;

import com.marine.game.chat.ChatColor;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created 2014-12-13 for MarineStandalone
 *
 * @author Citymonstret
 */
public class Chat {

    public List<Event> events = new ArrayList<>();
    public List<Part> with = new ArrayList<>();
    private String text;
    private ChatColor color = ChatColor.WHITE;

    public Chat(String text) {
        this.text = text;
    }

    public static void main(String[] args) {
        Chat chat = new Chat("Dogs").color(ChatColor.GREEN).with(new Part("Cows", ChatColor.YELLOW).event(new Event("hoverEvent", "show_text", ":DD")))
                .with(new Part("Cats", ChatColor.BLACK)).event(new Event("hoverEvent", "show_text", "hello world"));
        System.out.println(chat.toString());
    }

    public Chat color(ChatColor color) {
        this.color = color;
        return this;
    }

    public Chat with(Part part) {
        with.add(part);
        return this;
    }

    public Chat event(Event event) {
        events.add(event);
        return this;
    }

    @Override
    public String toString() {
        String format = "{\"text\":\"%s\",\"color\":\"%s\",\"extra\":[%s]%s}";
        format = format.replaceFirst("%s", text);
        format = format.replaceFirst("%s", color.getDataString());
        StringBuilder w = new StringBuilder("");
        if (with != null && !with.isEmpty()) {
            Iterator<Part> iterator = with.iterator();
            while (iterator.hasNext()) {
                w.append(iterator.next());
                if (iterator.hasNext())
                    w.append(",");
            }
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
        return format;
    }
}
