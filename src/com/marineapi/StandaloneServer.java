package com.marineapi;

import com.marineapi.net.NetworkManager;

public class StandaloneServer {
	
	public static final int PROTOCOL_VERSION = 47;
	public static final String Minecraft_Name = "1.8";
	
	static NetworkManager network;
	
	public static void main(String[] args) {
		Logging.getLogger().log("Marine Standalone Server Starting protocol version "+PROTOCOL_VERSION+" (Minecraft "+Minecraft_Name+")");
		network = new NetworkManager(25565); //TODO: Fix custom port!
		network.openConnection();
	}
}
