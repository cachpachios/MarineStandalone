package com.marine.game.chat;

public interface ChatReciver {
    public void sendMessage(String message);

    public void sendMessage(ChatMessage message);

    public void sendMessage(ChatComponent message);
}
