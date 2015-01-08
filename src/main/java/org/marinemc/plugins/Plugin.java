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

import java.io.File;
import java.util.UUID;

import org.marinemc.events.EventListener;
import org.marinemc.events.EventManager;
import org.marinemc.game.CommandManager;
import org.marinemc.game.chat.ChatColor;
import org.marinemc.game.command.Command;
import org.marinemc.game.command.ServiceProvider;
import org.marinemc.logging.Logging;
import org.marinemc.util.Assert;

/**
 * Plugins will need to implement this class in order to be loaded.
 * <p/>
 * Use the methods in here, rather than any external methods that do the same
 * thing, as these are optimized for use in plugins, and will make sure that
 * nothing breaks.
 *
 * @author Citymonstret
 */
public class Plugin implements ServiceProvider {

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
		uuid = UUID.randomUUID();
		enabled = false;
	}

	final public void create(final PluginFile desc, final File data,
			final PluginClassLoader classLoader) {
		Assert.notNull(desc, data, classLoader);
		if (this.desc != null)
			throw new RuntimeException("Plugin already created: " + desc.name);
		this.desc = desc;
		this.classLoader = classLoader;
		name = desc.name;
		this.data = data;
		author = desc.author;
		version = desc.version;
		logger = new PluginLogger(this);
	}

	/**
	 * Get the plugin class loader
	 *
	 * @return Plugin class loader
	 */
	final public PluginClassLoader getClassLoader() {
		return classLoader;
	}

	/**
	 * Used to enable the plugin
	 *
	 * @throws PluginException
	 *             If the plugin is already enabled, or if couldn't be enabled
	 */
	final public void enable() {
		if (enabled)
			throw new PluginException(this, "Plugin is already enabled");
		try {
			enabled = true;
			onEnable();
		} catch (final Exception e) {
			enabled = false;
			Logging.getLogger().error(
					"Could not enable " + ChatColor.RED + getName()
							+ ChatColor.WHITE
							+ ", see stacktrace for more info");
			throw new PluginException(this, "Could not enable plugin", e);
		}
	}

	/**
	 * Used to disable the plugin
	 *
	 * @throws PluginException
	 *             if it isn't enabled
	 */
	final public void disable() {
		if (!enabled)
			throw new PluginException(this, "Plugin is not enabled");
		enabled = false;
		onDisable();
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
		return name;
	}

	/**
	 * Get the plugin version
	 *
	 * @return Plugin version
	 */
	public String getVersion() {
		return version;
	}

	/**
	 * Get the plugin author
	 *
	 * @return Plugin author
	 */
	public String getAuthor() {
		return author;
	}

	/**
	 * Check if the plugin is enabled
	 *
	 * @return boolean (enabled)
	 */
	public boolean isEnabled() {
		return enabled;
	}

	/**
	 * Get the plugin uuid
	 *
	 * @return UUID
	 */
	final public UUID getUUID() {
		return uuid;
	}

	/**
	 * Get the plugin data folder
	 *
	 * @return Plugin data folder
	 */
	public File getDataFolder() {
		return data;
	}

	/**
	 * Get the plugin description file
	 *
	 * @return Plugin Desc. file
	 */
	public PluginFile getDesc() {
		return desc;
	}

	/**
	 * Get the plugin logger
	 *
	 * @return plugin logger
	 */
	public PluginLogger getLogger() {
		return logger;
	}

	@Override
	public boolean equals(final Object object) {
		return object instanceof Plugin
				&& ((Plugin) object).getUUID().equals(getUUID());
	}

	@Override
	public int hashCode() {
		return 3 * uuid.hashCode() + 37 * getName().toLowerCase().hashCode();
	}

	@Override
	public String toString() {
		return name;
	}

	@Override
	final public String getProviderName() {
		return getName().toLowerCase().replace(" ", "");
	}

	@Override
	final public byte getProviderPriority() {
		return 0x01;
	}

	/**
	 * Register a command using this instance as the provider
	 *
	 * @param command
	 *            Command
	 */
	final public void addCommand(final Command command) {
		Assert.notNull(command);
		CommandManager.getInstance().registerCommand(this, command);
	}

	/**
	 * Register a listener using this instance as the provider
	 *
	 * @param listener
	 *            Listener to register
	 */
	final public void registerListener(final EventListener listener) {
		Assert.notNull(listener);
		listener.setIDENTIFIERObject__DO_NOT_USE__(this);
		EventManager.getInstance().addListener(listener);
	}

}
