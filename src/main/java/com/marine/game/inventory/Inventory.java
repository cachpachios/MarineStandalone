///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// MarineStandalone is a minecraft server software and API.
// Copyright (C) IntellectualSites (marine.intellectualsites.com)
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

package com.marine.game.inventory;

import com.marine.game.chat.ChatComponent;
import com.marine.game.item.Item;
import com.marine.game.item.ItemID;
import com.marine.game.item.ItemSlot;
import com.marine.util.PacketWrapper;

public abstract class Inventory {

    private final byte uid;

    public PacketWrapper<Item>[] slots;

    public Inventory(int size, byte uid) {
        this.uid = uid;
        this.slots = new ItemSlot[size];
    }

    public byte getUID() {
        return this.uid;
    }

    public abstract byte getID();

    public abstract String getType();

    public PacketWrapper<Item>[] getSlots() {
        return slots;
    }

    public ItemSlot getSlot(int id) {
        return (ItemSlot) slots[id];
    }

    public void setSlot(int id, Item data) {
        slots[id] = new ItemSlot(data);
    }

    public void clear() {
        for (int i = 0; i < slots.length; i++)
            slots[i] = new ItemSlot(new Item(ItemID.EMPTY));
    }

    public abstract ChatComponent getTitle();

    public abstract byte getNumberOfSlots();

}
