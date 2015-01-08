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

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * A custom class loader used with plugins
 *
 * @author Citymonstret
 */
public class PluginClassLoader extends URLClassLoader {

	private final PluginLoader loader;
	private final PluginFile desc;
	private final File file, data;
	private final Map<String, Class> classes;
	public Plugin plugin, init;

	/**
	 * Constructor
	 *
	 * @param loader
	 *            PluginLoader Instance
	 * @param desc
	 *            PluginFile For the plugin
	 * @param file
	 *            Plugin Jar
	 * @throws MalformedURLException
	 *             If the jar location is invalid
	 * @throws PluginHandlerException
	 *             If the Main class is not in the specified location
	 * @throws PluginHandlerException
	 *             If the Main class is not an instanceof the Plugin class
	 */
	public PluginClassLoader(final PluginLoader loader, final PluginFile desc,
			final File file) throws MalformedURLException,
			PluginHandlerException {
		super(new URL[] { file.toURI().toURL() }, loader.getClass()
				.getClassLoader());
		this.loader = loader;
		this.desc = desc;
		this.file = file;
		data = new File(file.getParent(), desc.name);
		classes = new HashMap<>();
		Class jar;
		Class<? extends Plugin> plg;
		try {
			jar = Class.forName(desc.mainClass, true, this);
		} catch (final ClassNotFoundException e) {
			throw new PluginHandlerException(this,
					"Could not find main class. Plugin: " + desc.name
							+ ", Main class: " + desc.mainClass);
		}
		try {
			plg = jar.asSubclass(Plugin.class);
		} catch (final ClassCastException e) {
			throw new PluginHandlerException(this, "Plugin main class for "
					+ desc.name + " is not an instance of Plugin");
		}
		try {
			plugin = plg.newInstance();
		} catch (InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
		}
	}

	public File getData() {
		return data;
	}

	/**
	 * Load a jar file into [this] instance
	 *
	 * @param file
	 *            Jar file
	 * @throws PluginHandlerException
	 *             If the file is not a jar file
	 * @throws PluginHandlerException
	 *             If the file cannot be loaded
	 */
	public void loadJar(final File file) throws PluginHandlerException {
		if (!file.getName().endsWith(".jar"))
			throw new PluginHandlerException(this, file.getName()
					+ " is not a jar file", new IllegalArgumentException(
					file.getName() + " is of wrong type"));
		try {
			super.addURL(file.toURI().toURL());
		} catch (final MalformedURLException e) {
			throw new PluginHandlerException(this, "Could not load jar", e);
		}
	}

	@Override
	protected Class<?> findClass(final String name)
			throws ClassNotFoundException {
		return this.findClass(name, true);
	}

	protected Class<?> findClass(final String name, final boolean global)
			throws ClassNotFoundException {
		Class<?> clazz = null;
		if (classes.containsKey(name))
			clazz = classes.get(name);
		else {
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

	synchronized void create(final Plugin plugin) {
		if (init != null)
			throw new RuntimeException(plugin.getName() + " is already created");
		init = plugin;
		plugin.create(desc, data, this);
	}

	public PluginFile getDesc() {
		return desc;
	}
}
