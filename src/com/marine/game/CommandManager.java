package com.marine.game;

import com.marine.game.command.Command;

import java.util.*;

/**
 * Created 2014-12-01 for MarineStandalone
 *
 * @author Citymonstret
 */
public class CommandManager {

    private final Map<Command, List<String>> commandMap;
    private final List<String> raw;

    private static CommandManager instance;

    public CommandManager() {
        commandMap = new HashMap<>();
        raw = new ArrayList<>();
    }

    public static CommandManager getInstance() {
        if(instance == null)
            instance = new CommandManager();
        return instance;
    }

    public void registerCommand(Command command) throws RuntimeException {
        if (raw.contains(command.toString())) {
            throw new RuntimeException(String.format("Command Name '%s' is already taken", command.toString()));
        }
        raw.add(command.toString());
        List<String> s = new ArrayList<>(Arrays.asList(command.getAliases()));
        s.add(command.toString());
        commandMap.put(command, s);
    }

}
