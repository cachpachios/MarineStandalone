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
import org.marinemc.game.system.MarineSecurityManager;
import org.marinemc.util.FileUtils;
import sun.misc.JarFilter;

import java.io.*;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * The plugin loader - CANNOT BE ACCESS BY PLUGINS ITSELF
 *
 * @author Citymonstret
 */
public class PluginLoader {

    // SECURITY CHECK START ////////////////////////////////////////////////////////////////////////////////////////////
    static {
        System.getSecurityManager().checkPermission(MarineSecurityManager.MARINE_PERMISSION);
    }

    /* Instance */ {
        System.getSecurityManager().checkPermission(MarineSecurityManager.MARINE_PERMISSION);
    }
    // SECURITY CHECK END //////////////////////////////////////////////////////////////////////////////////////////////

    private final ConcurrentMap<String, PluginClassLoader> loaders;
    private final ConcurrentMap<String, Class> classes;
    private final PluginManager manager;
    private final String[] BLOCKED_NAMES = new String[]{
            "org.marinemc", "com.intellectualsites.marinemc"
    };

    /**
     * Constructor
     *
     * @param manager Related PluginManager
     */
    public PluginLoader(PluginManager manager) {
        // Security Check Start
        System.getSecurityManager().checkPermission(MarineSecurityManager.MARINE_PERMISSION);
        // Security Check End
        this.manager = manager;
        this.loaders = new ConcurrentHashMap<>();
        this.classes = new ConcurrentHashMap<>();
    }

    /**
     * Get the related plugin manager
     *
     * @return Plugin Manager specified in the constructor
     */
    public PluginManager getManager() {
        return this.manager;
    }

    private void checkIllegal(final PluginFile desc) {
        final String main = desc.mainClass;
        for (final String blocked : BLOCKED_NAMES)
            if (main.contains(blocked))
                throw new PluginHandlerException(this, "Plugin " + desc.name + " contains illegal main class path");
    }

    /**
     * Load all plugins
     *
     * @param folder Folder to load from
     * @throws PluginHandlerException if the folder doesn't exist
     */
    public void loadAllPlugins(File folder) throws PluginHandlerException {
        if (!folder.exists() || !folder.isDirectory()) {
            throw new PluginHandlerException(this, "Invalid plugin folder (doesn't exist)");
        }
        final File[] files = folder.listFiles(new JarFilter());
        for (final File file : files) {
            PluginClassLoader loader;
            try {
                loader = loadPlugin(file);
                // Make sure the path name is valid
                checkIllegal(loader.getDesc());
                loader.create(loader.plugin);
                manager.addPlugin(loader.plugin);
                if (new File(loader.getData(), "lib").exists()) {
                    File[] fs = new File(loader.getData(), "lib").listFiles(new JarFilter());
                    for (File f : fs) {
                        try {
                            loader.loadJar(f);
                        } catch (Exception e) {
                            new PluginHandlerException(this, "Could not load in lib " + f.getName(), e).printStackTrace();
                        }
                    }
                }
            } catch (PluginHandlerException e) {
                Logging.getLogger().log("Could not load in plugin: " + file.getName());
                e.printStackTrace();
            }
        }
    }

    /**
     * Enable all plugins
     */
    public void enableAllPlugins() {
        for (final Plugin plugin : manager.getPlugins()) {
            enablePlugin(plugin);
        }
    }

    /**
     * Disable all plugins
     */
    public void disableAllPlugins() {
        for (Plugin plugin : manager.getPlugins()) {
            try {
                disablePlugin(plugin);
            } catch (Exception e) {
                new PluginHandlerException(this, "Could not properly disable " + plugin.getName(), e).printStackTrace();
            }
        }
    }

    /**
     * Load a specific plugin
     *
     * @param file Jar File
     * @return Plugin Loaded
     * @throws PluginHandlerException If the file doesn't exists
     * @throws PluginHandlerException If the PluginClassLoader is unable to be loaded
     */
    public PluginClassLoader loadPlugin(final File file) throws PluginHandlerException {
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
        return loader;
    }

    /**
     * Get a class based on it's name
     *
     * @param name Of the class
     * @return Class if found, else null
     */
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

    /**
     * Set a class (name) to a class (object)
     *
     * @param name  Class Name
     * @param clazz Class Object
     */
    protected void setClass(final String name, final Class clazz) {
        if (!classes.containsKey(name)) {
            classes.put(name, clazz);
        }
    }

    /**
     * Remove a class based on its name
     *
     * @param name Class Name
     */
    protected void removeClass(final String name) {
        if (classes.containsKey(name))
            classes.remove(name);
    }

    /**
     * Enable a plugin
     *
     * @param plugin Plugin to enable
     */
    public void enablePlugin(final Plugin plugin) {
        if (!plugin.isEnabled()) {
            String name = plugin.getName();
            if (!loaders.containsKey(name))
                loaders.put(name, plugin.getClassLoader());
            manager.enablePlugin(plugin);
            plugin.getLogger().log(plugin.getName() + " is enabled");
        }
    }

    /**
     * Disable a plugin
     *
     * @param plugin Plugin to disable
     */
    public void disablePlugin(final Plugin plugin) {
        if (plugin.isEnabled()) {
            manager.disablePlugin(plugin);
            loaders.remove(plugin.getName());
            PluginClassLoader loader = plugin.getClassLoader();
            for (final String name : loader.getClasses())
                removeClass(name);
        }
    }

    /**
     * Copy the config and lib files from the plugin jar
     * to a specified data folder
     *
     * @param file        Jar File
     * @param destination Destination Folder
     * @throws PluginHandlerException If jar file cannot be loaded
     */
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

    /**
     * Get a plugin description file from a jar file
     *
     * @param file Jar File
     * @return Plugin Description file, or null (if it cannot be found)
     * @throws PluginHandlerException If the file is unable to be loaded
     * @throws PluginHandlerException If the file doesn't exist
     * @throws PluginHandlerException If the stream is unable to be loaded
     * @throws PluginHandlerException If a file cannot be loaded from the stream
     */
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
