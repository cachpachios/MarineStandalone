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
 * Exception thrown by the plugin internals when something
 * that shouldn't occur, occurs.
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
