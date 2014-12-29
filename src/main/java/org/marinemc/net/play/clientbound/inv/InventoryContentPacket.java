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

import java.io.IOException;

import org.marinemc.game.inventory.Inventory;
import org.marinemc.game.item.Item;
import org.marinemc.game.item.ItemID;
import org.marinemc.io.binary.ByteList;
import org.marinemc.net.Packet;
import org.marinemc.net.PacketOutputStream;
import org.marinemc.net.States;
import org.marinemc.util.wrapper.PacketWrapper;
/**
 * @author Fozie
 */
public class InventoryContentPacket extends Packet {

    final Inventory inv;

    public InventoryContentPacket(Inventory inventory) {
        super(0x30, States.INGAME);
        this.inv = inventory;
    }

    @Override
    public void writeToStream(PacketOutputStream stream) throws IOException {
    	ByteList d = new ByteList();

        d.writeByte(inv.getID());

        d.writeShort((short) inv.getSlots().length);

        for (PacketWrapper<Item> slot : inv.getSlots())
            if (slot == null)
                d.writeShort(ItemID.EMPTY.getFirst());
            else
                d.write(slot.getBytes());

        stream.write(getID(), d);
    }

}
