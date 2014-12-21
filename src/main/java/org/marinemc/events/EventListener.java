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

import java.lang.reflect.ParameterizedType;

/**
 * Event Listener Class
 *
 * @author Citymonstret
 */
public abstract class EventListener<T extends Event> {

    private final String listenTo;
    private final int accessor;
    private final EventPriority priority;
    private Object y = null;

    public EventListener() {
        this(EventPriority.MEDIUM);
    }

    public EventListener(final EventPriority priority) {
        this.listenTo = ((Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0]).getName();
        this.accessor = listenTo.hashCode();
        this.priority = priority;
    }

    public EventPriority getPriority() {
        return this.priority;
    }

    public abstract void listen(final T t);

    // USED IN INTERNAL METHODS

    final public void setIDENTIFIERObject__DO_NOT_USE__(final Object q) {
        y = q;
    }

    final public Object getIDENTIFIERObject() {
        return this.y;
    }

    final public String listeningTo() {
        return this.listenTo;
    }

    @Override
    final public int hashCode() {
        return accessor;
    }
}
