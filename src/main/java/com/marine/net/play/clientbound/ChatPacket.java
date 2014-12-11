package com.marine.net.play.clientbound;

import com.marine.game.chat.ChatMessage;
import com.marine.io.data.ByteData;
import com.marine.net.Packet;
import com.marine.net.PacketOutputStream;
import com.marine.net.States;

import org.json.simple.JSONObject;

import java.io.IOException;

/**
 * Created 2014-12-04 for MarineStandalone
 *
 * @author Citymonstret
 */
public class ChatPacket extends Packet {

    private String message;
    private int position;
    private JSONObject object;

    public ChatPacket(final String message) {
        this(message, 0);
    }

    public ChatPacket(final ChatMessage message) {
        this.message = message.toString();
        this.position = 0;
    }

    @SuppressWarnings("unchecked")
	public ChatPacket(final String message, final int position) {
        this.position = position;
        this.object = new JSONObject();
        object.put("text", message);
        this.message = object.toJSONString();
    }

    @Override
    public int getID() {
        return 0x02;
    }

    @Override
    public void writeToStream(PacketOutputStream stream) throws IOException {
        ByteData data = new ByteData();
        
        if(message.length() < 32767)
        	data.writeUTF8(message);
        else
        	data.writeUTF8(message.substring(0, 32766));
        
        data.writeByte((byte) position);
        
        stream.write(getID(), data.getBytes());
    }

    @Override
    public void readFromBytes(ByteData input) {}

    @Override
    public States getPacketState() {
        return States.INGAME;
    }
}
