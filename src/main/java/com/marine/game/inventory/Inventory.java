package com.marine.game.inventory;

import com.marine.game.chat.ChatComponent;
import com.marine.game.item.Item;
import com.marine.game.item.ItemID;
import com.marine.game.item.ItemSlot;
import com.marine.util.PacketWrapper;

public abstract class Inventory {

    private final byte uid;

    public PacketWrapper<Item>[] slots;

    public Inventory(int size, byte uid) {
        this.uid = uid;
        this.slots = new ItemSlot[size];
    }

    public byte getUID() {
        return this.uid;
    }

    public abstract byte getID();

    public abstract String getType();

    public PacketWrapper<Item>[] getSlots() {
        return slots;
    }

    public ItemSlot getSlot(int id) {
        return (ItemSlot) slots[id];
    }

    public void setSlot(int id, Item data) {
        slots[id] = new ItemSlot(data);
    }

    public void clear() {
        for (int i = 0; i < slots.length; i++)
            slots[i] = new ItemSlot(new Item(ItemID.EMPTY));
    }

    public abstract ChatComponent getTitle();

    public abstract byte getNumberOfSlots();

}
