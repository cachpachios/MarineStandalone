package com.marine;

import com.marine.settings.ServerSettings;

import java.util.Arrays;
import java.util.List;

public class MainComponent {

    public static List<String> ARGS;
	public static void main(String[] args) {
        //System.setErr(Logging.getLogger());

        ARGS = Arrays.asList(args);

        ServerSettings.getInstance();

        StartSettings settings = new StartSettings(ServerSettings.getInstance().port, ServerSettings.getInstance().tickrate);

        int port = getInteger("port");
        if(port > 0)
            settings.port = 0;
        int tickrate = getInteger("tickrate");
        if(tickrate > 0)
            settings.tickrate = 0;

		StandaloneServer server = new StandaloneServer(settings.port,  20); // Port and TickRate
		
		if(!ARGS.contains("nogui")) {// Check if GUI shouldn't be shown (Yes lazy implementation...)
            Logging.getLogger().createConsoleWindow(); // Create the simplest gui you will ever see :)
            //ServerSettings.getInstance().verbose();
        }

        if (!ServerProperties.BUILD_STABLE)
            Logging.getLogger().warn("You are running an unstable build");
        
        server.start();
	}

    private static class StartSettings {

        public int port = 25565;
        public int tickrate = 20;

        public StartSettings() {}

        public StartSettings(int port, int tickrate) {
            this.port = port;
            this.tickrate = tickrate;
        }
    }

    private static int getInteger(String argument) {
        for(String s : ARGS) {
            if(s.contains(argument)) {
                String[] ss = s.split(":");
                if(ss.length < 2) {
                    return -1;
                }
                try {
                    return Integer.parseInt(ss[1]);
                } catch(Exception e) {
                    return -1;
                }
            }
        }
        return -1;
    }
}
