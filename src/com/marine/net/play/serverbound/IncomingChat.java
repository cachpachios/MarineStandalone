package com.marine.net.play.serverbound;

import com.marine.game.async.ChatManagment;
import com.marine.io.data.ByteData;
import com.marine.net.Client;
import com.marine.net.Packet;
import com.marine.net.PacketOutputStream;
import com.marine.net.States;
import com.marine.server.Marine;

import java.io.IOException;

/**
 * Created 2014-12-04 for MarineStandalone
 *
 * @author Citymonstret
 */
public class IncomingChat extends Packet {

    private final Client client;

    public IncomingChat(Client client) {
        this.client = client;
    }

    @Override
    public int getID() {
        return 0x01;
    }

    @Override
    public void writeToStream(PacketOutputStream stream) throws IOException {

    }

    @Override
    public void readFromBytes(ByteData input) {
        ChatManagment.getInstance().sendChatMessage(
                Marine.getServer().getServer().getPlayerManager().getPlayerByClient(client).getName(), input.readUTF8());
    }

    @Override
    public States getPacketState() {
        return States.INGAME;
    }

}
