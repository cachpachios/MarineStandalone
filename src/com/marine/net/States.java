package com.marine.net;

public enum States {
	HANDSHAKE(0),
	INTRODUCE(1),
	LOGIN(2),
	INGAME(3);
	
	private final int id;
	
	private States(int id) {
		this.id = id;
	}
	
	public int getID() {
		return id;
	}
	
	public static final States getFromID(int id) {
		if(id == 0)
			return HANDSHAKE;
		else if(id == 1)
			return INTRODUCE;
		else if(id == 2)
			return LOGIN;
		else if(id == 3)
			return INGAME;
		return HANDSHAKE;
		
	}
}

	