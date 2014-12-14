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

package org.marinemc.net.play.serverbound.player;

import org.marinemc.io.data.ByteData;
import org.marinemc.net.Packet;
import org.marinemc.net.PacketOutputStream;
import org.marinemc.net.States;
import org.marinemc.util.Position;

import java.io.IOException;

public class DiggingPacket extends Packet {

    private Status status;

    private Position blockPos;

    private byte blockFace;

    @Override
    public int getID() {
        return 0;
    }

    @Override
    public void writeToStream(PacketOutputStream stream) throws IOException {
    }

    @Override
    public void readFromBytes(ByteData input) {
        switch (input.readByte()) {
            case 0:
                status = Status.StartedDigging;
            case 1:
                status = Status.CanceledDigging;
            case 2:
                status = Status.FinishDigging;
            default:
                status = Status.FinishDigging;
        }

        blockPos = input.readPosition();

        blockFace = input.readByte(); // Block Face
    }

    public Status getStatus() {
        return status;
    }

    public Position getBlockPos() {
        return blockPos;
    }

    public byte getBlockFace() {
        return blockFace;
    }

    @Override
    public States getPacketState() {
        return States.INGAME;
    }

    public enum Status {
        StartedDigging,
        CanceledDigging,
        FinishDigging,
        DropItemStack,
        DropItem
    }

}
