///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// MarineStandalone is a minecraft server software and API.
// Copyright (C) IntellectualSites (marine.intellectualsites.com)
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

package com.marine.game.system;

import com.marine.Logging;
import com.marine.plugins.Plugin;
import com.marine.plugins.PluginClassLoader;

import java.security.AccessControlException;
import java.security.Permission;

/**
 * Created 2014-12-13 for MarineStandalone
 *
 * @author Citymonstret
 */
public class MarineSecurityManager extends SecurityManager {

    /**
     * The marine permission
     * <p/>
     * Used to indicate internal classes that shouldn't be
     * used by plugins - or alike
     */
    public static Permission MARINE_PERMISSION = new RuntimePermission("marineInternal");

    private final SecurityManager defaultSecurityManager;

    /**
     * Constructor
     *
     * @param defaultSecurityManager Default security manager (can be null)
     */
    public MarineSecurityManager(final SecurityManager defaultSecurityManager) {
        this.defaultSecurityManager = defaultSecurityManager;
    }

    /**
     * Get the status (i.e, throws exception if illegal access)
     *
     * @param msg Message to send
     * @param allowOther Allow loaders other than the plugin one
     */
    private void status(final String msg, boolean allowOther) {
        final Class[] context = getClassContext();
        ClassLoader loader;
        for (int i = 1; i < context.length; ++i) {
            if ((loader = context[i].getClassLoader()) != null && loader != ClassLoader.getSystemClassLoader()) {
                if (loader instanceof PluginClassLoader) {
                    final Plugin plugin = ((PluginClassLoader) loader).plugin;
                    Logging.getLogger().warn("Plugin (" + plugin.getName() + ") tried to use illegal methods: " + msg);
                    throw new PluginSecurityException(plugin, msg);
                } else if (!allowOther) {
                    throw new AccessControlException("Internal (Marine) Security Breach: " + msg);
                }
            }
        }
    }

    @Override
    public void checkExit(int status) {
        // No plugins will be allowed to do this
        status("Close JVM", true);
    }

    @Override
    public void checkCreateClassLoader() {
        // Class loaders should not
        // be created by plugins, we provide
        // them with what they need to do so
        status("Create class loader", true);
    }

    @Override
    public void checkExec(String cmd) {
        // Ain't no commands getting through
        status("Execute command", false);
    }

    @Override
    public void checkDelete(String file) {
        // Plugins are not able to delete
        // files, if so is needed they can
        // inform the server owner about it
        status("Delete file", true);
    }

    @Override
    public void checkPermission(Permission perm, Object context) {
        checkInternal(perm);
        if (this.defaultSecurityManager != null) {
            defaultSecurityManager.checkPermission(perm, context);
        }
    }

    @Override
    public void checkAccess(ThreadGroup g) {
        status("Thread Group Access", true);
    }

    @Override
    public void checkAccess(Thread t) {
        status("Thread Access", true);
    }

    /**
     * An internal permission check
     *
     * @param permission Permission to check for
     */
    protected void checkInternal(Permission permission) {
        switch (permission.getName()) {
            case "marineInternal":
                // Only allow access to non-plugin loaded files
                // This is quite epic tbh
                status("Marine Internal Method Access", true);
            case "setSecurityManager":
                // Ain't nobody overriding me! He said, and shook his head
                status("setSecurityManager during runtime", false);
                break;
            case "setIO":
                // Set the IO managers should not
                // be allowed, as a plugin could
                // potentially hide nasty output
                status("setIO, could be used to hide output", false);
                break;
            case "read":
                // Read
                status("Read...", true);
            default:
                // Let's see what the JVM would do...
                // defaultSecurityManager.checkPermission(perm);
                break;
        }
    }

    @Override
    public void checkPermission(Permission perm) {
        checkInternal(perm);
        if (this.defaultSecurityManager != null) {
            defaultSecurityManager.checkPermission(perm);
        }
    }

    /**
     * SecurityException - Used to indicate illegal plugin activity
     *
     * A part of the MarineStandalone Security Suite
     *
     * @author Citymonstret
     */
    public static class PluginSecurityException extends AccessControlException {

        /**
         * Constructor
         *
         * @param plugin Plugin that breached the security
         * @param message Message (Description)
         */
        public PluginSecurityException(final Plugin plugin, final String message) {
            super("Plugin Security Breach: " + plugin.getName() + " did an illegal action: " + message);
        }

    }
}
