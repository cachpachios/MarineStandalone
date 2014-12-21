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

package org.marinemc.game.system;

import org.marinemc.logging.Logging;
import org.marinemc.plugins.Plugin;
import org.marinemc.plugins.PluginClassLoader;
import org.marinemc.util.annotations.Protected;

import java.security.AccessControlException;
import java.security.Permission;

/**
 * A custom SecurityManager created for
 * MarineStandalone. Will block pesky actions made
 * by plugins.
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
     * Check if a class is @Protected
     *
     * @param context Class
     * @return true if the class is @Protected
     */
    public boolean isProtected(final Class context) {
        return context.getAnnotation(Protected.class) instanceof Protected;
    }

    /**
     * Check if a context is allowed to even access the class
     *
     * @param context Class
     * @return true if the context is allowed to, false if not
     */
    public boolean checkAllowed(final Class context) {
        if (!isProtected(context))
            return false;
        try {
            status("Attempt Access", true);
        } catch (final Throwable e) {
            return false;
        }
        return true;
    }

    /**
     * Get the status (i.e, throws exception if illegal access)
     *
     * @param msg        Message to send
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
     * <p/>
     * A part of the MarineStandalone Security Suite
     *
     * @author Citymonstret
     */
    public static class PluginSecurityException extends AccessControlException {

        /**
         * Constructor
         *
         * @param plugin  Plugin that breached the security
         * @param message Message (Description)
         */
        public PluginSecurityException(final Plugin plugin, final String message) {
            super("Plugin Security Breach: " + plugin.getName() + " did an illegal action: " + message);
        }

    }
}
