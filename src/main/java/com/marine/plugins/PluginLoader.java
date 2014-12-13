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
import com.marine.Logging;
import com.marine.util.FileUtils;
import sun.misc.JarFilter;

import java.io.*;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * Created 2014-12-10 for MarineStandalone
 *
 * @author Citymonstret
 */
public class PluginLoader {

    private final BiMap<String, PluginClassLoader> loaders = HashBiMap.create();
    private final BiMap<String, Class> classes = HashBiMap.create();
    private final PluginManager manager;

    public PluginLoader(PluginManager manager) {
        this.manager = manager;
    }

    public PluginManager getManager() {
        return this.manager;
    }

    public void loadAllPlugins(File folder) throws PluginHandlerException {
        if (!folder.exists() || !folder.isDirectory()) {
            throw new PluginHandlerException(this, "Invalid plugin folder (doesn't exist)");
        }
        File[] files = folder.listFiles(new JarFilter());
        for (File file : files) {
            try {
                loadPlugin(file);
            } catch (PluginHandlerException e) {
                Logging.getLogger().log("Could not load in plugin: " + file.getName());
                e.printStackTrace();
            }
        }
    }

    public void enableAllPlugins() {
        for (Plugin plugin : manager.getPlugins()) {
            enablePlugin(plugin);
        }
    }

    public void disableAllPlugins() {
        for (Plugin plugin : manager.getPlugins()) {
            try {
                disablePlugin(plugin);
            } catch (Exception e) {
                new PluginHandlerException(this, "Could not properly disable " + plugin.getName(), e).printStackTrace();
            }
        }
    }

    public Plugin loadPlugin(final File file) throws PluginHandlerException {
        if (!file.exists())
            throw new PluginHandlerException(this, "Could not load plugin -> File cannot be null", new FileNotFoundException(file.getPath() + " does not exist"));
        final PluginFile desc = getPluginFile(file);
        final File parent = file.getParentFile(), data = new File(parent, desc.name);
        if (!data.exists()) {
            if (!data.mkdir()) {
                Logging.getLogger().warn("Could not create data folder for " + desc.name);
            }
        }
        copyConfigIfExists(file, data);
        PluginClassLoader loader;
        try {
            loader = new PluginClassLoader(this, desc, file);
        } catch (MalformedURLException e) {
            throw new PluginHandlerException(this, "Could not get the PluginClassLoader", e);
        }
        loaders.put(desc.name, loader);
        loader.create(loader.plugin);
        manager.addPlugin(loader.plugin);
        if (new File(data, "lib").exists()) {
            File[] files = new File(data, "lib").listFiles(new JarFilter());
            for (File f : files) {
                try {
                    loader.loadJar(f);
                } catch (Exception e) {
                    new PluginHandlerException(this, "Could not load in lib " + f.getName(), e).printStackTrace();
                }
            }
        }
        return loader.plugin;
    }

    protected Class<?> getClassByName(final String name) {
        if (classes.containsKey(name))
            return classes.get(name);
        Class clazz;
        PluginClassLoader loader;
        for (String current : loaders.keySet()) {
            loader = loaders.get(current);
            try {
                if ((clazz = loader.findClass(name, false)) != null)
                    return clazz;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    protected void setClass(final String name, final Class clazz) {
        if (!classes.containsKey(name)) {
            classes.put(name, clazz);
        }
    }

    protected void removeClass(String name) {
        if (classes.containsKey(name))
            classes.remove(name);
    }

    public void enablePlugin(final Plugin plugin) {
        if (!plugin.isEnabled()) {
            String name = plugin.getName();
            if (!loaders.containsKey(name))
                loaders.put(name, plugin.getClassLoader());
            manager.enablePlugin(plugin);
        }
    }

    public void disablePlugin(final Plugin plugin) {
        if (plugin.isEnabled()) {
            manager.disablePlugin(plugin);
            loaders.remove(plugin.getName());
            PluginClassLoader loader = plugin.getClassLoader();
            for (String name : loader.getClasses())
                removeClass(name);
        }
    }

    private void copyConfigIfExists(File file, File destination) throws PluginHandlerException {
        JarFile jar;
        try {
            jar = new JarFile(file);
        } catch (IOException ioe) {
            throw new PluginHandlerException(this, "Could not load in " + file.getName(), ioe);
        }
        Enumeration<JarEntry> entries = jar.entries();
        JarEntry entry;
        List<JarEntry> entryList = new ArrayList<>();
        while (entries.hasMoreElements()) {
            entry = entries.nextElement();
            if (!entry.getName().equalsIgnoreCase("desc.json") && (entry.getName().endsWith(".json") || entry.getName().endsWith(".jar") ||
                    entry.getName().endsWith(".properties") || entry.getName().endsWith(".sql") || entry.getName().endsWith(".db"))) {
                entryList.add(entry);
            }
        }
        for (JarEntry e : entryList) {
            if (!e.getName().endsWith(".jar")) {
                if (new File(destination, e.getName()).exists())
                    continue;
                try {
                    FileUtils.copyFile(jar.getInputStream(e),
                            new BufferedOutputStream(new FileOutputStream(new File(destination, e.getName()))), 1024 * 1024 * 5);
                } catch (IOException exz) {
                    new PluginHandlerException(this, "Could not load in entry...", exz).printStackTrace();
                }
            } else {
                File lib = new File(destination, "lib");
                if (!lib.exists()) {
                    if (!lib.mkdir()) {
                        Logging.getLogger().error("Could not create " + lib.getPath());
                        continue;
                    }
                }
                if (new File(lib, e.getName()).exists())
                    continue;
                try {
                    FileUtils.copyFile(jar.getInputStream(e),
                            new BufferedOutputStream(new FileOutputStream(new File(lib, e.getName()))), 1024 * 1024 * 5);
                } catch (IOException exz) {
                    new PluginHandlerException(this, "Could not copy jar file", exz).printStackTrace();
                }
            }
        }
    }

    private PluginFile getPluginFile(File file) throws PluginHandlerException {
        JarFile jar;
        try {
            jar = new JarFile(file);
        } catch (IOException ioe) {
            throw new PluginHandlerException(this, "Could not load in " + file.getName(), ioe);
        }
        JarEntry desc = jar.getJarEntry("desc.json");
        if (desc == null)
            throw new PluginHandlerException(this, "Could not find desc.json in file: " + file.getName());
        InputStream stream;
        try {
            stream = jar.getInputStream(desc);
        } catch (IOException ioe) {
            throw new PluginHandlerException(this, "Could not get stream for desc.json", ioe);
        }
        PluginFile f;
        try {
            f = new PluginFile(stream);
        } catch (Exception e) {
            throw new PluginHandlerException(this, "Could not load in plugin file from stream", e);
        }
        try {
            jar.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return f;
    }
}
