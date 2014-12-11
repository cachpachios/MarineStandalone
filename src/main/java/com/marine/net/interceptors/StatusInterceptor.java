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

import com.marine.io.data.ByteData;
import com.marine.net.Client;
import com.marine.net.handshake.ListPacket;
import com.marine.net.handshake.PingPacket;

public class StatusInterceptor implements PacketInterceptor {

    @Override
    public boolean intercept(int id, ByteData data, Client c) {
        if (id == 0x00) {
            ListPacket packet = new ListPacket();
            c.sendPacket(packet);
        } else if (id == 0x01) {
            PingPacket packet = new PingPacket();
            packet.readFromBytes(data);
            c.sendPacket(packet);
        }
        return false;
    }

}
