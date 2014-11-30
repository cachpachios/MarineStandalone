package com.marine.game.chat;


public enum ChatColor {	
	BLACK			("black",		 "0"),  
    DARK_BLUE		("dark_blue",	 "1"),  
    DARK_GREEN		("dark_green",	 "2"),  
    DARK_AQUA		("dark_aqua",	 "3"),  
    DARK_RED		("dark_red",	 "4"),  
    DARK_PURPLE		("dark_purple",	 "5"),  
    GOLD			("gold",		 "6"),  
    GRAY			("gray",		 "7"),  
    DARK_GRAy		("dark_gray",	 "8"),  
    BLUE			("blue",		 "9"),  
    GREEN			("green",		 "a"),  
    AQUA			("aqua",		 "b"),  
    LIGHT_BLUE		("aqua",		 "b"),  
    RED				("red",			 "c"),  
    LIGHT_PURPLE	("light_purple", "d"),  
    YELLOW			("yellow",		 "e"),  
    WHITE			("white",		 "f"),  
    OBFUSCATED		("obfuscated",	 "k"),  
    BOLD			("bold",		 "l"),  
    STRIKETHROUGH	("strikethrough","m"),  
    UNDERLINE		("underline",	 "n"),  
    ITALIC			("italic",		 "o"),  
    RESET			("reset",		 "r");
	
	private final String packetData;
	private final String id; // Old System one char ID
	
	private ChatColor(String rawData, String id) {
		this.packetData = rawData;
		this.id = id;
	}
	
	
	public String getDataString() {
		return packetData;
	}
	
	public String getOldSystemID() {
		return id;
	}
	
	public char getOldSystemIDChar() {
		return id.charAt(0);
	}
	
	public String toString() {
		return "§" + id;
	}
}
