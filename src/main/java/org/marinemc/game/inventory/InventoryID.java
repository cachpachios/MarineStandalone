///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// MarineStandalone is a minecraft server software and API.
// Copyright (C) MarineMC (marinemc.org)
//
// This program is free software; you can redistribute it and/or modify
// it under the terms of the GNU General Public License as published by
// the Free Software Foundation; either version 2 of the License, or
// (at your option) any later version.
//
// This program is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
// GNU General Public License for more details.
//
// You should have received a copy of the GNU General Public License along
// with this program; if not, write to the Free Software Foundation, Inc.,
// 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

package org.marinemc.game.inventory;

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
