package com.marine.plugins;

/**
 * Created 2014-12-10 for MarineStandalone
 *
 * @author Citymonstret
 */
public class PluginException extends RuntimeException {

    public PluginException(Plugin plugin, String message) {
        super(String.format("Plugin %s caused an error -> %s", plugin.getName(), message));
    }
}
