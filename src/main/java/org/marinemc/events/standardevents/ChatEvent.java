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
import org.marinemc.events.Event;
import org.marinemc.game.chat.ChatSender;
import org.marinemc.game.chat.UnspecifiedSender;

/**
 * Created 2014-12-07 for MarineStandalone
 *
 * @author Citymonstret
 * @author Fozie
 */
public class ChatEvent extends Event implements Cancellable {

	private String message;
	private boolean cancelled;

	private final ChatSender sender;

	public ChatEvent(final ChatSender sender, final String message) {
		super("chat", true);
		this.sender = sender;
		this.message = message;
		cancelled = false;
	}

	public ChatEvent(final String message) {
		super("chat", true);
		sender = UnspecifiedSender.getInstance();
		this.message = message;
		cancelled = false;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(final String newMessage) {
		message = newMessage;
	}

	public ChatSender getSender() {
		return sender;
	}

	@Override
	public boolean isCancelled() {
		return cancelled;
	}

	@Override
	public void setCancelled(final boolean b) {
		cancelled = b;
	}
}
