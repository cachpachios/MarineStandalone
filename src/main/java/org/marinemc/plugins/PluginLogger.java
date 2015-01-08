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

import org.marinemc.logging.Logging;

/**
 * The logger used in plugins
 *
 * @author Citymonstret
 */
public class PluginLogger {

	private final Plugin plugin;

	/**
	 * The plugin to init with
	 *
	 * @param plugin
	 *            Plugin
	 */
	public PluginLogger(final Plugin plugin) {
		this.plugin = plugin;
	}

	/**
	 * Log a string to the global logger
	 *
	 * @param message
	 *            Message to log
	 */
	final public void log(final String message) {
		Logging.getLogger().log(constructMessage(message));
	}

	/**
	 * Construct a plugin message
	 *
	 * @param message
	 *            Message
	 * @return Constructed message
	 */
	private PluginMessage constructMessage(final String message) {
		return new PluginMessage(String.format("[%s] %s", plugin.getName(),
				message), this);
	}

	/**
	 * Get the plugin for this logger
	 *
	 * @return plugin
	 */
	final public Plugin getPlugin() {
		return plugin;
	}

	public static class PluginMessage {

		private final String message;
		private final PluginLogger logger;

		/**
		 * Construct a new message
		 *
		 * @param message
		 *            Message
		 * @param logger
		 *            Logger
		 */
		public PluginMessage(final String message, final PluginLogger logger) {
			this.message = message;
			this.logger = logger;
		}

		/**
		 * Get the message
		 *
		 * @return
		 */
		public String getMessage() {
			return message;
		}

		/**
		 * Get the logger
		 *
		 * @return logger
		 */
		public PluginLogger getLogger() {
			return logger;
		}
	}
}
