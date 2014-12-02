package com.marine.game.chat;

import org.json.simple.JSONObject;

public class ChatComponent {
	
	JSONObject JSON;
	
	@SuppressWarnings("unchecked")
	public ChatComponent(String text, boolean bold, boolean italic, boolean underlined, boolean stirkethrough, ChatColor color) {
		JSON = new JSONObject();
		JSON.put("color", color.getDataString());
		JSON.put("bold", bold);
		JSON.put("italic", italic);
		JSON.put("underlined", underlined);
		JSON.put("strikethrough", stirkethrough);
		JSON.put("text", text);
	}
	
	public ChatComponent(String text) {
		this(text, false, false, false, false, ChatColor.RESET);
	}
	
	public String toString() {
		return JSON.toJSONString();
	}
	
	public JSONObject getJsonObject() {
		return JSON;
	}
	
	
}
