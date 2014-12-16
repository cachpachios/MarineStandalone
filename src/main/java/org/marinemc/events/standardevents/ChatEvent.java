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

package org.marinemc.events.standardevents;

import org.marinemc.events.Cancellable;
import org.marinemc.events.PlayerEvent;
import org.marinemc.player.Player;

/**
 * Created 2014-12-07 for MarineStandalone
 *
 * @author Citymonstret
 */
public class ChatEvent extends PlayerEvent implements Cancellable {

    private String message;
    private boolean cancelled;

    public ChatEvent(final Player sender, String message) {
        super(sender, "chat", true);
        this.message = message;
        this.cancelled = false;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String newMessage) {
        this.message = newMessage;
    }

    public Player getSender() {
        return this.getPlayer();
    }

    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    @Override
    public void setCancelled(boolean b) {
        this.cancelled = b;
    }
}
