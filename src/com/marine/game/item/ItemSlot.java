package com.marine.game.item;

import com.marine.io.data.ByteData;
import com.marine.util.PacketWrapper;

public class ItemSlot extends PacketWrapper<Item> {

	public ItemSlot(Item itemID) {
		super(itemID);
	}

	@Override
	public ByteData toByteData() {
		return null;
	}

	@Override
	public Item readFromData(ByteData d) {
		return null;
	}
}
