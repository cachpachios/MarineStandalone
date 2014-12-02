package com.marine;

import com.marine.settings.ServerSettings;

import java.util.Arrays;

public class MainComponent {

	public static void main(String[] args) {
        ServerSettings.getInstance();

		StandaloneServer server = new StandaloneServer(ServerSettings.getInstance().port, 20); // Port and TickRate
		
		if(!Arrays.asList(args).contains("nogui")) {// Check if GUI should'nt be shown (Yes lazy implementation...)
            Logging.getLogger().createConsoleWindow(); // Create the simplest gui you will ever see :)
            ServerSettings.getInstance().verbose();
        }

        if (!ServerProperties.BUILD_STABLE)
            Logging.getLogger().warn("You are running an unstable build");

		server.start();
	}
}
