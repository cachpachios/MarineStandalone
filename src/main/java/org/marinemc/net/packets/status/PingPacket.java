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

package org.marinemc.net.packets.status;

import java.io.IOException;

import org.marinemc.io.binary.ByteInput;
import org.marinemc.io.data.ByteEncoder;
import org.marinemc.net.Packet;
import org.marinemc.net.PacketOutputStream;
import org.marinemc.net.States;

@SuppressWarnings("deprecation")
public class PingPacket extends Packet {

    protected long TIME;

    public PingPacket() {
        super(0x01, States.INTRODUCE);
    }

	@Override
    public void writeToStream(PacketOutputStream stream) throws IOException {
        stream.write(getID(), ByteEncoder.writeLong(TIME));
    }

    @Override
    public void readFromBytes(ByteInput input) {
        TIME = input.readLong();
    }

}
