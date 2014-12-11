package com.marine.plugins;

/**
 * Created 2014-12-10 for MarineStandalone
 *
 * @author Citymonstret
 */
public class PluginException extends RuntimeException {

    public PluginException(final Plugin plugin, final String message) {
        super(String.format("Plugin %s caused an error -> %s", plugin.getName(), message));
    }

    public PluginException(final Plugin plugin, final String message, final Throwable cause) {
        super(String.format("Plugin %s caused an error -> %s", plugin.getName(), message), cause);
    }
}
