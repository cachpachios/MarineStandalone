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

package org.marinemc.game.inventory;

import org.marinemc.game.chat.ChatMessage;
import org.marinemc.game.item.Item;
import org.marinemc.game.item.ItemID;
import org.marinemc.game.item.ItemSlot;
import org.marinemc.util.wrapper.PacketWrapper;

public abstract class Inventory {

	private final byte uid;

	public PacketWrapper<Item>[] slots;

	public Inventory(final int size, final byte uid) {
		this.uid = uid;
		slots = new ItemSlot[size];
	}

	public byte getUID() {
		return uid;
	}

	public abstract byte getID();

	public abstract String getType();

	public PacketWrapper<Item>[] getSlots() {
		return slots;
	}

	public ItemSlot getSlot(final int id) {
		return (ItemSlot) slots[id];
	}

	public void setSlot(final int id, final Item data) {
		slots[id] = new ItemSlot(data);
	}

	public void clear() {
		for (int i = 0; i < slots.length; i++)
			slots[i] = new ItemSlot(new Item(ItemID.EMPTY));
	}

	public abstract ChatMessage getTitle();

	public abstract byte getNumberOfSlots();

}
