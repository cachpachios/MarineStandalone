///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// MarineStandalone is a minecraft server software and API.
// Copyright (C) IntellectualSites (marine.intellectualsites.com)
//
// This program is free software; you can redistribute it and/or modify
// it under the terms of the GNU General Public License as published by
// the Free Software Foundation; either version 2 of the License, or
// (at your option) any later version.
//
// This program is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
// GNU General Public License for more details.
//
// You should have received a copy of the GNU General Public License along
// with this program; if not, write to the Free Software Foundation, Inc.,
// 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

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

    private static CommandManager instance;
    // These are already async-optimized
    private final Map<Command, List<String>> commandMap;
    private final Map<String, Command> stringMap;

    public CommandManager() {
        commandMap = HashBiMap.create();
        stringMap = HashBiMap.create();
    }

    public static CommandManager getInstance() {
        if (instance == null)
            instance = new CommandManager();
        return instance;
    }

    public Command getCommand(String cmd) {
        if (stringMap.containsKey(cmd))
            return stringMap.get(cmd);
        return null;
    }

    public void registerCommand(Command command) throws RuntimeException {
        synchronized (commandMap) {
            synchronized (stringMap) {
                if (stringMap.containsKey(command.toString())) {
                    throw new RuntimeException(String.format("Command Name '%s' is already taken", command.toString()));
                }
                stringMap.put(command.toString(), command);
                List<String> s = new ArrayList<>(Arrays.asList(command.getAliases()));
                s.add(command.toString());
                commandMap.put(command, s);
            }
        } // Synchornization end.
    }

    public List<Command> getCommands() {
        return new ArrayList<>(commandMap.keySet());
    }

}
