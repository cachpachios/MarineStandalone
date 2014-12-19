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
 * Created 2014-12-16 for MarineStandalone
 *
 * @author Citymonstret
 */
public abstract class EventListener<T extends Event> {

    private final String listenTo;
    private final int accessor;
    private Object y = null;

    public EventListener() {
        this.listenTo = ((Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0]).getName();
        this.accessor = listenTo.hashCode();
    }

    public void setIDENTIFIERObject__DO_NOT_USE__(final Object q) {
        y = q;
    }

    public abstract void listen(T t);

    public Object getIDENTIFIERObject() {
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
