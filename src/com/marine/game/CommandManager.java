package com.marine.game;

import com.google.common.collect.HashBiMap;
import com.marine.game.command.Command;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Created 2014-12-01 for MarineStandalone
 *
 * @author Citymonstret
 */
public class CommandManager {

    // These are already async-optimized
    private final Map<Command, List<String>> 	commandMap;
    private final Map<String, Command> 			stringMap;

    private static CommandManager instance;

    public CommandManager() {
        commandMap = HashBiMap.create();
        stringMap = HashBiMap.create();
    }

    public static CommandManager getInstance() {
        if(instance == null)
            instance = new CommandManager();
        return instance;
    }

    public Command getCommand(String cmd) {
        if(stringMap.containsKey(cmd))
            return stringMap.get(cmd);
        return null;
    }

    public void registerCommand(Command command) throws RuntimeException {
    	synchronized(commandMap) { synchronized(stringMap) {
        if (stringMap.containsKey(command.toString())) {
            throw new RuntimeException(String.format("Command Name '%s' is already taken", command.toString()));
        }
        stringMap.put(command.toString(), command);
        List<String> s = new ArrayList<>(Arrays.asList(command.getAliases()));
        s.add(command.toString());
        commandMap.put(command, s);
    	}} // Synchornization end.
    }

}
