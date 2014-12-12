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

import com.marine.game.command.Command;
import com.marine.util.StringUtils;

import java.util.*;

/**
 * Created 2014-12-01 for MarineStandalone
 *
 * @author Citymonstret
 */
public class CommandManager {

    private static CommandManager instance;
    // These are already async-optimized
    private final Map<String, Command> stringMap;

    public CommandManager() {
        stringMap = new HashMap<>();
    }

    public static CommandManager getInstance() {
        if (instance == null)
            instance = new CommandManager();
        return instance;
    }

    public Command getCommand(String cmd) {
        try {
            return stringMap.get(cmd);
        } catch (NullPointerException e) {
            return null;
        }
    }

    public void registerCommand(Command command) throws RuntimeException {
        if (stringMap.containsKey(command.toString()))
            throw new RuntimeException(StringUtils.format("Command Name '%s' is already taken", command));
        List<String> ss = new ArrayList<>();
        stringMap.put(command.toString(), command);
        for (String s : command.getAliases()) {
            if (stringMap.containsKey(s))
                ss.add(s);
            else
                stringMap.put(s, command);
        }
        command.getAliases().removeAll(ss);
    }

    public Collection<Command> getCommands() {
        return stringMap.values();
    }

}
