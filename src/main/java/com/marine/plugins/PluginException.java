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

/**
 * Exception thrown by plugins
 *
 * @author Citymonstret
 */
public class PluginException extends RuntimeException {

    public PluginException(final Plugin plugin, final String message) {
        super(String.format("Plugin %s caused an error -> %s", plugin.getName(), message));
    }

    public PluginException(final Plugin plugin, final String message, final Throwable cause) {
        super(String.format("Plugin %s caused an error -> %s", plugin.getName(), message), cause);
    }
}
