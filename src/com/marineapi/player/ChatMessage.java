package com.marineapi.player;

import org.json.simple.JSONObject;

public class ChatMessage {

	JSONObject JSON;
	
	@SuppressWarnings("unchecked")
	public ChatMessage(String text) {
		JSON.put("text", text);
	}
	
	
	
	public String toString() {
		return JSON.toJSONString();
	}
}
