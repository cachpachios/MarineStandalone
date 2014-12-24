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

import org.marinemc.io.binary.ByteData;

/**
 * Packet Wrapper used to wrap object when sent in packets
 *
 * @param <T>
 * @author Fozie
 */
public abstract class PacketWrapper<T> {

    private T obj;

    public PacketWrapper(T v) {
        this.obj = v;
    }

    public abstract T readFromData(ByteData d);

    public T readFromBytes(byte[] b) {
        return readFromData(new ByteData(b));
    }

    public abstract ByteData toByteData();

    public byte[] getBytes() {
        return toByteData().getBytes();
    }

    public T getData() {
        return obj;
    }

    public void setData(T data) {
        obj = data;
    }

    public T get() {
        return obj;
    }
}
