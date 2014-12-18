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
import org.marinemc.util.StringUtils;
import org.marinemc.util.SystemUtils;
import org.marinemc.util.annotations.Protected;

import java.util.Arrays;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

@Protected
/**
 * @author Fozie
 * @author Citymonstret
 */
public class Bootstrap {

    // SECURITY CHECK START ////////////////////////////////////////////////////////////////////////////////////////////
    static {
        if (System.getSecurityManager() == null) {
            System.setSecurityManager(new MarineSecurityManager(System.getSecurityManager()));
        }
        System.getSecurityManager().checkPermission(MarineSecurityManager.MARINE_PERMISSION);
    }
    // SECURITY CHECK END //////////////////////////////////////////////////////////////////////////////////////////////

    private static Bootstrap instance;

    public List<String> arguments;
    public Timer mainTimer;

    public Bootstrap() {
        System.getSecurityManager().checkPermission(MarineSecurityManager.MARINE_PERMISSION);
    }

    public static void printf(final String s, final Object... os) {
        System.out.println(StringUtils.format("[Marine] " + s, os));
    }

    public static Bootstrap instance() {
        if (instance == null) {
            throw new RuntimeException("Boostrap not initialized");
        }
        return instance;
    }

    public static void main(final String[] args) {
        if (instance != null) {
            throw new RuntimeException("Cannot re-initialize the bootstrapper");
        }
        if (SystemUtils.getJavaVersion() < 7) {
            printf("-- Could not start MarineStandalone: Requires java {0} or above, you have {1} --", 7, SystemUtils.getJavaVersion());
            System.exit(1);
        }
        // Check if run from compressed folder
        /*if (!(new File(".").getAbsolutePath().indexOf('!') == -1)) {
            System.out.println("-- Could not start MarineStandalone: Cannot run from compressed folder");
            System.exit(1);
        }*/
        instance = new Bootstrap();
        instance.start(args);
    }

    private static int getInteger(final String argument) {
        for (final String s : instance.arguments) {
            if (s.contains(argument)) {
                final String[] ss = s.split(":");
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
        double v;
        for (int x = 0; x < 10000; x++) {
            v = Math.sqrt(x * 255);
            v = Math.sin(x);
            v = Math.cos(x);
            v = Math.tan(x);
            v = Math.asin(x);
            v = Math.acos(x);
            v = Math.atan(x);
            v = Math.random();
        }
    }

    private void start(final String[] args) {
        if (SystemUtils.getArch() != 64) {
            if (SystemUtils.getArch() == 32) {
                Logging.getLogger().warn("Server is running on 32bit, this is not recommended as it can cause fatal errors and lag");
                Logging.getLogger().warn("Consider updating java, or your hardware");
            } else {
                Logging.getLogger().warn("Unable to retrieve computer arch, either you're running a 128bit computer (impressive) or it's blocked.");
            }
        }
        // Custom Security Manager = WE BE DA SAFEST
        System.setSecurityManager(new MarineSecurityManager(System.getSecurityManager()));
        // Use IPv4 instead of IPv6
        System.setProperty("java.net.preferIPv4Stack", "true");
        // Make math fast(er than my brain) :D
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
        if (!arguments.contains("nogui")) {// Check if GUI shouldn't be shown (Yes lazy implementation...)
            Logging.getLogger().createConsoleWindow(); // Create the simplest gui you will ever see :)
            //ServerSettings.getInstance().verbose();
            System.setErr(Logging.getLogger());
        }
        Logging.getLogger().logf("Starting MarineStandalone Server - Protocol Version §c§o{0}§0 (Minecraft §c§o{1}§0)",
                ServerProperties.PROTOCOL_VERSION, ServerProperties.MINECRAFT_NAME);
        StandaloneServer server = null;
        try {
            server = new StandaloneServer(settings); // Port and TickRate
        } catch (final Throwable e) {
            Logging.getLogger().error("Could not start the server, errors occurred", e);
            System.exit(1);
        }
        // Check if the build is stable
        if (!ServerProperties.BUILD_STABLE)
            Logging.getLogger().warn("You are running an unstable build");
        // Start the server
        // server.start();
        startTimer(server, tickrate);
    }

    private void startTimer(final StandaloneServer server, final int tickrate) {
        mainTimer = new Timer("mainTimer");
        mainTimer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                // Let's do the logging here instead
                // rather than in the run method
                // to make sure that the error is
                // outputted properly
                try {
                    server.run();
                } catch (final Exception e) {
                    Logging.getLogger().error("Something went wrong in the main thread...", e);
                }
            }
        }, 0L, (1000 / tickrate));
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
