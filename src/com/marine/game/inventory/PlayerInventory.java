package com.marine.game.inventory;

import com.marine.game.chat.ChatComponent;

public final class PlayerInventory extends Inventory {

	public PlayerInventory() {
		super(44);
	}
	
	@Override
	public String getType() {
		return "inventory";
	}


	@Override
	public byte getNumberOfSlots() {
		return 44;
	}

	@Override
	public byte getID() {
		return 0; // Player inventory is always 0
	}

	@Override
	public ChatComponent getTitle() {
		return new ChatComponent("Inventory");
	}

}
