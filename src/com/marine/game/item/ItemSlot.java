package com.marine.game.item;

import com.marine.io.data.ByteEncoder;

public class ItemSlot {
	private Item item;
	
	private byte amount;
	private byte damage;
	
	//TODO Enchantments :D

	public ItemSlot(ItemID itemID) {
		this(itemID, 1, 0);
	}
	
	public ItemSlot(ItemID itemID, int amount, int damage) {
		this.item = new Item(itemID, (short) damage, (byte) 0, amount);
	}
	
	public byte[] getBytes() {
		return ByteEncoder.writeShort(item.getID().getNumericID());
	}
}
