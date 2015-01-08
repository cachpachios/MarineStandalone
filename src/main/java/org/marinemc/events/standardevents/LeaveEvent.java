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

import org.marinemc.events.PlayerEvent;
import org.marinemc.game.player.Player;

/**
 * Created 2014-12-08 for MarineStandalone
 *
 * @author Citymonstret
 */
public class LeaveEvent extends PlayerEvent {

	private final QuitReason reason;
	private String message;

	public LeaveEvent(final Player player, final QuitReason reason) {
		super(player, "leave");
		this.reason = reason;
		message = reason.getMessage();
	}

	public QuitReason getReason() {
		return reason;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(final String message) {
		this.message = message;
	}

	public static enum QuitReason {
		KICKED("%plr got kicked for: %reason"), NORMAL("%plr left the game"), TIMEOUT(
				"%plr timed out");

		private final String message;

		QuitReason(final String message) {
			this.message = message;
		}

		public String getMessage() {
			return message;
		}
	}

}
