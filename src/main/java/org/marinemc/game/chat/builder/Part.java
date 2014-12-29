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

package org.marinemc.game.chat.builder;

import java.util.ArrayList;
import java.util.List;

import org.marinemc.game.chat.ChatColor;

/**
 * Created 2014-12-13 for MarineStandalone
 *
 * @author Citymonstret
 */
public class Part {

    private String s;
    private ChatColor c;
    private List<Event> events = new ArrayList<>();
    private List<ChatColor> formats = new ArrayList<>();

    public Part(String s, ChatColor c) {
        this.s = s;
        this.c = c;
    }

    public Part event(Event event) {
        events.add(event);
        return this;
    }

    public Part format(ChatColor color) {
        if (!color.isFormat())
            throw new RuntimeException(color.getDataString() + " is not a format");
        if (formats.contains(color))
            formats.remove(color);
        else
            formats.add(color);
        return this;
    }

    private String eFormat() {
        StringBuilder b = new StringBuilder();
        for (Event e : events)
            b.append(",").append(e);
        return b.toString();
    }

    private String fFormat() {
        StringBuilder b = new StringBuilder();
        for (ChatColor c : formats) {
            b.append(",\"").append(c.getDataString()).append("\":\"true\"");
        }
        for (ChatColor c : ChatColor.getFormats()) {
            if (formats.contains(c)) continue;
            b.append(",\"").append(c.getDataString()).append("\":\"false\"");
        }
        return b.toString();
    }

    @Override
    public String toString() {
        return String.format("{\"text\":\"%s\",\"color\":\"%s\"%s%s}", s, c.getDataString(), events.isEmpty() ? "" : eFormat(), fFormat());
    }
}
