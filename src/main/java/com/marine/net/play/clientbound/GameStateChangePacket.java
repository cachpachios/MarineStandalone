package com.marine.net.play.clientbound;

import com.marine.io.data.ByteData;
import com.marine.net.Packet;
import com.marine.net.PacketOutputStream;
import com.marine.net.States;

import java.io.IOException;

/**
 * Created 2014-12-06 for MarineStandalone
 *
 * @author Citymonstret
 */
public class GameStateChangePacket extends Packet {

    private final Reason reason;
    private final float value;

    public GameStateChangePacket(Reason reason, float value) {
        this.reason = reason;
        this.value = value;
    }

    @Override
    public int getID() {
        return 0x2B;
    }

    @Override
    public void writeToStream(PacketOutputStream stream) throws IOException {
        ByteData data = new ByteData();
        data.writeByte((byte) reason.ordinal());
        if (value > -1f)
            data.writeFloat(value);
        stream.write(getID(), data.getBytes());
    }

    @Override
    public void readFromBytes(ByteData input) {

    }

    @Override
    public States getPacketState() {
        return States.INGAME;
    }

    public static enum Reason {
        INVALID_BED,
        END_RAINING,
        BEGIN_RAINING,
        CHANGE_GAMEMODE,
        ENTER_CREDITS,
        DEMO_MESSAGES,
        ARROW_HITTING_PLAYER,
        FADE_VALUE,
        FADE_TIME
    }
}
