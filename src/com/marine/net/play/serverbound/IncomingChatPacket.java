package com.marine.net.play.serverbound;

import java.io.IOException;

import com.marine.io.data.ByteData;
import com.marine.net.Packet;
import com.marine.net.PacketOutputStream;
import com.marine.net.States;

/**
 * Created 2014-12-04 for MarineStandalone
 *
 * @author Citymonstret
 */
public class IncomingChatPacket extends Packet {

    @Override
    public int getID() {
        return 0x01;
    }

    @Override
    public void writeToStream(PacketOutputStream stream) throws IOException {

    }

    private String msg;
    
    @Override
    public void readFromBytes(ByteData input) {
        msg = input.readUTF8();
    }
    
    public String getMessage() {
    	return msg;
    }

    @Override
    public States getPacketState() {
        return States.INGAME;
    }


}
