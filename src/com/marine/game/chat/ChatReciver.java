package com.marine.game.chat;

public interface ChatReciver {
    public void sendMessage(String message);
    public void sendMessage(RawChatMessage message);
}
