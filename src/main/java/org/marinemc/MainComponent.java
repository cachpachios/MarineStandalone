///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// MarineStandalone is a minecraft server software and API.
// Copyright (C) MarineMC (marinemc.org)
//
// This program is free software; you can redistribute it and/or modify
// it under the terms of the GNU General Public License as published by
// the Free Software Foundation; either version 2 of the License, or
// (at your option) any later version.
//
// This program is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
// GNU General Public License for more details.
//
// You should have received a copy of the GNU General Public License along
// with this program; if not, write to the Free Software Foundation, Inc.,
// 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

package org.marinemc;

import org.marinemc.game.system.MarineSecurityManager;
import org.marinemc.settings.ServerSettings;
import org.marinemc.util.annotations.Protected;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

@Protected
/**
 * @author Fozie
 * @author Citymonstret
 */
public class MainComponent {

    public static List<String> arguments;
    public static Timer mainTimer;

    // SECURITY CHECK START ////////////////////////////////////////////////////////////////////////////////////////////

    static {
        if (System.getSecurityManager() == null) {
            System.setSecurityManager(new MarineSecurityManager(System.getSecurityManager()));
        }
        System.getSecurityManager().checkPermission(MarineSecurityManager.MARINE_PERMISSION);
    }

    public MainComponent() {
        System.getSecurityManager().checkPermission(MarineSecurityManager.MARINE_PERMISSION);
    }

    // SECURITY CHECK END //////////////////////////////////////////////////////////////////////////////////////////////

    public static double getJavaVersion() {
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
        // Check if run from compressed folder
        if (!(new File(".").getAbsolutePath().indexOf('!') == -1)) {
            System.out.println("-- Could not start MarineStandalone: Cannot run from compressed folder");
            System.exit(1);
        }
        
        try { // Check OS Arch and warn if lower than 64bit
            if (Integer.parseInt(System.getProperty("sun.arch.data.model")) < 64) {
                Logging.getLogger().warn("Warning Server is running on 32bit this is highly not recommended and can cause fatal errors or lag!");
                Logging.getLogger().warn("Consider updating java or your hardware.");
            }
        } catch (SecurityException e) { // If blocked print an error
            Logging.getLogger().warn("Unable to retrieve computer arch! Perhaps blocked by the JVM.");
        }
        
        // Make sure that plugins can't
        // close down the jvm
        // or replace the security
        // manager with a custom one
        System.setSecurityManager(new MarineSecurityManager(System.getSecurityManager()));

        // Use IPv4 instead of IPv6
        System.setProperty("java.net.preferIPv4Stack", "true");
        
        // Make math fast :D
        chargeUp();
        
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
            tickrate = Math.min(tickrate, 80);
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
        Logging.getLogger().logf("Starting MarineStandalone Server - Protocol Version §c§o{0}§0 (Minecraft §c§o{1}§0)",
                ServerProperties.PROTOCOL_VERSION, ServerProperties.MINECRAFT_NAME);
        StandaloneServer server = null;
        try {
            server = new StandaloneServer(settings); // Port and TickRate
        } catch (Throwable e) {
            Logging.getLogger().error("Could not start the server, errrors occured");
            e.printStackTrace();
            System.exit(1);
        }
        // Check if the build is stable
        if (!ServerProperties.BUILD_STABLE)
            Logging.getLogger().warn("You are running an unstable build");
        // Start the server
        // server.start();
        startTimer(server, tickrate);
    }

    private static void startTimer(final StandaloneServer server, final int tickrate) {
        mainTimer = new Timer("mainTimer");
        mainTimer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                server.run();
            }
        }, 0l, (1000 / tickrate));
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

    private static void chargeUp() {
        for (int x = 0; x < 10000; x++) {
            double v;
            v = Math.sqrt(222039929);
            v = Math.sin(93);
            v = Math.cos(93);
            v = Math.tan(93);
            v = Math.random();
            v = Math.asin(93);
            v = Math.acos(93);
            v = Math.atan(93);
        }
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
