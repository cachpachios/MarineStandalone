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

package com.marine.game.chat;


import java.awt.*;
import java.util.ArrayList;

public enum ChatColor {
    BLACK("black", "0", true, "000000", Color.BLACK),
    DARK_BLUE("dark_blue", "1", true, "0000AA", Color.BLUE),
    DARK_GREEN("dark_green", "2", true, "00AA00", Color.GREEN),
    DARK_AQUA("dark_aqua", "3", true, "00AAAA", Color.CYAN),
    DARK_RED("dark_red", "4", true, "AA0000", Color.RED),
    DARK_PURPLE("dark_purple", "5", true, "AA0000", Color.MAGENTA),
    GOLD("gold", "6", true, "FFAA00", Color.ORANGE),
    GRAY("gray", "7", true, "AAAAAA", Color.GRAY),
    DARK_GRAY("dark_gray", "8", true, "555555", Color.DARK_GRAY),
    BLUE("blue", "9", true, "5555FF", Color.BLUE),
    GREEN("green", "a", true, "55FF55", Color.GREEN),
    AQUA("aqua", "b", true, "55FFFF", Color.CYAN),
    LIGHT_BLUE("aqua", "b", true, "55FFFF", Color.BLUE),
    RED("red", "c", true, "FF5555", Color.RED),
    LIGHT_PURPLE("light_purple", "d", true, "FF55FF", Color.MAGENTA),
    YELLOW("yellow", "e", true, "FFFF55", Color.YELLOW),
    WHITE("white", "f", true, "FFFFFF", Color.WHITE),
    OBFUSCATED("obfuscated", "k", false),
    BOLD("bold", "l", false),
    STRIKETHROUGH("strikethrough", "m", false),
    UNDERLINE("underline", "n", false),
    ITALIC("italic", "o", false),
    RESET("reset", "r", false);

    public static final char COLOR_CHARACTER = '\u00A7';

    /**
     * Constants, you can use these instead of
     * the enums when sending normal messages.
     */
    public static final String
            black = COLOR_CHARACTER + "0",
            dark_blue = COLOR_CHARACTER + "1",
            dark_green = COLOR_CHARACTER + "2",
            dark_aqua = COLOR_CHARACTER + "3",
            dark_red = COLOR_CHARACTER + "4",
            dark_purple = COLOR_CHARACTER + "5",
            gold = COLOR_CHARACTER + "6",
            gray = COLOR_CHARACTER + "7",
            dark_gray = COLOR_CHARACTER + "8",
            blue = COLOR_CHARACTER + "9",
            green = COLOR_CHARACTER + "a",
            aqua = COLOR_CHARACTER + "b",
            red = COLOR_CHARACTER + "c",
            light_purple = COLOR_CHARACTER + "d",
            yellow = COLOR_CHARACTER + "e",
            white = COLOR_CHARACTER + "f",
            obfuscated = COLOR_CHARACTER + "k",
            bold = COLOR_CHARACTER + "l",
            strikethrough = COLOR_CHARACTER + "m",
            underline = COLOR_CHARACTER + "n",
            italic = COLOR_CHARACTER + "o",
            reset = COLOR_CHARACTER + "r";
    private final String packetData;
    private final char id; // Old System one char ID
    private final Color color;
    private final boolean isColor;
    private final boolean isFormat;
    private final String hexa;
    private final String rawID;

    private ChatColor(String rawData, String id, boolean isColor, String hexa, Color color) {
        this.packetData = rawData;
        this.id = id.charAt(0);
        this.color = color;
        this.isColor = isColor;
        this.isFormat = !isColor && !id.equals("r");
        this.hexa = hexa;
        this.rawID = id;
    }

    private ChatColor(String rawData, String id, boolean isColor) {
        this.packetData = rawData;
        this.id = id.charAt(0);
        this.color = null;
        this.isColor = isColor;
        this.isFormat = !isColor && !id.equals("r");
        this.hexa = "";
        this.rawID = id;
    }

    /**
     * Get a list of the string contants (not the enums)
     *
     * @return String constants
     */
    public static String[] constants() {
        return new String[]{
                black, dark_blue, dark_green, dark_aqua,
                dark_red, dark_purple, gold, gray, dark_gray,
                blue, green, aqua, red, light_purple, yellow,
                white, obfuscated, bold, strikethrough, underline,
                italic, reset
        };
    }

    public static ChatColor getColor(char c) {
        for (ChatColor color : ChatColor.values()) {
            if (color.getOldSystemIDChar() == c)
                return color;
        }
        return null;
    }

    public static String transform(char c, String s) {
        char[] characters = s.toCharArray();
        for (int i = 0; i < characters.length; i++) {
            if (i < characters.length - 1) {
                if (characters[i] == c && "0123456789abcdefghjiklmnor".contains(("" + characters[i + 1]).toLowerCase())) {
                    characters[i] = COLOR_CHARACTER;
                }
            }
        }
        return s.replace(c, COLOR_CHARACTER);
    }

    public static String stripColors(String s) {
        return s.replaceAll(COLOR_CHARACTER + "([a-f0-9])", "");
    }

    public static ChatColor randomColor() {
        return ChatColor.values()[(int) Math.floor(Math.random() * 17)];
    }

    public static java.util.List<ChatColor> getColors() {
        ArrayList<ChatColor> colors = new ArrayList<>();
        for (ChatColor color : values()) {
            if (color.isColor())
                colors.add(color);
        }
        return colors;
    }

    public static java.util.List<ChatColor> getFormats() {
        ArrayList<ChatColor> formats = new ArrayList<>();
        for (ChatColor color : values()) {
            if (color.isFormat())
                formats.add(color);
        }
        return formats;
    }

    public String getDataString() {
        return packetData;
    }

    public String getOldSystemID() {
        return rawID;
    }

    public char getOldSystemIDChar() {
        return id;
    }

    public Color getColor() {
        return this.color;
    }

    public boolean isFormat() {
        return isFormat;
    }

    public boolean isColor() {
        return isColor;
    }

    public String getHexa() {
        return this.hexa;
    }

    @Override
    public String toString() {
        return COLOR_CHARACTER + rawID;
    }
}
