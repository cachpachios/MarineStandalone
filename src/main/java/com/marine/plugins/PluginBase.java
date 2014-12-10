package com.marine.plugins;

import java.io.File;

/**
 * Created 2014-12-10 for MarineStandalone
 *
 * @author Citymonstret
 */
public class PluginBase {

    public final String name;
    public final String author;
    public final String version;
    public final File data;
    public final PluginFile desc;
    public final PluginClassLoader classLoader;
    public final PluginLogger logger;

    public PluginBase(final String name, final String author, final String version, final File data, final PluginFile desc,
        final PluginClassLoader classLoader, final PluginLogger logger) {
        this.name = name;
        this.author = author;
        this.version = version;
        this.data = data;
        this.desc = desc;
        this.classLoader = classLoader;
        this.logger = logger;
    }
}
