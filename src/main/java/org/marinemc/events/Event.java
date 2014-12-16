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

/**
 * Created 2014-12-16 for MarineStandalone
 *
 * @author Citymonstret
 */
public abstract class Event {

    private final String name;
    private final boolean async;

    public Event(final String name) {
        this(name, false);
    }

    public Event(final String name, final boolean async) {
        this.name = name;
        this.async = async;
    }

    @Override
    public String toString() {
        return this.getClass().getName();
    }

    public String getName() {
        return this.name;
    }

    @Override
    public int hashCode() {
        return this.name.hashCode();
    }

    @Override
    public boolean equals(final Object o) {
        return o instanceof Event && ((Event) o).getName().equals(this.getName());
    }

    public boolean async() {
        return this.async;
    }
}
