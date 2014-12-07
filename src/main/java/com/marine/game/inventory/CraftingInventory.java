package com.marine.game.inventory;

import com.marine.game.chat.ChatComponent;

/**
 * Created 2014-12-04 for MarineStandalone
 *
 * @author Citymonstret
 */
public class CraftingInventory extends Inventory {

    public CraftingInventory() {
        super(45, (byte) 0);
    }

    @Override
    public byte getID() {
        return (byte) InventoryID.CRAFTING_TABLE.getIntegerID();
    }

    @Override
    public String getType() {
        return InventoryID.CRAFTING_TABLE.getStringID();
    }

    @Override
    public ChatComponent getTitle() {
        return new ChatComponent("Crafting");
    }

    @Override
    public byte getNumberOfSlots() {
        return 45;
    }
}
