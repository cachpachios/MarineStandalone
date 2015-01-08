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

package org.marinemc.events;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.marinemc.Bootstrap;
import org.marinemc.logging.Logging;
import org.marinemc.plugins.Plugin;

/**
 * Event Manager
 *
 * @author Citymonstret
 */
public class EventManager {

	private static EventManager instance;
	private final Map<Integer, ArrayDeque<EventListener>> listeners;
	private Map<Integer, EventListener[]> bakedListeners;

	/**
	 * Constructor
	 */
	public EventManager() {
		listeners = new HashMap<>();
	}

	/**
	 * Get the static instance
	 *
	 * @return THIS, literally THIS
	 */
	public static EventManager getInstance() {
		if (instance == null)
			instance = new EventManager();
		return instance;
	}

	/**
	 * Remove all listeners registered by a plugin
	 *
	 * @param plugin
	 *            Plugin for which the listeners should be removed
	 */
	public void removeAll(final Plugin plugin) {
		synchronized (listeners) {
			for (final Deque<EventListener> listeners : this.listeners.values())
				for (final EventListener listener : listeners)
					if (listener.getIDENTIFIERObject() instanceof Plugin
							&& listener.getIDENTIFIERObject().equals(plugin))
						listeners.remove(listener);
			bake();
		}
	}

	/**
	 * Add a listener
	 *
	 * @param listener
	 *            listener to add
	 */
	public void addListener(final EventListener listener) {
		synchronized (listeners) {
			if (!listeners.containsKey(listener.hashCode()))
				listeners.put(listener.hashCode(),
						new ArrayDeque<EventListener>());
			listeners.get(listener.hashCode()).add(listener);
		}
	}

	/**
	 * Remove a listener
	 *
	 * @param listener
	 *            listener to remove
	 */
	public void removeListener(final EventListener listener) {
		synchronized (listeners) {
			for (final Deque<EventListener> ll : listeners.values())
				ll.remove(listener);
		}
	}

	/**
	 * Handle an event
	 *
	 * @param event
	 *            event to be handled
	 */
	public void handle(final Event event) {
		if (event.async())
			call(event);
		else
			synchronized (this) {
				call(event);
			}
	}

	/**
	 * Bake the listeners...
	 */
	public void bake() {
		Logging.getLogger().log("Baking Event Listeners...");
		synchronized (this) {
			bakedListeners = new HashMap<>();
			List<EventListener> low, med, hig;
			int index;
			EventListener[] array;
			for (final Map.Entry<Integer, ArrayDeque<EventListener>> entry : listeners
					.entrySet()) {
				low = new ArrayList<>();
				med = new ArrayList<>();
				hig = new ArrayList<>();
				for (final EventListener listener : entry.getValue())
					switch (listener.getPriority()) {
					case LOW:
						low.add(listener);
						break;
					case MEDIUM:
						low.add(listener);
						break;
					case HIGH:
						hig.add(listener);
						break;
					default:
						break;
					}
				array = new EventListener[low.size() + med.size() + hig.size()];
				index = 0;
				for (final EventListener listener : low)
					array[index++] = listener;
				for (final EventListener listener : med)
					array[index++] = listener;
				for (final EventListener listener : hig)
					array[index++] = listener;
				bakedListeners.put(entry.getKey(), array);
			}
		}
		if (Bootstrap.debug()) {
			for (final Map.Entry<Integer, EventListener[]> entry : bakedListeners
					.entrySet()) {
				Logging.getLogger().debug("Listeners for " + entry.getKey());
				int index = 0;
				for (final EventListener listener : entry.getValue())
					Logging.getLogger().debug(
							"[" + ++index + "] Listing to: "
									+ listener.listeningTo() + ", Class: "
									+ listener.getClass());
			}
			Logging.getLogger().debug("Baked!");
		}
	}

	@SuppressWarnings("ALL")
	private void call(final Event event) throws NullPointerException {
		if (bakedListeners == null
				|| !bakedListeners.containsKey(event.hashCode()))
			return;
		for (final EventListener listener : bakedListeners
				.get(event.hashCode()))
			listener.listen(event);
	}

}
