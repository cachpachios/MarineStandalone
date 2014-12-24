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

import org.marinemc.io.binary.ByteData;
import org.marinemc.net.interceptors.HandshakeInterceptor;
import org.marinemc.net.interceptors.IngameInterceptor;
import org.marinemc.net.interceptors.LoginInterceptor;
import org.marinemc.net.interceptors.PacketInterceptor;
import org.marinemc.net.interceptors.StatusInterceptor;

/**
 * Points the packets to diffrent interceptors depending on the client state
 * 
 * @author Fozie
 */
public final class PacketHandler implements PacketInterceptor {

    HandshakeInterceptor handshake;
    StatusInterceptor status;
    LoginInterceptor login;
    IngameInterceptor ingame;

    public PacketHandler() {
        handshake = new HandshakeInterceptor();
        status = new StatusInterceptor();
        login = new LoginInterceptor();
        ingame = new IngameInterceptor();
    }

    public boolean intercept(int id, ByteData data, final Client c) {
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
