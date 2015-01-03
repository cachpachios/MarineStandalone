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

package org.marinemc.world;

import org.json.JSONException;
import org.json.JSONObject;
import org.marinemc.util.IDObject;
/**
 * All posseble blocks/materials in minecraft as of 1.8.2
 * 
 * @author Fozie
 */
public enum BlockID implements IDObject {
    AIR(0, "air"),
    STONE(1, "stone"),
    GRASS(2, "grass"),
    DIRT(3, "dirt"),
    COBBLESTONE(4, "cobblestone"),
    PLANKS(5, "planks"),
    SAPLING(6, "sapling"),
    BEDROCK(7, "bedrock"),
    WATER_FLOWING(8, "flowing_water"),
    WATER_STILL(9, "water"),
    LAVA_FLOWING(10, "flowing_lava"),
    LAVA_STILL(11, "lava"),
    SAND(12, "sand"),
    GRAVEL(13, "gravel"),
    ORE_GOLD(14, "gold_ore"),
    ORE_IRON(15, "iron_ore"),
    ORE_COAL(16, "coal_ore"),
    LOG_OAK(17, "log",  0), // Metadata 0 for oak 1 for spruce 2 for birch.... etc
    LOG_SPRUCE(17, "log",  1), // Metadata 0 for oak 1 for spruce 2 for birch.... etc
    LOG_BIRCH(17, "log",  2), // Metadata 0 for oak 1 for spruce 2 for birch.... etc
    LOG_JUNGLE(17, "log",  3), // Metadata 0 for oak 1 for spruce 2 for birch.... etc
    LOG_ACACIA(17, "log",  4), // Metadata 0 for oak 1 for spruce 2 for birch.... etc
    LOG_DARK_OAK(17, "log",  5), // Metadata 0 for oak 1 for spruce 2 for birch.... etc
    LEAVES_OAK(18, "leaves",  0),
    LEAVES_SPRUCE(18, "leaves",  1),
    LEAVES_BIRCH(18, "leaves",  2),
    LEAVES_JUNGLE(18, "leaves",  3),
    LEAVES_NODCAY_OAK(18, "leaves",  4),
    LEAVES_NODCAY_SPRUCE(18, "leaves",  5),
    LEAVES_NODCAY_BIRCH(18, "leaves",  6),
    LEAVES_NODCAY_JUNGLE(18, "leaves",  7),
    LEAVES_CHDCAY_OAK(18, "leaves",  8),
    LEAVES_CHDCAY_SPRUCE(18, "leaves",  9),
    LEAVES_CHDCAY_BIRCH(18, "leaves",  10),
    LEAVES_CHDCAY_JUNGLE(18, "leaves",  11),
    LEAVES_DCAY_OAK(18, "leaves",  12),
    LEAVES_DCAY_SPRUCE(18, "leaves",  13),
    LEAVES_DCAY_BIRCH(18, "leaves",  14),
    LEAVES_DCAY_JUNGLE(18, "leaves",  15),
    SPONGE(19, "sponge"),
    GLASS(20, "glass"),
    LAPIS_ORE(21, "lapis_ore"),
    LAPIS_BLOCK(22, "lapis_block"),
    DISPENSER(23, "dispenser"),
    SANDSTONE(24, "sandstone"),
    NOTEBLOCK(25, "noteblock"),
    BED(26, "bed"),
    RAIL_POWERD(27, "golden_rail"),
    RAIL_DETECTION(28, "detector_rail"),
    PISTON_STICKY(29, "sticky_piston"),
    COBWEB(30, "web"),
    TALL_GRASS(31, "tallgrass",1),
    FERN(31, "tallgrass",2),
    DEADBUSH(32, "deadbush"),
    PISTON_BASE(33, "piston"),
    PISON_HEAD(34, "piston_head"),
    WOOL_WHITE(35, "wool",  0),
    WOOL_ORANGE(35, "wool",  1),
    WOOL_MAGENTA(35, "wool",  2),
    WOOL_LIGHT_BLUE(35, "wool",  3),
    WOOL_YELLOW(35, "wool",  4),
    WOOL_LIME(35, "wool",  5),
    WOOL_PINK(35, "wool",  6),
    WOOL_GRAY(35, "wool",  7),
    WOOL_LIGHT_GRAY(35, "wool",  8),
    WOOL_CYAN(35, "wool",  9),
    WOOL_PURPLE(35, "wool",  10),
    WOOL_BLUE(35, "wool",  11),
    WOOL_BROWN(35, "wool",  12),
    WOOL_GREEN(35, "wool",  13),
    WOOL_RED(35, "wool",  14),
    WOOL_BLACK(35, "wool",  15),
    PISON_EXTENSTION(36, "piston_extension"),
    YELLOW_FLOWER(37, "yellow_flower"),
    RED_FLOWER(38, "red_flower"),
    MUSHROOM_BROWN(39, "brown_mushroom"),
    MUSHTOOM_RED(40, "red_mushroom"),
    GOLD_BLOCK(41, "gold_block"),
    IRON_BLOCK(42, "iron_block"),
    STONE_SLAB_DOUBLE(43, "double_stone_slab"),
    STONE_SLAB(44, "stone_slab"),
    BRICK_BLOCK(45, "brick_block"),
    TNT(46, "tnt"),
    BOOKSHELF(47, "bookshelf"),
    COBBLESTONE_MOSSY(48, "mossy_cobblestone"),
    OBSIDIAN(49, "obsidian"),
    TORCH(50, "torch"),
    FIRE(51, "fire"),
    SPAWNER(52, "mob_spawner"),
    WOOD_STAIRS(53, "oak_stairs"),
    CHEST(54, "chest"),
    REDSTONE_WIRE(55, "redstone_wire"),
    DIAMOND_ORE(56, "diamond_ore"),
    DIAMOND_BLOCK(57, "diamond_block"),
    WORKBENCH(58, "crafting_table"),
    WHEAT_TILE(59, "wheat"),
    FARMLAND(60, "farmland"),
    FURNACE(61, "furnace"),
    FURNACE_LIT(62, "lit_furnace"),
    SIGN_STANDING(63, "standing_sign"),
    DOOR_WOODEN(64, "wooden_door"),
    LADDER(65, "ladder"),
    RAIL(66, "rail"),
    STONE_STAIRS(67, "stone_stairs"),
    SIGN_WALL(68, "wall_sign"),
    LEVER(69, "lever"),
    PRESSURE_PLATE_STONE(70, "stone_pressure_plate"),
    DOOR_IRON(71, "iron_door"),
    PRESSURE_PLATE_WOOD(72, "wooden_pressure_plate"),
    REDSTONE_ORE(73, "redstone_ore"),
    REDSTONE_ORE_LIT(74, "lit_redstone_ore"),
    REDSTONE_TORCH(75, "unlit_redstone_torch"),
    REDSTONE_TORCH_LIT(76, "redstone_torch"),
    BUTTON_STONE(77, "stone_button"),
    SNOW_LAYER(78, "snow_layer"),
    ICE(79, "ice"),
    SNOW(80, "snow"),
    CACTUS(81, "cactus"),
    CLAY(82, "clay"),
    SUGERCANE(83, "reeds"),
    JUKEBOX(84, "jukebox"),
    FENCE(85, "fence"),
    PUMPKIN(86, "pumpkin"),
    NETHERRACK(87, "netherrack"),
    SOUL_SAND(88, "soul_sand"),
    GLOWSTONE(89, "glowstone"),
    PORTAL(90, "portal"),
    PUMPKIN_LIT(91, "lit_pumpkin"),
    CAKE(92, "cake"),
    REPEATER_UNPOWERED(93, "unpowered_repeater"),
    REPEATER_POWERD(94, "powered_repeater"),
    GLASS_STAINED_WHITE(95, "stained_glass", 0),
    GLASS_STAINED_ORANGE(95, "stained_glass", 1),
    GLASS_STAINED_MAGENTA(95, "stained_glass", 2),
    GLASS_STAINED_LIGHT_BLUE(95, "stained_glass", 3),
    GLASS_STAINED_YELLOW(95, "stained_glass", 4),
    GLASS_STAINED_LIME(95, "stained_glass", 5),
    GLASS_STAINED_PINK(95, "stained_glass", 6),
    GLASS_STAINED_GRAY(95, "stained_glass", 7),
    GLASS_STAINED_LIGHT_GRAY(95, "stained_glass", 8),
    GLASS_STAINED_CYAN(95, "stained_glass", 9),
    GLASS_STAINED_PURPLE(95, "stained_glass", 10),
    GLASS_STAINED_BLUE(95, "stained_glass",  11),
    GLASS_STAINED_BROWN(95, "stained_glass",  12),
    GLASS_STAINED_GREEN(95, "stained_glass",  13),
    GLASS_STAINED_RED(95, "stained_glass",  14),
    GLASS_STAINED_BLACK(95, "stained_glass",  15),
    TRAPDOOR(96, "trapdoor"),
    MONSTER_EGG(97, "monster_egg"),
    STONEBRICK(98, "stonebrick"),
    MUSHROOM_BLOCK_BROWN(99, "brown_mushroom_block"),
    MUSHROOM_BLOCK_RED(100, "red_mushroom_block"),
    FENCE_IRON(101, "iron_bars"),
    GLASS_PANE(102, "glass_pane"),
    MELON_BLOCK(103, "melon_block"),
    PUMKIN_PLANT(104, "pumpkin_stem"),
    MELON_PLANT(105, "melon_stem"),
    VINE(106, "vine"),
    FENCE_GATE(107, "fence_gate"),
    BRICK_STAIRS(108, "brick_stairs"),
    STONE_BRICK_STAIRS(109, "stone_brick_stairs"),
    MYCELIUM(110, "mycelium"),
    LILY_PAD(111, "waterlily"),
    NETHER_BRICK(112, "nether_brick"),
    NETHER_BRICK_FENCE(113, "nether_brick_fence"),
    NETHER_BRICK_STAIRS(114, "nether_brick_stairs"),
    NETHER_WART(115, "nether_wart"),
    ENCHANTING_TABLE(116, "enchanting_table"),
    BREWING_STAND(117, "brewing_stand"),
    CAULDRON(118, "cauldron"),
    PORTAL_END(119, "end_portal"),
    PORTAL_FRAME(120, "end_portal_frame"),
    END_STONE(121, "end_stone"),
    DRAGON_EGG(122, "dragon_egg"),
    REDSTONE_LAMP(123, "redstone_lamp"),
    REDSTONE_LAMP_LIT(124, "lit_redstone_lamp"),
    WOODEN_SLAB_DOUBLE(125, "double_wooden_slab"),
    WOODEN_SLAB(126, "wooden_slab"),
    COCOA_BEAN(127, "cocoa"),
    SANDSTONE_STAIRS(128, "sandstone_stairs"),
    ENERALD_ORE(129, "emerald_ore"),
    CHEST_ENDER(130, "ender_chest"),
    TRIPWIRE_HOOK(131, "tripwire_hook"),
    TRIPWIRE(132, "tripwire"),
    EMERALD_BLOCK(133, "emerald_block"),
    WOODEN_SPRUCE_STAIRS(134, "spruce_stairs"),
    WOODEN_BITCH_STAIRS(135, "birch_stairs"),
    WOODEN_JUNGLE_STAIRS(136, "jungle_stairs"),
    COMMAND_BLOCK(137, "command_block"),
    BEACON(138, "beacon"),
    COBBLESTONE_WALL(139, "cobblestone_wall"),
    FLOWER_POT(140, "flower_pot"),
    CARROTS_PLANT(141, "carrots"),
    POTATO_PLANT(142, "potatoes"),
    BUTTON_WOOD(143, "wooden_button"),
    SKUL(144, "skull"),
    ANVIL(145, "anvil"),
    CHEST_TRAPPED(146, "trapped_chest"),
    PRESSURE_PLATE_GOLD(147, "light_weighted_press"),
    PRESSURE_PLATE_IRON(148, "heavy_weighted_press"),
    COMPARATOR_UNPOWERED(149, "unpowered_comparator"),
    COMPARATOR_POWERED(150, "powered_comparator"),
    DAYLIGHT_DETECTOR(151, "daylight_detector"),
    REDSTONE_BLOCK(152, "redstone_block"),
    QUARTZ_ORE(153, "quartz_ore"),
    HOPPER(154, "hopper"),
    QUARTZ_BLOCK(155, "quartz_block"),
    QUARTZ_STAIRS(156, "quartz_stairs"),
    RAIL_ACTIVATOR(157, "activator_rail"),
    DROPPER(158, "dropper"),
    HARDENED_CLAY_STAINED_WHITE(95, "stained_glass",  0),
    HARDENED_CLAY_STAINED_ORANGE(95, "stained_glass",  1),
    HARDENED_CLAY_STAINED_MAGENTA(95, "stained_glass",  2),
    HARDENED_CLAY_STAINED_LIGHT_BLUE(95, "stained_glass",  3),
    HARDENED_CLAY_STAINED_YELLOW(95, "stained_glass",  4),
    HARDENED_CLAY_STAINED_LIME(95, "stained_glass",  5),
    HARDENED_CLAY_STAINED_PINK(95, "stained_glass",  6),
    HARDENED_CLAY_STAINED_GRAY(95, "stained_glass",  7),
    HARDENED_CLAY_STAINED_LIGHT_GRAY(95, "stained_glass",  8),
    HARDENED_CLAY_STAINED_CYAN(95, "stained_glass",  9),
    HARDENED_CLAY_STAINED_PURPLE(95, "stained_glass",  10),
    HARDENED_CLAY_STAINED_BLUE(95, "stained_glass",  11),
    HARDENED_CLAY_STAINED_BROWN(95, "stained_glass",  12),
    HARDENED_CLAY_STAINED_GREEN(95, "stained_glass",  13),
    HARDENED_CLAY_STAINED_RED(95, "stained_glass",  14),
    HARDENED_CLAY_STAINED_BLACK(95, "stained_glass",  15),
    GLASS_PANE_STAINED_WHITE(160, "stained_glass_pane",  0),
    GLASS_PANE_STAINED_ORANGE(160, "stained_glass_pane",  1),
    GLASS_PANE_STAINED_MAGENTA(160, "stained_glass_pane",  2),
    GLASS_PANE_STAINED_LIGHT_BLUE(160, "stained_glass_pane",  3),
    GLASS_PANE_STAINED_YELLOW(160, "stained_glass_pane",  4),
    GLASS_PANE_STAINED_LIME(160, "stained_glass_pane",  5),
    GLASS_PANE_STAINED_PINK(160, "stained_glass_pane",  6),
    GLASS_PANE_STAINED_GRAY(160, "stained_glass_pane",  7),
    GLASS_PANE_STAINED_LIGHT_GRAY(160, "stained_glass_pane",  8),
    GLASS_PANE_STAINED_CYAN(160, "stained_glass_pane",  9),
    GLASS_PANE_STAINED_PURPLE(160, "stained_glass_pane",  10),
    GLASS_PANE_STAINED_BLUE(160, "stained_glass_pane",  11),
    GLASS_PANE_STAINED_BROWN(160, "stained_glass_pane",  12),
    GLASS_PANE_STAINED_GREEN(160, "stained_glass_pane",  13),
    GLASS_PANE_PANE_STAINED_RED(160, "stained_glass_pane",  14),
    GLASS_PANE_STAINED_BLACK(160, "stained_glass_pane",  15),
    LEAVES_OTHER(161, "leaves2"),
    LOGS_OTHER(162, "log2"),
    WOODEN_ACAIA_STAIRS(163, "acacia_stairs"),
    WOODEN_DARKOAK_STAIRS(164, "dark_oak_stairs"),
    SLIME_BLOCK(165, "slime"),
    BARRIER(166, "barrier"),
    IRON_TRAPDOOR(167, "iron_trapdoor"),
    PRISMARINE(168, "prismarine"),
    SEA_LANTERN(169, "sea_lantern"),
    HAY_BLOCK(170, "hay_block"),
    CARPET(171, "carpet"),
    HARDENED_CLAY(172, "hardened_clay"),
    COAL_BLOCK(173, "coal_block"),
    ICE_PACKED(174, "packed_ice"),
    PLANT_DOUBLE(175, "double_plant"),
    BANNER_STANDING(176, "standing_banner"),
    BANNER_WALL(177, "wall_banner"),
    DAYLIGHT_SENSOR_INV(178, "daylight_detector_inverted"),
    SANDSTONE_RED(179, "red_sandstone"),
    SANDSTONE_RED_STAIRS(180, "red_sandstone_stairs"),
    DOUBLE_STONE_SLAB_OTHER(181, "double_stone_slab2"),
    STONE_SLAB_OTHER(182, "stone_slab2"),
    FENCE_GATE_SPRUCE(183, "spruce_fence_gate"),
    FENCE_GATE_BIRCH(184, "birch_fence_gate"),
    FENCE_GATE_JUNGLE(185, "jungle_fence_gate"),
    FENCE_GATE_DARK_OAK(186, "dark_oak_fence_gate"),
    FENCE_GATE_ACACIA(187, "acacia_fence_gate"),
    FENCE_SPRUCE(188, "spruce_fence"),
    FENCE_BIRCH(189, "birch_fence"),
    FENCE_JUNGLE(190, "jungle_fence"),
    FENCE_DARK_OAK(191, "dark_oak_fence"),
    FENCE_ACACIA(192, "acacia_fence"),
    DOOR_SPRUCE(193, "spruce_door"),
    DOOR_BIRCH(194, "birch_door"),
    DOOR_JUNGLE(195, "jungle_door"),
    DOOR_ACACIA(196, "acacia_door"),
    DOOR_DARKOAK(197, "dark_oak_door");
    private final int ID;
    private final String NAME;

    private final int metaData;

    private BlockID(int ID, String NAME) {
        this.ID = ID;
        this.NAME = NAME;
        this.metaData = 0;
    }

    private BlockID(int ID, String NAME, int metaType) {
        this.ID = ID;
        this.NAME = NAME;
        this.metaData = metaType;
    }

    public boolean isMetaBlock() {
        return metaData != 0;
    }
    
    public int getIntID() {
        return ID;
    }
    
    public byte getID() {
        return (byte)ID;
    }

    public String getName() {
        return NAME;
    }

    public int getMetaBlock() {
        return metaData;
    }

    public short getPacketID() {
        return (short) ((getID() << 4) | getMetaBlock());
    }

    @Override
    public String getStringID() {
        return this.getName();
    }

    @Override
    public short getNumericID() {
        return getID();
    }

    @Override
    public JSONObject toJSON() throws JSONException {
        JSONObject o = new JSONObject();
        o.put("type", "block");
        o.put("numeric", getNumericID());
        o.put("string", getStringID());
        return o;
    }
}    
