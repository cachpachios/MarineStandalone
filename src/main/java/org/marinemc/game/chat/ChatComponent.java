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

import org.json.simple.JSONObject;

public class ChatComponent {

    JSONObject JSON;

    @SuppressWarnings("unchecked")
    public ChatComponent(String text, boolean bold, boolean italic, boolean underlined, boolean stirkethrough, ChatColor color) {
        JSON = new JSONObject();
        JSON.put("color", color.getDataString());
        JSON.put("bold", bold);
        JSON.put("italic", italic);
        JSON.put("underlined", underlined);
        JSON.put("strikethrough", stirkethrough);
        JSON.put("text", text);
    }

    public ChatComponent(String text) {
        this(text, false, false, false, false, ChatColor.RESET);
    }

    public String toString() {
        return JSON.toJSONString();
    }

    public JSONObject getJsonObject() {
        return JSON;
    }


}
