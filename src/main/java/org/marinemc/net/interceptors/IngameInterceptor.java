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

package org.marinemc.net.interceptors;

import org.marinemc.game.PlayerManager;
import org.marinemc.io.data.ByteData;
import org.marinemc.net.Client;
import org.marinemc.net.play.KeepAlivePacket;
import org.marinemc.net.play.serverbound.IncomingChatPacket;
import org.marinemc.net.play.serverbound.player.PlayerLookPacket;
import org.marinemc.net.play.serverbound.player.PlayerPositionPacket;
import org.marinemc.net.play.serverbound.player.ServerboundPlayerLookPositionPacket;
import org.marinemc.util.Location;
/**
 * Intercepts ingamepackets
 * 
 * @author Fozie
 */
public class IngameInterceptor implements PacketInterceptor {

    public IngameInterceptor() {
    }

    @Override
    public boolean intercept(int id, ByteData data, Client c) {
    	switch(id) {
    	case 0:
    	case 1:
    	
    	
    	default: return false;
    	}
    }
}
