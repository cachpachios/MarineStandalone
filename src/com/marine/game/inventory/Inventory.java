package com.marine.game.inventory;

import com.marine.game.chat.ChatComponent;

public abstract class Inventory {
	public SlotData[] slots;
	
	public Inventory(int size) {
		slots = new SlotData[size];
	}
	
	public abstract byte getID();
	
	public abstract String getType();
	
	public SlotData[] getSlots() {
		return slots;
	}
	
	public SlotData getSlot(int id) {
		return slots[id];
	}
	
	public void setSlot(int id, SlotData data) {
		slots[id] = data;
	}
	
	public abstract ChatComponent getName();
	
	public abstract byte getNumberOfSlots();
	
}
