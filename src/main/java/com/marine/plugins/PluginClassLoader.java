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

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Set;

/**
 * Created 2014-12-10 for MarineStandalone
 *
 * @author Citymonstret
 */
public class PluginClassLoader extends URLClassLoader {

    private final PluginLoader loader;
    private final PluginFile desc;
    private final File file, data;
    private final BiMap<String, Class> classes = HashBiMap.create();
    public Plugin plugin, init;

    public PluginClassLoader(final PluginLoader loader, final PluginFile desc, final File file) throws MalformedURLException, PluginHandlerException {
        super(new URL[]{file.toURI().toURL()}, loader.getClass().getClassLoader());
        this.loader = loader;
        this.desc = desc;
        this.file = file;
        this.data = new File(file.getParent(), desc.name);
        Class jar;
        Class<? extends Plugin> plg;
        try {
            jar = Class.forName(desc.mainClass, true, this);
        } catch (ClassNotFoundException e) {
            throw new PluginHandlerException(this, "Could not find main class. Plugin: " + desc.name + ", Main class: " + desc.mainClass);
        }
        try {
            plg = jar.asSubclass(Plugin.class);
        } catch (ClassCastException e) {
            throw new PluginHandlerException(this, "Plugin main class for " + desc.name + " is not an instance of Plugin");
        }
        try {
            this.plugin = plg.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        return this.findClass(name, true);
    }

    protected Class<?> findClass(String name, boolean global) throws ClassNotFoundException {
        Class<?> clazz = null;
        if (classes.containsKey(name)) {
            clazz = classes.get(name);
        }
        if (clazz == null) {
            if (global)
                clazz = loader.getClassByName(name);
            if (clazz == null) {
                clazz = super.findClass(name);
                if (clazz != null)
                    loader.setClass(name, clazz);
            }
            classes.put(name, clazz);
        }
        return clazz;
    }

    protected Set<String> getClasses() {
        return classes.keySet();
    }

    synchronized void create(Plugin plugin) {
        if (this.init != null)
            throw new RuntimeException(plugin.getName() + " is already created");
        this.init = plugin;
        plugin.create(desc, data, this);
    }
}
