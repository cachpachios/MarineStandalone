package com.marine.plugins;

import java.util.ArrayList;
import java.util.List;

/**
 * Created 2014-12-10 for MarineStandalone
 *
 * @author Citymonstret
 */
public class PluginManager {

    private final List<Plugin> plugins;

    /**
     * Constructor
     */
    public PluginManager() {
        this.plugins = new ArrayList<>();
    }

    public void addPlugin(final Plugin plugin) {
        this.plugins.add(plugin);
    }

    public void removePlugin(final Plugin plugin) {
        if (this.plugins.contains(plugin))
            this.plugins.remove(plugin);
    }

    public void enablePlugin(final Plugin plugin) {
        if (!this.plugins.contains(plugin))
            throw new RuntimeException("Plugin: " + plugin.getName() + " is not added to the plugin list, can't enable");
        plugin.enable();
    }

    public void disablePlugin(final Plugin plugin) {
        if (!this.plugins.contains(plugin))
            throw new RuntimeException("Plugin: " + plugin.getName() + " is not added to the plugin list, can't disable");
        plugin.disable();
    }

    public List<Plugin> getPlugins() {
        return this.plugins;
    }
}
