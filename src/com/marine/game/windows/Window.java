package com.marine.game.windows;

import com.marine.game.chat.ChatComponent;

public abstract class Window {
	public abstract String getType();
	
	public abstract ChatComponent getName();
	
	public abstract byte getNumberOfSlots();
	
}
