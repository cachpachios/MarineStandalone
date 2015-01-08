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

package org.marinemc.game.item;

import java.util.Arrays;
import java.util.List;

import org.json.JSONException;
import org.marinemc.game.chat.ChatColor;
import org.marinemc.util.IDObject;
import org.marinemc.world.BlockID;

/**
 * Created 2014-12-03 for MarineStandalone
 *
 * @author Citymonstret
 */
public class Item {

	private IDObject id;
	private String displayName = "";
	private List<String> lore = null;
	private short damage = 0;
	private byte data = 0x00;
	private int amount = 1;

	public Item(final IDObject id, final short damage, final byte data,
			final int amount) {
		this.id = id;
		this.damage = damage;
		this.data = data;
		this.amount = amount;
	}

	public Item(final IDObject id) {
		damage = 0;
		data = 0x00;
		amount = 1;
	}

	public IDObject getID() {
		return id;
	}

	public List<String> getLore() {
		return lore;
	}

	public void setLore(final String... lore) {
		this.lore = Arrays.asList(lore);
	}

	public void setLore(final List<String> lore) {
		if (lore == null)
			setLore();
		else
			this.lore = lore;
	}

	public short getDamage() {
		return damage;
	}

	public void setDamage(final short damage) {
		this.damage = damage;
	}

	public byte getData() {
		return data;
	}

	public void setData(final byte data) {
		this.data = data;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		if (amount < 0)
			amount = 1;
		this.amount = amount;
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		if (displayName.equals(""))
			displayName = ChatColor.WHITE.toString();
		this.displayName = displayName;
	}

	public boolean canTakeDamage() {
		return isItem() && ((ItemID) id).canTakeDamage();
	}

	/**
	 * Get either the friendly name or the block name
	 *
	 * @return Name
	 */
	public String getFriendlyName() {
		if (isItem())
			return ((ItemID) id).getFriendlyName();
		else
			return ((BlockID) id).getName();
	}

	public boolean isItem() {
		return id instanceof ItemID;
	}

	public boolean isBlock() {
		return id instanceof BlockID;
	}

	public JSONObject toJSONObject() throws JSONException {
		final JSONObject o = new JSONObject();
		o.put("id", id.toJSON());
		o.put("displayName", displayName);
		o.put("lore", lore.toArray(new String[lore.size()]));
		o.put("damage", damage);
		o.put("data", data);
		o.put("amount", amount);
		return o;
	}

}
