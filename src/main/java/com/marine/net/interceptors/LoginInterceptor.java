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

package com.marine.net.interceptors;

import com.marine.Logging;
import com.marine.StandaloneServer;
import com.marine.game.LoginHandler;
import com.marine.io.data.ByteData;
import com.marine.net.Client;
import com.marine.net.login.DisconnectPacket;
import com.marine.net.login.LoginPacket;

public class LoginInterceptor implements PacketInterceptor {

    final StandaloneServer server;

    public LoginInterceptor(StandaloneServer server) {
        this.server = server;
    }

    @Override
    public boolean intercept(int ID, ByteData data, Client c) {
        if (ID == 0x00) {
            LoginPacket packet = new LoginPacket();
            packet.readFromBytes(data);

            Logging.instance().info("Player: " + packet.name + " connected.");

            LoginHandler.LoginResponse loginReturn = server.getPlayerManager().getLoginManager().login(packet.name, c);
            if (!loginReturn.succeed()) {
                DisconnectPacket nopePacket = new DisconnectPacket(loginReturn.response);
                c.sendPacket(nopePacket);
                server.getNetwork().cleanUp(c);
                return true;
            }

            //TODO: Fix this
            server.getPlayerManager().getLoginManager().passPlayer(loginReturn.player);

            return true;
        }

        //TODO: Encryption Packets

        return false;
    }

}
