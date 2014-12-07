package com.marine.game.inventory;

/**
 * Created 2014-12-04 for MarineStandalone
 *
 * @author Citymonstret
 */
public enum InventoryID {
    INVENTORY(0, "inventory"),
    CHEST(0, "minecraft:chest"),
    CRAFTING_TABLE(1, "minecraft:crafting_table"),
    FURNACE(2, "minecraft:furnace"),
    DISPENSER(3, "minecraft:dispenser"),
    ENCHANTMENT_TABLE(4, "minecraft:enchantment_table"),
    BREWING_STAND(5, "minecraft:brewing_stand"),
    NPC_TRADE(6, "minecraft:npc_trade"),
    BEACON(7, "minecraft:beacon"),
    ANVIL(8, "minecraft:anvil"),
    HOPPER(9, "minecraft:hopper"),
    DROPPER(10, "minecraft:dropper"),
    HORSE(11, "EntityHorse");

    private final int integerID;
    private final String stringID;

    InventoryID(final int integerID, final String stringID) {
        this.integerID = integerID;
        this.stringID = stringID;
    }

    @Override
    public String toString() {
        return this.stringID;
    }

    public String getStringID() {
        return this.stringID;
    }

    public int getIntegerID() {
        return this.integerID;
    }
}
