package com.marine.game;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.marine.game.command.Command;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Created 2014-12-01 for MarineStandalone
 *
 * @author Citymonstret
 */
public class CommandManager {

    private final BiMap<Command, List<String>> commandMap;
    private final List<String> raw;

    private static CommandManager instance;

    public CommandManager() {
        commandMap = HashBiMap.create();
        raw = Collections.synchronizedList(new ArrayList<String>());
    }

    public static CommandManager getInstance() {
        if(instance == null)
            instance = new CommandManager();
        return instance;
    }

    public void registerCommand(Command command) throws RuntimeException {
    	synchronized(commandMap) { synchronized(raw) {
        if (raw.contains(command.toString())) {
            throw new RuntimeException(String.format("Command Name '%s' is already taken", command.toString()));
        }
        raw.add(command.toString());
        List<String> s = new ArrayList<>(Arrays.asList(command.getAliases()));
        s.add(command.toString());
        commandMap.put(command, s);
    	}} // Synchornization end.
    }

}
