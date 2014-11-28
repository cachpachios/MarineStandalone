package com.marineapi.world;

public enum WorldType {
	
	DEFAULT("default"),
	FLAT("flat"),
	LARGE_BIOMES("largeBiomes"),
	AMPLIFIED("amplified");
	
	private final String dataName;
	
	
	private WorldType(String name) {
		this.dataName = name;
	}
	
	public String toString() {
		return dataName;
	}
}
