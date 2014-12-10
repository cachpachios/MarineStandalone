package com.marine.plugins;

import java.io.File;
import java.util.UUID;

/**
 * Created 2014-12-10 for MarineStandalone
 *
 * @author Citymonstret
 */
public class Plugin {

    private final UUID uuid;
    private boolean enabled;

    private String name, version, author;

    private File data;

    private PluginClassLoader classLoader;

    private PluginFile desc;

    private PluginLogger logger;

    /**
     * Constructor
     */
    public Plugin() {
        this.uuid = UUID.randomUUID();
        this.enabled = false;
    }

    final public void create(final PluginFile desc, final File data, final PluginClassLoader classLoader) {
        if (this.desc != null)
            throw new RuntimeException("Plugin already created: " + desc.name);
        this.desc = desc;
        this.classLoader = classLoader;
        this.name = desc.name;
        this.data = data;
        this.author = desc.author;
        this.version = desc.version;
        this.logger = new PluginLogger(this);
    }

    /**
     * Get the plugin class loader
     * @return Plugin class loader
     */
    final public PluginClassLoader getClassLoader() {
        return this.classLoader;
    }

    /**
     * Used to enable the plugin
     */
    final public void enable() {
        if (this.enabled)
            throw new PluginException(this, "Plugin is already enabled");
        this.enabled = true;
        this.onEnable();
    }

    /**
     * Used to disable the plugin
     */
    final public void disable() {
        if (!this.enabled)
            throw new PluginException(this, "Plugin is not enabled");
        this.enabled = false;
        this.onDisable();
    }

    /**
     * Listen to enable
     */
    public void onEnable() {}

    /**
     * Listen to disable
     */
    public void onDisable() {}

    /**
     * Get the plugin name
     * @return Plugin name
     */
    final public String getName() {
        return this.name;
    }

    /**
     * Get the plugin version
     * @return Plugin version
     */
    public String getVersion() {
        return this.version;
    }

    /**
     * Get the plugin author
     * @return Plugin author
     */
    public String getAuthor() {
        return this.author;
    }

    /**
     * Check if the plugin is enabled
     * @return boolean (enabled)
     */
    public boolean isEnabled() {
        return this.enabled;
    }

    /**
     * Get the plugin uuid
     * @return UUID
     */
    final public UUID getUUID() {
        return this.uuid;
    }

    /**
     * Get the plugin data folder
     * @return Plugin data folder
     */
    public File getDataFolder() {
        return this.data;
    }

    /**
     * Get the plugin description file
     * @return Plugin Desc. file
     */
    public PluginFile getDesc() {
        return this.desc;
    }

    /**
     * Get the plugin logger
     * @return plugin logger
     */
    public PluginLogger getLogger() {
        return this.logger;
    }

    @Override
    public boolean equals(Object object) {
        if(object == null || !(object instanceof Plugin))
            return false;
        if(object == this)
            return true;
        Plugin plugin = (Plugin) object;
        return plugin.getUUID().equals(getUUID()) && getName().equals(plugin.getName());
    }

    @Override
    public int hashCode() {
        return 3 * uuid.hashCode() + 37 * getName().toLowerCase().hashCode();
    }

    @Override
    public String toString() {
        return this.name;
    }
}
