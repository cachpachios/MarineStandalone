package com.marine.game.inventory;

import com.marine.game.chat.ChatComponent;
import com.marine.game.item.ItemID;
import com.marine.game.item.ItemSlot;

public abstract class Inventory {
	public ItemSlot[] slots;
	
	public Inventory(int size) {
		slots = new ItemSlot[size];
	}
	
	public abstract byte getID();
	
	public abstract String getType();
	
	public ItemSlot[] getSlots() {
		return slots;
	}
	
	public ItemSlot getSlot(int id) {
		return slots[id];
	}
	
	public void setSlot(int id, ItemSlot data) {
		slots[id] = data;
	}
	
	public void clear() {
		for(int i = 0; i < slots.length;i++)
			slots[i] = new ItemSlot(ItemID.EMPTY, 0, 0);
	}
	
	public abstract ChatComponent getName();
	
	public abstract byte getNumberOfSlots();
	
}
