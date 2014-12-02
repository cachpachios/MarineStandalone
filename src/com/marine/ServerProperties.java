package com.marine;

public class ServerProperties {
	
	// BUILD INFO
	public static final String BUILD_VERSION = "0.01";
	public static final String BUILD_TYPE = "Development";
	public static final String BUILD_NAME = "Netties";
	public static final boolean BUILD_STABLE = false;
	
	// Server Settings TODO: Make this not static and saved in StandaloneServer not here :I
	public static int MAX_PLAYERS = 99;
	public static String MOTD = "MarineStandalone Server 0.1";
	
	
	//FINAL VALUES
	public static final int PROTOCOL_VERSION = 47;
	public static final String MINECRAFT_NAME = "1.8.1";
	public static final int MAX_Y = 256;
	
	
	private static long currentTick;
	
	protected static void tick() { currentTick++; }
	
	public static long getLifeTime() {
		return currentTick;
	}
}
