package com.marine.net.play.clientbound.inv;

import com.marine.game.inventory.Inventory;
import com.marine.game.item.Item;
import com.marine.game.item.ItemID;
import com.marine.io.data.ByteData;
import com.marine.net.Packet;
import com.marine.net.PacketOutputStream;
import com.marine.net.States;
import com.marine.util.PacketWrapper;

import java.io.IOException;

public class InventoryContentPacket extends Packet {

    final Inventory inv;

    public InventoryContentPacket(Inventory inventory) {
        this.inv = inventory;
    }

    @Override
    public int getID() {
        return 0x30;
    }

    @Override
    public void writeToStream(PacketOutputStream stream) throws IOException {
        ByteData d = new ByteData();
        
        d.writeByte(inv.getID());

        d.writeShort((short) inv.getSlots().length);

        for (PacketWrapper<Item> slot : inv.getSlots())
            if (slot == null)
                d.writeShort(ItemID.EMPTY.getID());
            else
                d.writeByte(slot.getBytes());

        stream.write(getID(), d);
    }

    @Override
    public void readFromBytes(ByteData input) {
    }

    @Override
    public States getPacketState() {
        return States.INGAME;
    }

}
