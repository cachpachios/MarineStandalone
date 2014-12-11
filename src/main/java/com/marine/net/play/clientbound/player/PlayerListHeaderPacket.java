package com.marine.net.play.clientbound.player;

import com.marine.game.chat.ChatColor;
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
public class PlayerListHeaderPacket extends Packet {

    private final String header, footer;

    public PlayerListHeaderPacket(final String header, final String footer) {
        JSONObject headerObject = new JSONObject();
        headerObject.put("text", ChatColor.transform('&', header));
        this.header = headerObject.toJSONString();

        JSONObject footerObject = new JSONObject();
        footerObject.put("text", ChatColor.transform('&', footer));
        this.footer = footerObject.toJSONString();
    }

    @Override
    public int getID() {
        return 0x47;
    }

    @Override
    public void writeToStream(PacketOutputStream stream) throws IOException {
        ByteData data = new ByteData();
        data.writeUTF8(header);
        data.writeUTF8(footer);
        stream.write(getID(), data);
    }

    @Override
    public void readFromBytes(ByteData input) {

    }

    @Override
    public States getPacketState() {
        return null;
    }
}
