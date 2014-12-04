package com.marine.game.command;

import com.marine.game.chat.ChatReciver;


/**
 * Created 2014-12-01 for MarineStandalone
 *
 * @author Citymonstret
 */
public interface CommandSender extends ChatReciver{
    public void executeCommand(String command);
    public void executeCommand(String command, String[] arguments);
    public void executeCommand(Command command, String[] arguments);
    public boolean hasPermission(String permission);
}
