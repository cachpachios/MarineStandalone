package com.marine.plugins;

/**
 * Created 2014-12-11 for MarineStandalone
 *
 * @author Citymonstret
 */
public class PluginHandlerException extends RuntimeException {

    public PluginHandlerException(PluginClassLoader classLoader, String error, Exception cause) {
        super("PluginClassLoader caused an exception, description: " + error);
    }

    public PluginHandlerException(PluginClassLoader classLoader, String error) {
        super("PluginClassLoader caused an exception, description: " + error);
    }

    public PluginHandlerException(PluginLoader loader, String error, Exception cause) {
        super("PluginLoader caused an exception, description: " + error, cause);
    }

    public PluginHandlerException(PluginLoader loader, String error) {
        super("PluginLoader caused an exception, description: " + error);
    }
}
