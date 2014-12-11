package com.marine.net.play.serverbound.player;

import com.marine.io.data.ByteData;
import com.marine.net.Packet;
import com.marine.net.PacketOutputStream;
import com.marine.net.States;
import com.marine.util.Position;

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
        DropItem;
    }

}
