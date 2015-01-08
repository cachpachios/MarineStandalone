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

/**
 * @author Fozie
 */
public enum BiomeID {
	UNKNOWN(-1, "unknowned"), OCEAN(0, "Ocean"), PLAINS(1, "Plains"), DESERT(2,
			"Desert"), EXTREAM_HILLS(3, "Extreme Hills"), FOREST(4, "Forest"), TAIGA(
			5, "Taiga"), SWAMPLAND(6, "Swampland"), RIVER(7, "River"), NETHER(
			8, "Nether"), THE_END(9, "End"), FROZEN_OCEAN(10, "Frozen Ocean"), FROZEN_RIVER(
			11, "Frozen River"), ICE_PLAINS(12, "Ice Plains"), ICE_MOUNTIANS(
			13, "Ice Mountains"), MUSHROOM_ISLAND(14, "Mushroom Island"), MUSHROOM_ISLAND_SHORE(
			15, "Mushroom Island Shore"), BEACH(16, "Beach"), DESERT_HILLS(17,
			"Desert Hills"), FOREST_HILLS(18, "Forest Hills"), TAIGA_HILLS(19,
			"Taiga Hills"), EXTREAM_HILLS_EDGE(20, "Extreme Hills Edge"), JUNGLE(
			21, "Jungle"), JUNGLE_HILLS(22, "Jungle Hills"), JUNGLE_EDGE(23,
			"Jungle Edge"), DEEP_OCEAN(24, "Deep Ocean"), STONE_BEACH(25,
			"Stone Beach"), COLD_BEACH(26, "Cold Beach"), BIRCH_FOREST(27,
			"Birch Forest"), BIRCH_FOREST_HILLS(28, "Birch Forest Hills"), ROOFED_FOREST(
			29, "Roofed Forest"), COLD_TAIGA(30, "Cold Taiga"), COLD_TAIGA_HILLS(
			31, "Cold Taiga Hills"), MEGA_TAIGA(32, "Mega Taiga"), MEGA_TAIGA_HILLS(
			33, "Mega Taiga Hills"), EXTREAM_HILLS_EXTRA(34, "Extreme Hills+"), SAVANNA(
			35, "Savanna"), SAVANNA_PLATEAU(36, "Savanna Plateau"), MESA(37,
			"Mesa"), MESA_PLATEUA_F(38, "Mesa Plateau F"), MESA_PLATEAU(39,
			"Mesa Plateau"), SUNFLOWER_PLAINS(129, "Sunflower Plains"), DESERT_M(
			130, "Desert M"), EXTREAM_HILLS_M(131, "Extreme Hills M"), FLOWER_FOREST(
			132, "Flower Forest"), TAIGA_M(133, "Taiga M"), SWAMPLAND_M(134,
			"Swampland M"), ICE_PLAINS_SPIKES(140, "Ice Plains Spikes"), JUNGLE_M(
			149, "Jungle M"), JUNGLE_EDGE_M(151, "JungleEdge M"), BIRCH_FOREST_M(
			155, "Birch Forest M"), BIRCH_FOREST_HILLS_M(156,
			"Birch Forest Hills M"), ROOFED_FOREST_M(157, "Roofed Forest M"), COLD_TAIGA_M(
			158, "Cold Taiga M"), MEGA_SPRUCE_TAIGA(160, "Mega Spruce Taiga"), REDWOOD_TAIGA_HILLS_M(
			161, "Redwood Taiga Hills M"), EXTREME_HILLS_EXTRA_M(162,
			"Extreme Hills+ M"), SAVANNA_M(163, "Savanna M"), SAVANNA_PLATEAU_M(
			164, "Savanna Plateau M"), MESA_BRYCE(165, "Mesa (Bryce)"), MESA_PLATEAU_F_M(
			166, "Mesa Plateau F M"), MESA_PLATEAU_M(167, "Mesa Plateau M");

	private final byte ID;
	private final String name;

	private BiomeID(final int id, final String name) {
		ID = (byte) id;
		this.name = name;
	}

	public byte getID() {
		return ID;
	}

	public String getName() {
		return name;
	}
}