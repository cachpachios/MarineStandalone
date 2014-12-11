package com.marine.plugins;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.marine.Logging;
import sun.misc.JarFilter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
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

    public void loadAllPlugins(File folder) {
        if (!folder.exists() || !folder.isDirectory()) {
            throw new IllegalArgumentException("You have to provide a valid plugin folder");
        }
        File[] files = folder.listFiles(new JarFilter());
        for (File file : files) {
            try {
                loadPlugin(file);
            } catch (Throwable e) {
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

    public Plugin loadPlugin(final File file) throws Throwable {
        if (!file.exists())
            throw new FileNotFoundException("Could not load plugin -> File cannot be null");
        final PluginFile desc = getPluginFile(file);
        final File parent = file.getParentFile(), data = new File(parent, desc.name);
        if (!data.exists()) {
            if (!data.mkdir()) {
                Logging.getLogger().warn("Could not create data folder for " + desc.name);
            }
        }
        PluginClassLoader loader = new PluginClassLoader(this, desc, file);
        loaders.put(desc.name, loader);
        loader.create(loader.plugin);
        manager.addPlugin(loader.plugin);
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

    private PluginFile getPluginFile(File file) throws Throwable {
        JarFile jar = new JarFile(file);
        JarEntry desc = jar.getJarEntry("desc.json");
        if (desc == null)
            throw new RuntimeException("Could not find desc.json in file: " + file.getName());
        InputStream stream = jar.getInputStream(desc);
        PluginFile f = new PluginFile(stream);
        try {
            jar.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return f;
    }
}
