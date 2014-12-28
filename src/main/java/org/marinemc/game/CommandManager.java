///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// MarineStandalone is a minecraft server software and API.
// Copyright (C) MarineMC (marinemc.org)
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

package org.marinemc.game;

import org.marinemc.game.command.Command;
import org.marinemc.game.command.ServiceProvider;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

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
        stringMap = new ConcurrentHashMap<>();
    }

    public static CommandManager getInstance() {
        if (instance == null) {
            instance = new CommandManager();
        }
        return instance;
    }

    public Command getCommand(final String cmd) {
        try {
            return stringMap.get(cmd);
        } catch (NullPointerException e) {
            return null;
        }
    }

    public void registerCommand(final ServiceProvider provider, final Command command) {
        String name = command.toString();
        if (stringMap.containsKey(name)) {
            final Command old = stringMap.get(name);
            if (old.getServiceProvider().getProviderPriority() == 0x00 && provider.getProviderPriority() != 0x00) {
                old.setName(old.getServiceProvider().getProviderName() + ":" + old.toString());
            } else {
                command.setName(provider.getProviderName() + ":" + command);
            }
        }
        name = command.toString();
        final List<String> ss = new ArrayList<>();
        stringMap.put(name, command);
        for (final String s : command.getAliases()) {
            if (stringMap.containsKey(s)) ss.add(s);
            else stringMap.put(s, command);
        }
        command.getAliases().removeAll(ss);
        command.setProvider(this);
        command.setServiceProvider(provider);
    }

    public Collection<Command> getCommands() {
        Collection<Command> commands = new ArrayList<>();
        for (final Command command : stringMap.values()) {
            if (!commands.contains(command))
                commands.add(command);
        }
        return commands;
    }

}
