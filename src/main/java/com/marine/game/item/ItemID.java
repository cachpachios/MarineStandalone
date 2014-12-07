package com.marine.game.item;

import com.marine.util.IDObject;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created 2014-12-02 for MarineStandalone
 *
 * @author Citymonstret
 */
public enum ItemID implements IDObject {
    // AKA AIR
    EMPTY(-1, "air", "Air", false),

    IRON_SHOVEL(256, "iron_shovel", "Iron Shovel", true),
    IRON_PICKAXE(257, "iron_pickaxe", "Iron Pickaxe", true),
    IRON_AXE(258, "iron_axe", "Iron Axe", true),
    FLINT_AND_STEEL(259, "flint_and_steel", "Flint and Steel", true),
    APPLE(260, "apple", "Apple", false),
    BOW(261, "bow", "Bow", true),
    ARROW(262, "arrow", "Arrow", false),
    COAL(263, "coal", "Coal", false),
    DIAMOND(264, "diamond", "Diamond", false),
    IRON_INGOT(265, "iron_ingot", "Iron Ingot", false),
    GOLD_INGOT(266, "gold_ingot", "Gold Ingot", false),
    IRON_SWORD(267, "iron_sword", "Iron Sword", true),
    WOODEN_SWORD(268, "wooden_sword", "Wooden Sword", true),
    WOODEN_SHOVEL(269, "wooden_shovel", "Wooden Shovel", true),
    WOODEN_PICKAXE(270, "wooden_pickaxe", "Wooden Pickaxe", true),
    WOODEN_AXE(271, "wooden_axe", "Wooden Axe", true);

    private final short id;
    private final String name;
    private final String friendly;
    private final boolean canTakeDamage;

    ItemID(int id, String name, String friendly, boolean canTakeDamage) {
        this.id = (short) id;
        this.name = name;
        this.friendly = friendly;
        this.canTakeDamage = canTakeDamage;
    }

    public boolean canTakeDamage() {
        return canTakeDamage;
    }

    public short getID() {
        return id;
    }

    public String getFriendlyName() {
        return friendly;
    }

    @Override
    public String getStringID() {
        return this.name;
    }

    @Override
    public short getNumericID() {
        return this.id;
    }

    @Override
    public JSONObject toJSON() throws JSONException {
        JSONObject o = new JSONObject();
        o.put("type", "item");
        o.put("numeric", getNumericID());
        o.put("string", getStringID());
        return o;
    }

    @Override
    public String toString() {
        return "minecraft:" + name;
    }
}
