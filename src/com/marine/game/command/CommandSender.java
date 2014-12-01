package com.marine.game.command;

import com.marine.game.chat.RawChatMessage;

/**
 * Created 2014-12-01 for MarineStandalone
 *
 * @author Citymonstret
 */
public interface CommandSender {
    public void executeCommand(String command);
    public void executeCommand(String command, String[] arguments);
    public void executeCommand(Command command, String[] arguments);
    public void sendMessage(String message);
    public void sendMessage(RawChatMessage message);
}
