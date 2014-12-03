package com.marine.game.item;

import com.marine.io.data.ByteEncoder;

public class ItemSlot {
	private ItemID itemID;
	
	private byte amount;
	private byte damage;
	
	//TODO Enchantments :D

	public ItemSlot(ItemID itemID) {
		this(itemID, 1, 0);
	}
	
	public ItemSlot(ItemID itemID, int amount, int damage) {
		this.itemID = itemID;
		this.amount = (byte) amount;
		this.damage = (byte) damage;
	}
	
	public byte[] getBytes() {
		return ByteEncoder.writeShort(itemID.getID());
	}
}
