package com.marine.game.chat;


import java.awt.*;
import java.util.ArrayList;

public enum ChatColor {
	BLACK			("black",		 "0", true, "000000", Color.BLACK),
    DARK_BLUE		("dark_blue",	 "1", true, "0000AA", Color.BLUE),
    DARK_GREEN		("dark_green",	 "2", true, "00AA00", Color.GREEN),
    DARK_AQUA		("dark_aqua",	 "3", true, "00AAAA", Color.CYAN),
    DARK_RED		("dark_red",	 "4", true, "AA0000", Color.RED),
    DARK_PURPLE		("dark_purple",	 "5", true, "AA0000", Color.MAGENTA),
    GOLD			("gold",		 "6", true, "FFAA00", Color.ORANGE),
    GRAY			("gray",		 "7", true, "AAAAAA", Color.GRAY),
    DARK_GRAY		("dark_gray",	 "8", true, "555555", Color.DARK_GRAY),
    BLUE			("blue",		 "9", true, "5555FF", Color.BLUE),
    GREEN			("green",		 "a", true, "55FF55", Color.GREEN),
    AQUA			("aqua",		 "b", true, "55FFFF", Color.CYAN),
    LIGHT_BLUE		("aqua",		 "b", true, "55FFFF", Color.BLUE),
    RED				("red",			 "c", true, "FF5555", Color.RED),
    LIGHT_PURPLE	("light_purple", "d", true, "FF55FF", Color.MAGENTA),
    YELLOW			("yellow",		 "e", true, "FFFF55", Color.YELLOW),
    WHITE			("white",		 "f", true, "FFFFFF", Color.WHITE),
    OBFUSCATED		("obfuscated",	 "k", false),
    BOLD			("bold",		 "l", false),
    STRIKETHROUGH	("strikethrough","m", false),
    UNDERLINE		("underline",	 "n", false),
    ITALIC			("italic",		 "o", false),
    RESET			("reset",		 "r", false);

    public static final char COLOR_CHARACTER = '\u00A7';

	private final String packetData;
	private final char id; // Old System one char ID
    private final Color color;
    private final boolean isColor;
    private final boolean isFormat;
    private final String hexa;
	
	private ChatColor(String rawData, String id, boolean isColor, String hexa, Color color) {
		this.packetData = rawData;
		this.id = id.charAt(0);
        this.color = color;
        this.isColor = isColor;
        this.isFormat = !isColor && !id.equals("r");
        this.hexa = hexa;
	}

    private ChatColor(String rawData, String id, boolean isColor) {
        this.packetData = rawData;
        this.id = id.charAt(0);
        this.color = null;
        this.isColor = isColor;
        this.isFormat = !isColor && !id.equals("r");
        this.hexa = "";
    }

    public static ChatColor getColor(char c) {
        for(ChatColor color : ChatColor.values()) {
            if(color.getOldSystemIDChar() == c)
                return color;
        }
        return null;
    }

	public String getDataString() {
		return packetData;
	}
	
	public String getOldSystemID() {
		return new String(new char[] {id});
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

    public static String transform(char c, String s) {
        char[] characters = s.toCharArray();
        for(int i = 0; i < characters.length; i++) {
            if(i < characters.length - 1) {
                if(characters[i] == c && "0123456789abcdefghjiklmnor".contains(("" + characters[i + 1]).toLowerCase())) {
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
        for(ChatColor color : values()) {
            if(color.isColor())
                colors.add(color);
        }
        return colors;
    }

    public static java.util.List<ChatColor> getFormats() {
        ArrayList<ChatColor> formats = new ArrayList<>();
        for(ChatColor color : values()) {
            if(color.isFormat())
                formats.add(color);
        }
        return formats;
    }

    @Override
	public String toString() {
		return "" + COLOR_CHARACTER + id;
	}
}
