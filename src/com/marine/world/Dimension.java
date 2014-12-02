package com.marine.world;

public enum Dimension {
	OVERWORLD(0),
	NETHER(-1),
	END(1);

	private final int id;
	
	private Dimension(int id) {
		this.id = (byte) id;
	}
	
 	public byte getID() {
		return (byte) id;
	}
}
