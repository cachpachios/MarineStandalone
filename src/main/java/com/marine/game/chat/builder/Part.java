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
import java.util.List;

/**
 * Created 2014-12-13 for MarineStandalone
 *
 * @author Citymonstret
 */
public class Part {

    private String s;
    private ChatColor c;
    private List<Event> events = new ArrayList<Event>();

    public Part(String s, ChatColor c) {
        this.s = s;
        this.c = c;
    }

    public Part event(Event event) {
        events.add(event);
        return this;
    }

    private String eFormat() {
        StringBuilder b = new StringBuilder();
        for (Event e : events)
            b.append(",").append(e);
        return b.toString();
    }

    @Override
    public String toString() {
        return String.format("{\"text\":\"%s\",\"color\":\"%s\"%s}", s, c.getDataString(), events.isEmpty() ? "" : eFormat());
    }
}
