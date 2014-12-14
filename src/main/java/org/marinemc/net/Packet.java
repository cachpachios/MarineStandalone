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

package org.marinemc.net;

import org.marinemc.io.data.ByteData;

import java.io.IOException;
/**
 * @author Fozie
 */
public abstract class Packet {

    public static Packet createSimplePacket(final byte ID, final byte[] inputdata, final States state) {
        Packet p = new Packet() {

            States s = state;

            ByteData data = new ByteData(inputdata);

            @Override
            public void writeToStream(PacketOutputStream stream) throws IOException {
                stream.write(ID, data);
            }

            @Override
            public void readFromBytes(ByteData input) {
            }

            @Override
            public int getID() {
                return ID;
            }

            @Override
            public States getPacketState() {
                return s;
            }

        };
        return p;
    }

    public abstract int getID();

    public abstract void writeToStream(PacketOutputStream stream) throws IOException;

    public abstract void readFromBytes(ByteData input);

    public abstract States getPacketState();

}
