package com.marine.net.play.clientbound.inv;

import com.marine.game.inventory.Inventory;
import com.marine.io.data.ByteData;
import com.marine.net.Packet;
import com.marine.net.PacketOutputStream;
import com.marine.net.States;

import java.io.IOException;

/**
 * Created 2014-12-06 for MarineStandalone
 *
 * @author Citymonstret
 */
public class InventoryOpenPacket extends Packet {

    private final Inventory inventory;

    public InventoryOpenPacket(Inventory inventory) {
        this.inventory = inventory;
    }

    @Override
    public int getID() {
        return 0x2D;
    }

    @Override
    public void writeToStream(PacketOutputStream stream) throws IOException {
        ByteData data = new ByteData();
        data.writeByte(inventory.getUID());
        data.writeUTF8(inventory.getType());
        data.writeUTF8(inventory.getTitle().toString());
        data.writeByte(inventory.getNumberOfSlots());
        stream.write(getID(), data.getBytes());
    }

    @Override
    public void readFromBytes(ByteData input) {

    }

    @Override
    public States getPacketState() {
        return null;
    }
}
