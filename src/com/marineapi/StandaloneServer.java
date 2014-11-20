package com.marineapi;

import com.marineapi.net.NetworkManager;

public class StandaloneServer {
	
	static NetworkManager network;
	
	public static void main(String[] args) {
		Logging.getLogger().log("Marine Standalone Server Starting protocol version "+ ServerProperties.PROTOCOL_VERSION +" (Minecraft "+ ServerProperties.Minecraft_Name +")");
		network = new NetworkManager(25565); //TODO: Fix custom port!
		network.openConnection();
	}
}
