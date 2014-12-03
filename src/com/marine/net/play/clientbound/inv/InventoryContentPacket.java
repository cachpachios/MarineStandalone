package com.marine.net.play.clientbound.inv;

import java.io.IOException;
import java.io.OutputStream;

import com.marine.game.inventory.Inventory;
import com.marine.game.item.ItemID;
import com.marine.game.item.ItemSlot;
import com.marine.io.data.ByteData;
import com.marine.net.Packet;
import com.marine.net.States;

public class InventoryContentPacket extends Packet{

	final Inventory inv;
	
	public InventoryContentPacket(Inventory inventory) {
		this.inv = inventory;
	}

	@Override
	public int getID() {
		return 0x30;
	}

	@Override
	public void writeToStream(OutputStream stream) throws IOException {
		ByteData d = new ByteData();
		d.writeVarInt(getID());
		
		d.writeByte(inv.getID());
		
		d.writeShort((short) inv.getSlots().length);
		
		for(ItemSlot slot : inv.getSlots())
			if(slot==null)
				d.writeShort(ItemID.EMPTY.getID());
			else
				d.writeByte(slot.getBytes());
		
		d.writePacketPrefix();
		stream.write(d.getBytes());
	}

	@Override
	public void readFromBytes(ByteData input) {}

	@Override
	public States getPacketState() {
		return States.INGAME;
	}
	
}
