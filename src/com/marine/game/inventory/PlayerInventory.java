package com.marine.game.inventory;

import com.marine.game.chat.ChatComponent;

public final class PlayerInventory extends Inventory {

	public PlayerInventory(byte uid) {
		super(44, uid);
	}
	
	@Override
	public String getType() {
		return InventoryID.INVENTORY.getStringID();
	}


	@Override
	public byte getNumberOfSlots() {
		return 44;
	}

	@Override
	public byte getID() {
		return (byte) InventoryID.INVENTORY.getIntegerID(); // Player inventory is always 0
	}

	@Override
	public ChatComponent getTitle() {
		return new ChatComponent("Inventory");
	}

}
