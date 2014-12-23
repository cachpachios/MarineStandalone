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

package org.marinemc.net.packets;

import org.marinemc.io.data.ByteData;
import org.marinemc.net.Packet;
import org.marinemc.net.PacketOutputStream;
import org.marinemc.net.States;

import java.io.IOException;
/**
 * Sent by the client to introduce the client to the server,
 * All new connections should begin with this packet
 * 
 * @author Fozie
 */
public class HandshakePacket extends Packet {

    public int protocolVersion;
    public String serverAddress;
    public int port;
    public int nextState;

    @Override
    public int getID() {
        return 0x00;
    }

    @Override
    public void writeToStream(PacketOutputStream stream) throws IOException {
        ByteData d = new ByteData();
        d.writeVarInt(protocolVersion);
        d.writeUTF8(serverAddress);
        d.writeShort((short) port);
        d.writeVarInt(nextState);
    }

    @Override
    public void readFromBytes(ByteData input) {
        protocolVersion = input.readVarInt();
        serverAddress = input.readUTF8();
        port = input.readUnsignedShort();
        nextState = input.readVarInt();
    }

    public String toString() {
        return "Protocol: " + protocolVersion + " connected to " + serverAddress + " : " + port + " target state " + nextState;
    }

    public int getProtocolVersion() {
        return protocolVersion;
    }

    public String getServerAddress() {
        return serverAddress;
    }

    public int getPort() {
        return port;
    }

    public int getState() {
        return nextState;
    }

    @Override
    public States getPacketState() {
        return States.HANDSHAKE;
    }

}
