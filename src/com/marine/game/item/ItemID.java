package com.marine.game.item;

public enum ItemID {

	IRON_SHOVEL				(256, "iron_shovel", true),
	
	;
	
	private final short ID;
	private final String name;
	private final boolean canTakeDamage;
	
	private ItemID(int id, String name, boolean canTakeDamage) {
		this.ID = (short) id;
		this.name = name;
		this.canTakeDamage = canTakeDamage;
	}

	public short getID() {
		return ID;
	}

	public String getName() {
		return name;
	}

	public boolean isCanTakeDamage() {
		return canTakeDamage;
	}

}
