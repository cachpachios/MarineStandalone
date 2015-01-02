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

package org.marinemc.util.vectors;

/**
 * Vector interface
 *
 * @author Citymonstret
 * @author Fozie
 * 
 */
public interface Vector<T extends Number> {

    /**
     * Divide all values by a factor
     *
     * @param factor Factor to divide by
     */
    public void divide(final int factor);

    /**
     * Divide all values by an other number
     *
     * @param number Number to divide by
     */
    public void divide(final T number);
    
    /**
     * Multiply all values by a factor
     *
     * @param factor Factor to multiply with
     */
    public void multiply(final int factor);
    
    /**
    * Multiply all values by by an other number
    *
    * @param number Number to multiply with
    */
    public void multiply(final T number);
    
    /**
     * Adds all values by a factor
     *
     * @param factor Factor to add with
     */
    public void add(final int factor);
    
    /**
    * Adds all values by by an other number
    *
    * @param number Number to add with
    */
    public void add(final T number);
    
    /**
     * Subtracts all values by a factor
     *
     * @param factor Factor to subtract with
     */
    public void sub(final int factor);
    
    /**
    * Subtracts all values by by an other number
    *
    * @param number Number to subtract with
    */
    public void sub(final T number);
    
    /**
     * Gives all values packed in an array
     * @return All values in one single array
     */
    public T[] asArray();
}
