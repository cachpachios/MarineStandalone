package com.marineapi;

public class ServerProperties {
	public static int MAX_PLAYERS = 99;
	public static String MOTD = "MarineStandalone Server 0.1";

	
	//FINAL VALUES
	public static final int PROTOCOL_VERSION = 47;
	public static final String Minecraft_Name = "1.8.1";
	public static final int MAX_Y = 256;
	
	
	private static long currentTick;
	
	protected static void tick() { currentTick++; }
	
	public static long getLifeTime() {
		return currentTick;
	}
}
