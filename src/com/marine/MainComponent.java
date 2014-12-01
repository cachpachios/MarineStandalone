package com.marine;

import java.util.Arrays;

public class MainComponent {

	public static void main(String[] args) {
		StandaloneServer server = new StandaloneServer(25565, 20); // Port and TickRate
		
		if(!Arrays.asList(args).contains("nogui")) // Check if GUI should'nt be shown (Yes lazy implementation...)
			Logging.getLogger().createConsoleWindow(); // Create the simplest gui you will ever see :)
		
		server.start();
	}

}
