package com.marine.world.generators;

public enum LevelType {

	DEFAULT			("default"),
	FLAT			("flat"),
	LARGE_BIOMES	("largeBiomes"),
	AMPLIFIED		("amplified"),
	DEFAULT_1_1		("default_1_1"); // What ever that is :S
	
	private final String name;
	
	private LevelType(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}
	
	
}
