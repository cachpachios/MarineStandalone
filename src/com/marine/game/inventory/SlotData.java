package com.marine.game.inventory;

import com.marine.game.item.ItemID;
import com.marine.io.data.ByteEncoder;

public class SlotData {
	public SlotData(ItemID item, int amount) {
		super();
		this.item = item;
		this.amount = amount;
	}

	private ItemID item;
	@SuppressWarnings("unused")
	private int amount;
	
	public byte[] toBytes() {
		return ByteEncoder.writeShort(item.getID());
	}
}
