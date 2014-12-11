package com.marine;

import com.marine.settings.ServerSettings;

import java.util.Arrays;
import java.util.List;

public class MainComponent {

    public static List<String> arguments;

    private static double getJavaVersion() {
        try {
            return Double.parseDouble(System.getProperty("java.specification.version"));
        } catch (Throwable e) {
            return -1;
        }
    }

    public static void main(String[] args) {
        if (getJavaVersion() < 1.7) {
            System.out.println("-- Could not start MarineStandalone: Requires java 1.7 or above --");
            System.exit(1);
        }
        try { // Check OS Arch and warn if lower than 64bit
            if (Integer.parseInt(System.getProperty("sun.arch.data.model")) < 64) {
                Logging.getLogger().warn("Warning Server is running on 32bit this is highly not recommended and can cause fatal errors or lag!");
                Logging.getLogger().warn("Consider updating java or your hardware.");
            }
        } catch (SecurityException e) { // If blocked print an error
            Logging.getLogger().error("Unable to retrieve computer arch! Perhaps blocked by the OS.");
        }
        // Get the arguments
        arguments = Arrays.asList(args);
        // Init. ServerSettings
        ServerSettings.getInstance();
        // Create a new StartSetting instance
        StartSettings settings = new StartSettings();
        // Start settings
        int port = getInteger("port");
        int tickrate = getInteger("tickrate");
        if (port != -1) {
            port = Math.min(port, 65535);
            port = Math.max(port, 0);
        } else {
            port = 25565;
        }
        if (tickrate != -1) {
            tickrate = Math.min(tickrate, 120);
            tickrate = Math.max(tickrate, 0);
        } else {
            tickrate = 20;
        }
        settings.port = port;
        settings.tickrate = tickrate;
        // Check for GUI and init it
        if (!MainComponent.arguments.contains("nogui")) {// Check if GUI shouldn't be shown (Yes lazy implementation...)
            Logging.getLogger().createConsoleWindow(); // Create the simplest gui you will ever see :)
            //ServerSettings.getInstance().verbose();
            System.setErr(Logging.getLogger());
        }
        StandaloneServer server = new StandaloneServer(settings); // Port and TickRate
        // Check if the build is stable
        if (!ServerProperties.BUILD_STABLE)
            Logging.getLogger().warn("You are running an unstable build");
        // Start the server
        server.start();
    }

    private static int getInteger(String argument) {
        for (String s : arguments) {
            if (s.contains(argument)) {
                String[] ss = s.split(":");
                if (ss.length < 2) {
                    return -1;
                }
                try {
                    return Integer.parseInt(ss[1]);
                } catch (Exception e) {
                    return -1;
                }
            }
        }
        return -1;
    }

    public static class StartSettings {

        public int port;
        public int tickrate;

        public StartSettings() {
            this.port = 25565;
            this.tickrate = 20;
        }

        public StartSettings(int port, int tickrate) {
            this.port = port;
            this.tickrate = tickrate;
        }
    }
}
