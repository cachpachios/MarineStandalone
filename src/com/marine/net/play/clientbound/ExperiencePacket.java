package com.marine.net.play.clientbound;

import com.marine.io.data.ByteData;
import com.marine.net.Packet;
import com.marine.net.PacketOutputStream;
import com.marine.net.States;
import com.marine.player.Player;

import java.io.IOException;

/**
 * Created 2014-12-07 for MarineStandalone
 *
 * @author Citymonstret
 */
public class ExperiencePacket extends Packet {

    private final Player player;

    public ExperiencePacket(final Player player) {
        this.player = player;
    }

    @Override
    public int getID() {
        return 0x1F;
    }

    @Override
    public void writeToStream(PacketOutputStream stream) throws IOException {
        ByteData data = new ByteData();
        data.writeFloat(player.getExp());
        data.writeVarInt(player.getLevels());
        data.writeVarInt((int) (player.getLevels() * 100 + (player.getExp() * 10)));
    }

    @Override
    public void readFromBytes(ByteData input) {

    }

    @Override
    public States getPacketState() {
        return States.INGAME;
    }
}
