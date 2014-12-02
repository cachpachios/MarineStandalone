package com.marine.player;

public enum Gamemode {
	SURVIVAL	(0,"Survival"),
	CREATIVE	(1,"Creative"),
	ADVENTURE	(2,"Adventure"),
	SPECTATOR	(3,"Spectator");
	
	private final int id;
	private final String name;
	
	private Gamemode(int id, String name) {
		this.id = id;
		this.name = name;		
	}
	
	public byte getID() {
		return (byte) id;
	}
	
	public String getName() {
		return name;
	}
}
