package com.marine.plugins;

import com.marine.Logging;

/**
 * Created 2014-12-10 for MarineStandalone
 *
 * @author Citymonstret
 */
public class PluginLogger {

    private final Plugin plugin;

    public PluginLogger(Plugin plugin) {
        this.plugin = plugin;
    }

    final public void log(String message) {
        Logging.getLogger().log(constructMessage(message));
    }

    public PluginMessage constructMessage(String message) {
        return new PluginMessage(String.format("[%s] %s", plugin.getName(), message), this);
    }

    final public Plugin getPlugin() {
        return this.plugin;
    }

    public static class PluginMessage {

        private final String message;
        private final PluginLogger logger;

        public PluginMessage(String message, final PluginLogger logger) {
            this.message = message;
            this.logger = logger;
        }

        public String getMessage() {
            return this.message;
        }
    }
}
