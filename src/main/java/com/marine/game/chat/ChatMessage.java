package com.marine.game.chat;

import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ChatMessage {
    public List<ChatComponent> components;

    public ChatMessage() {
        components = Collections.synchronizedList(new ArrayList<ChatComponent>());
    }

    @SuppressWarnings("unchecked")
    public String toString() {
        JSONObject obj = new JSONObject();

		/*int i = 0;
        for(ChatComponent c : components) {
			i++;	
			obj.put(("Text" + i), c.JSON);
		}*/


        return obj.toJSONString();
    }
}
