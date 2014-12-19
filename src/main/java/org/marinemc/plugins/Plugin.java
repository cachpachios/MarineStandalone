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

package org.marinemc.plugins;

import org.marinemc.Logging;
import org.marinemc.events.EventListener;
import org.marinemc.events.EventManager;
import org.marinemc.game.CommandManager;
import org.marinemc.game.chat.ChatColor;
import org.marinemc.game.command.Command;
import org.marinemc.game.command.CommandProvider;

import java.io.File;
import java.util.UUID;

/**
 * Plugins will need to implement this class in order
 * to be loaded.
 * <p/>
 * Use the methods in here, rather than any external
 * methods that do the same thing, as these are optimized
 * for use in plugins, and will make sure that nothing
 * breaks.
 *
 * @author Citymonstret
 */
public class Plugin implements CommandProvider {

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
     *
     * @return Plugin class loader
     */
    final public PluginClassLoader getClassLoader() {
        return this.classLoader;
    }

    /**
     * Used to enable the plugin
     *
     * @throws PluginException If the plugin is already enabled, or if couldn't be enabled
     */
    final public void enable() {
        if (this.enabled)
            throw new PluginException(this, "Plugin is already enabled");
        try {
            this.enabled = true;
            this.onEnable();
        } catch (Exception e) {
            this.enabled = false;
            Logging.getLogger().error("Could not enable " + ChatColor.RED + getName() + ChatColor.WHITE + ", see stacktrace for more info");
            throw new PluginException(this, "Could not enable plugin", e);
        }
    }

    /**
     * Used to disable the plugin
     *
     * @throws PluginException if it isn't enabled
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
    public void onEnable() {
    }

    /**
     * Listen to disable
     */
    public void onDisable() {
    }

    /**
     * Get the plugin name
     *
     * @return Plugin name
     */
    final public String getName() {
        return this.name;
    }

    /**
     * Get the plugin version
     *
     * @return Plugin version
     */
    public String getVersion() {
        return this.version;
    }

    /**
     * Get the plugin author
     *
     * @return Plugin author
     */
    public String getAuthor() {
        return this.author;
    }

    /**
     * Check if the plugin is enabled
     *
     * @return boolean (enabled)
     */
    public boolean isEnabled() {
        return this.enabled;
    }

    /**
     * Get the plugin uuid
     *
     * @return UUID
     */
    final public UUID getUUID() {
        return this.uuid;
    }

    /**
     * Get the plugin data folder
     *
     * @return Plugin data folder
     */
    public File getDataFolder() {
        return this.data;
    }

    /**
     * Get the plugin description file
     *
     * @return Plugin Desc. file
     */
    public PluginFile getDesc() {
        return this.desc;
    }

    /**
     * Get the plugin logger
     *
     * @return plugin logger
     */
    public PluginLogger getLogger() {
        return this.logger;
    }

    @Override
    public boolean equals(Object object) {
        return object instanceof Plugin && ((Plugin) object).getUUID().equals(getUUID());
    }

    @Override
    public int hashCode() {
        return 3 * uuid.hashCode() + 37 * getName().toLowerCase().hashCode();
    }

    @Override
    public String toString() {
        return this.name;
    }

    @Override
    final public String getProviderName() {
        return getName().toLowerCase().replace(" ", "");
    }

    @Override
    final public byte getProviderPriority() {
        return (byte) 0x01;
    }

    /**
     * Register a command using this instance as the
     * provider
     *
     * @param command Command
     */
    final public void addCommand(Command command) {
        CommandManager.getInstance().registerCommand(this, command);
    }

    /**
     * Register a listener using this
     * instance as the provider
     *
     * @param listener Listener to register
     */
    public void registerListener(final EventListener listener) {
        listener.setIDENTIFIERObject__DO_NOT_USE__(this);
        EventManager.getInstance().addListener(listener);
    }
}
