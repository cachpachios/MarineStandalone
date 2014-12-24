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

package org.marinemc.net.play.clientbound.inv;

import org.marinemc.game.inventory.Inventory;
import org.marinemc.io.data.ByteData;
import org.marinemc.net.Packet;
import org.marinemc.net.PacketOutputStream;
import org.marinemc.net.States;

import java.io.IOException;

/**
 * @author Fozie
 */
public class InventoryOpenPacket extends Packet {

    private final Inventory inventory;

    public InventoryOpenPacket(Inventory inventory) {
        super(0x2d, States.INGAME);
        this.inventory = inventory;
    }

    @Override
    public void writeToStream(PacketOutputStream stream) throws IOException {
        ByteData data = new ByteData();
        data.writeByte(inventory.getUID());
        data.writeUTF8(inventory.getType());
        data.writeUTF8(inventory.getTitle().toString());
        data.writeByte(inventory.getNumberOfSlots());
        stream.write(getID(), data);
    }

    @Override
    public void readFromBytes(ByteData input) {

    }
}
