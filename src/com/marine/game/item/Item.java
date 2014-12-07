package com.marine.game.item;

import com.marine.game.chat.ChatColor;
import com.marine.util.IDObject;
import com.marine.world.BlockID;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.List;

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

    public Item(IDObject id, short damage, byte data, int amount) {
        this.id = id;
        this.damage = damage;
        this.data = data;
        this.amount = amount;
    }

    public Item(IDObject id) {
        this.damage = 0;
        this.data = 0x00;
        this.amount = 1;
    }

    public IDObject getID() {
        return id;
    }

    public List<String> getLore() {
        return lore;
    }

    public void setLore(String... lore) {
        this.lore = Arrays.asList(lore);
    }

    public short getDamage() {
        return damage;
    }

    public void setDamage(short damage) {
        this.damage = damage;
    }

    public byte getData() {
        return data;
    }

    public void setData(byte data) {
        this.data = data;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        if (amount < 0) amount = 1;
        this.amount = amount;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        if (displayName.equals("")) {
            displayName = ChatColor.WHITE.toString();
        }
        this.displayName = displayName;
    }

    public void setLore(List<String> lore) {
        if (lore == null) {
            setLore();
        } else {
            this.lore = lore;
        }
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
        if (isItem()) {
            return ((ItemID) id).getFriendlyName();
        } else {
            return ((BlockID) id).getName();
        }
    }

    public boolean isItem() {
        return (id instanceof ItemID);
    }

    public boolean isBlock() {
        return (id instanceof BlockID);
    }

    public JSONObject toJSONObject() throws JSONException {
        JSONObject o = new JSONObject();
        o.put("id", id.toJSON());
        o.put("displayName", displayName);
        o.put("lore", lore.toArray(new String[lore.size()]));
        o.put("damage", damage);
        o.put("data", data);
        o.put("amount", amount);
        return o;
    }

}
