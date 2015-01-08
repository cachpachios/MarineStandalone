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
import org.marinemc.game.player.Player;

/**
 * Created 2014-12-20 for MarineStandalone
 *
 * @author Citymonstret
 */
public class PreLoginEvent extends Event implements Cancellable {

	private final Player player;
	private boolean allowed = true;
	private String message = "Disconnected";

	public PreLoginEvent(final Player player) {
		super("pre_join", true);
		this.player = player;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(final String s) {
		message = s;
	}

	public Player getPlayer() {
		return player;
	}

	@Override
	public boolean isCancelled() {
		return allowed;
	}

	@Override
	public void setCancelled(final boolean n) {
		allowed = n;
	}
}
