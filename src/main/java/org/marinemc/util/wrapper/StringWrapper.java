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

package org.marinemc.util.wrapper;

/**
 * A string wrapper,
 * basically an improved string
 *
 * @author Empire92
 */
public class StringWrapper {

    public final String value;

    /**
     * Constructor
     *
     * @param value to wrap
     */
    public StringWrapper(final String value) {
        this.value = value;
    }

    /**
     * Check if a wrapped string equals another one
     *
     * @param obj to compare
     * @return true if obj equals the stored value
     */
    @Override
    public boolean equals(final Object obj) {
        return (this == obj) &&
                (obj != null) &&
                (getClass() == obj.getClass()) &&
                (obj instanceof StringWrapper) &&
                ((StringWrapper) obj).value.toLowerCase().equals(this.value.toLowerCase());
    }

    /**
     * Get the string value
     *
     * @return string value
     */
    @Override
    public String toString() {
        return this.value;
    }

    /**
     * Get the hash value
     *
     * @return has value
     */
    @Override
    public int hashCode() {
        return this.value.toLowerCase().hashCode();
    }
}