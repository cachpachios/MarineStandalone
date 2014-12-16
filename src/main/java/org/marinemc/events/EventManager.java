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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created 2014-12-16 for MarineStandalone
 *
 * @author Citymonstret
 */
public class EventManager {

    private static EventManager instance;
    public final Map<String, ArrayList<EventListener>> listeners;

    public EventManager() {
        this.listeners = new HashMap<>();
    }

    public static EventManager getInstance() {
        if (instance == null)
            instance = new EventManager();
        return instance;
    }

    public void addListener(final EventListener listener) {
        synchronized (listeners) {
            if (!listeners.containsKey(listener.listeningTo())) {
                listeners.put(listener.listeningTo(), new ArrayList<EventListener>());
            }
            listeners.get(listener.listeningTo()).add(listener);
        }
    }

    public void removeListener(final EventListener listener) {
        synchronized (listeners) {
            for (ArrayList<EventListener> ll : listeners.values()) {
                ll.remove(listener);
            }
        }
    }

    public void handle(final Event event) {
        if (event.async()) {
            call(event);
        } else {
            synchronized (this) {
                call(event);
            }
        }
    }

    private void call(final Event event) {
        if (!listeners.containsKey(event.toString())) return;
        for (final EventListener listener : listeners.get(event.toString())) {
            listener.listen(event);
        }
    }
}
