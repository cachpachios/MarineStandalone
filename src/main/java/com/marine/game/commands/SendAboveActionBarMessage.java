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

package com.marine.game.commands;

import com.marine.game.chat.ChatColor;
import com.marine.game.command.Command;
import com.marine.game.command.CommandSender;
import com.marine.player.Player;
import com.marine.util.StringUtils;

import java.util.Arrays;

/**
 * Created 2014-12-10 for MarineStandalone
 *
 * @author Citymonstret
 */
public class SendAboveActionBarMessage extends Command {

    public SendAboveActionBarMessage() {
        super("sendaboveactionbarmessage", new String[]{}, "Send above action bar message");
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (!(sender instanceof Player))
            return;
        Player player = (Player) sender;
        args = replaceAll(args, sender);
        player.sendAboveActionbarMessage(ChatColor.transform('&', StringUtils.join(Arrays.asList(args), " ")));
    }
}
