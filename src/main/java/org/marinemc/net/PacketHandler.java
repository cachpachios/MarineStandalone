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

package org.marinemc.net;

import org.marinemc.io.data.ByteData;
import org.marinemc.net.interceptors.*;
import org.marinemc.server.StandaloneServer;

/**
 * @author Fozie
 */
public class PacketHandler implements PacketInterceptor {

    HandshakeInterceptor handshake;
    StatusInterceptor status;
    LoginInterceptor login;
    IngameInterceptor ingame;

    public PacketHandler(StandaloneServer server) {
        handshake = new HandshakeInterceptor();
        status = new StatusInterceptor();
        login = new LoginInterceptor(server);
        ingame = new IngameInterceptor(server.getPlayerManager());
    }

    public boolean intercept(int id, ByteData data, Client c) {
        switch (c.getState()) {
            case HANDSHAKE:
                return handshake.intercept(id, data, c);
            case INTRODUCE:
                return status.intercept(id, data, c);
            case LOGIN:
                return login.intercept(id, data, c);
            case INGAME:
                return ingame.intercept(id, data, c);
            default:
                return false;
        }
    }
}
