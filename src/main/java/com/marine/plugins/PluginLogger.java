///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// MarineStandalone is a minecraft server software and API.
// Copyright (C) IntellectualSites (marine.intellectualsites.com)
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
