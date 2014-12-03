package com.marine.net.play.clientbound.windows;

import java.io.IOException;
import java.io.OutputStream;

import com.marine.game.inventory.Inventory;
import com.marine.game.inventory.SlotData;
import com.marine.game.item.ItemID;
import com.marine.io.data.ByteData;
import com.marine.net.Packet;
import com.marine.net.States;

public class WindowItemsPacket extends Packet{

	final Inventory inv;
	
	public WindowItemsPacket(Inventory inventory) {
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
		
		for(SlotData slot : inv.getSlots())
			if(slot==null)
				d.writeShort(ItemID.EMPTY.getID());
			else
				d.writeByte(slot.toBytes());
		
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
