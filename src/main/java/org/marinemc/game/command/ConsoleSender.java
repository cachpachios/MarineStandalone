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

package org.marinemc.game.command;

import org.marinemc.game.CommandManager;
import org.marinemc.game.chat.ChatComponent;
import org.marinemc.game.chat.ChatMessage;
import org.marinemc.logging.Logging;
import org.marinemc.util.Location;

/**
 * Created 2014-12-21 for MarineStandalone
 *
 * @author Citymonstret
 */
public class ConsoleSender implements CommandSender {

    @Override
    public void executeCommand(String command) {
        executeCommand(command, new String[]{});
    }

    @Override
    public void executeCommand(String command, String[] arguments) {
        Command cmd;
        if ((cmd = CommandManager.getInstance().getCommand(command.toLowerCase().substring(1))) == null) {
            sendMessage("That command doesn't exist");
        } else {
            executeCommand(cmd, arguments);
        }
    }

    @Override
    public void executeCommand(Command command, String[] arguments) {
        command.execute(this, arguments);
    }

    @Override
    public Location getLocation() {
        return null;
    }

    @Override
    public boolean hasPermission(String permission) {
        return true;
    }

    @Override
    public void sendMessage(String message) {
        Logging.getLogger().log(message);
    }

    @Override
    public void sendMessage(ChatMessage message) {
        sendMessage(this.toString());
    }

    @Override
    public void sendMessage(ChatComponent message) {
        sendMessage(message.getJsonObject().get("text").toString());
    }
}
