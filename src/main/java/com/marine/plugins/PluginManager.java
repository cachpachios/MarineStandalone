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

import java.util.ArrayList;
import java.util.Collection;
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

    public Collection<Plugin> getPlugins() {
        return this.plugins;
    }

    public Collection<Plugin> getEnabledPlugins() {
        final Collection<Plugin> plugins = new ArrayList<>();
        for (final Plugin plugin : this.plugins) {
            if (plugin.isEnabled())
                plugins.add(plugin);
        }
        return plugins;
    }
}
